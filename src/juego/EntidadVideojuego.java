package juego;

/**
 * Clase abstracta que representa cualquier elemento del videojuego en la cuadrícula.
 */
public abstract class EntidadVideojuego {
    private final String nombre;
    private int x;
    private int y;
    private final int ancho;
    private final int alto;
    private int vida;
    private final String animacionId;

    public EntidadVideojuego(String nombre, int x, int y, int ancho, int alto, int vida) {
        this.nombre = nombre;
        this.x = x;
        this.y = y;
        this.ancho = ancho;
        this.alto = alto;
        this.vida = vida;
        this.animacionId = "anim_" + nombre.toLowerCase().replace(" ", "_");
    }

    public void mover(int dx, int dy) {
        this.x += dx;
        this.y += dy;
    }

    // Método abstracto obligatorio: cada entidad se comportará de forma única en cada turno
    public abstract void actualizarComportamiento(MotorJuego motor);

    // Métodos Getters y Setters para respetar la encapsulación exigida
    public String getNombre() { return nombre; }
    public int getX() { return x; }
    public void setX(int x) { this.x = x; }
    public int getY() { return y; }
    public void setY(int y) { this.y = y; }
    public int getAncho() { return ancho; }
    public int getAlto() { return alto; }
    public int getVida() { return vida; }
    public void setVida(int vida) { this.vida = vida; }
    public String getAnimacionId() { return animacionId; }
}