package src.juego;

/**
 * Representa al héroe del videojuego controlado por comandos.
 */
public class Jugador extends EntidadVideojuego {
    private static final int VIDA_INICIAL = 100;
    private static final int TAMANIO_CASILLA = 1;

    public Jugador(String nombre, int x, int y) {
        super(nombre, x, y, TAMANIO_CASILLA, TAMANIO_CASILLA, VIDA_INICIAL);
    }

    @Override
    public void actualizarComportamiento(MotorJuego motor) {
        if (this.getVida() <= 0) {
            System.out.println("[LOG-JUGADOR] " + getNombre() + " ha caído en combate.");
        }
    }
}