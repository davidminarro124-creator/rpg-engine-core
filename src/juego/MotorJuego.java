package juego;

import java.util.ArrayList;
import java.util.List;

/**
 * Motor central que gestiona el estado del juego, el bucle y las entidades.
 */
public class MotorJuego {
    // Constantes de estado globales para evitar "magic strings"
    public static final String ESTADO_MENU = "MENU";
    public static final String ESTADO_JUGANDO = "JUGANDO";
    public static final String ESTADO_PAUSA = "PAUSA";
    public static final String ESTADO_GAME_OVER = "GAME_OVER";

    private String estadoActual;
    private final List<EntidadVideojuego> entidades;
    private int puntuacion;

    public MotorJuego() {
        this.estadoActual = ESTADO_MENU;
        this.entidades = new ArrayList<>();
        this.puntuacion = 0;
    }

    public void iniciarPartida() {
        this.estadoActual = ESTADO_JUGANDO;
        System.out.println("[LOG-MOTOR] Estado cambiado a JUGANDO. ¡Comienza la aventura!");
    }

    public void pausarPartida() {
        if (this.estadoActual.equals(ESTADO_JUGANDO)) {
            this.estadoActual = ESTADO_PAUSA;
            System.out.println("[LOG-MOTOR] Partida en PAUSA.");
        }
    }

    public void reanudarPartida() {
        if (this.estadoActual.equals(ESTADO_PAUSA)) {
            this.estadoActual = ESTADO_JUGANDO;
            System.out.println("[LOG-MOTOR] Partida REANUDADA.");
        }
    }

    public void añadirEntidad(EntidadVideojuego e) {
        this.entidades.add(e);
        System.out.println("[LOG-MOTOR] Entidad añadida: " + e.getNombre());
    }

    public void eliminarEntidad(EntidadVideojuego e) {
        this.entidades.remove(e);
        System.out.println("[LOG-MOTOR] Entidad eliminada: " + e.getNombre());
    }

    public void actualizar() {
        if (!this.estadoActual.equals(ESTADO_JUGANDO)) {
            System.out.println("[LOG-MOTOR] El motor está en reposo. Estado actual: " + estadoActual);
            return;
        }

        System.out.println("\n--- [CICLO DE JUEGO / GAME LOOP TICK] ---");
        
        // Copia de seguridad de la lista para evitar errores si eliminamos elementos durante el bucle
        List<EntidadVideojuego> copia = new ArrayList<>(this.entidades);
        for (EntidadVideojuego e : copia) {
            e.actualizarComportamiento(this);
        }

        verificarGameOver();
    }

    public void verificarGameOver() {
        for (EntidadVideojuego e : entidades) {
            if (e instanceof Jugador && e.getVida() <= 0) {
                this.estadoActual = ESTADO_GAME_OVER;
                System.out.println("[LOG-MOTOR] ¡GAME OVER! El jugador se ha quedado sin vida.");
            }
        }
    }

    public void procesarInputSimulado(String comando) {
        if (!this.estadoActual.equals(ESTADO_JUGANDO)) return;

        for (EntidadVideojuego e : entidades) {
            if (e instanceof Jugador) {
                switch (comando.toUpperCase()) {
                    case "ARRIBA":    e.mover(0, -1); System.out.println("[INPUT] Mover ARRIBA"); break;
                    case "ABAJO":     e.mover(0, 1);  System.out.println("[INPUT] Mover ABAJO"); break;
                    case "IZQUIERDA": e.mover(-1, 0); System.out.println("[INPUT] Mover IZQUIERDA"); break;
                    case "DERECHA":   e.mover(1, 0);  System.out.println("[INPUT] Mover DERECHA"); break;
                    case "RECIBIR_DANIO": 
                        e.setVida(e.getVida() - 30);
                        System.out.println("[INPUT] Táctil: Trampa activada. Jugador pierde 30 de vida."); 
                        break;
                    default: System.out.println("[INPUT] Comando no reconocido.");
                }
            }
        }
    }

    public List<EntidadVideojuego> getEntidades() { return entidades; }
    public String getEstadoActual() { return estadoActual; }
    public int getPuntuacion() { return puntuacion; }
    public void setPuntuacion(int p) { this.puntuacion = p; }
}