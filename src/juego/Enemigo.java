package src.juego;

/**
 * Representa a las amenazas que habitan en la mazmorra.
 */
public class Enemigo extends EntidadVideojuego {
    private static final int VIDA_ENEMIGO = 40;
    private static final int TAMANIO_CASILLA = 1;
    private String comportamiento;

    public Enemigo(String nombre, int x, int y) {
        super(nombre, x, y, TAMANIO_CASILLA, TAMANIO_CASILLA, VIDA_ENEMIGO);
        this.comportamiento = "PATRULLAR";
    }

    @Override
    public void actualizarComportamiento(MotorJuego motor) {
        // Simulación de movimiento errático simple para la versión base
        int direccion = (Math.random() > 0.5) ? 1 : -1;
        if (Math.random() > 0.5) {
            this.mover(direccion, 0);
        } else {
            this.mover(0, direccion);
        }
        System.out.println("[LOG-ENEMIGO] " + getNombre() + " se mueve en modo " + comportamiento + " a la posición (" + getX() + "," + getY() + ")");
    }

    public String getComportamiento() { return comportamiento; }
    public void setComportamiento(String comportamiento) { this.comportamiento = comportamiento; }
}