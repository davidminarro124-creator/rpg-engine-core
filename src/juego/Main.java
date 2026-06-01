package juego;

/**
 * Clase conductora encargada de simular las acciones y el bucle.
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("=== INICIALIZANDO RPG CORE ENGINE ===");
        MotorJuego motor = new MotorJuego();

        Jugador caballero = new Jugador("Arturo", 2, 2);
        Enemigo goblin = new Enemigo("Goblin Saqueador", 4, 4);

        motor.añadirEntidad(caballero);
        motor.añadirEntidad(goblin);

        // Cambios de estado y simulaciones
        motor.iniciarPartida();
        motor.actualizar();

        // Simulamos controles táctiles del jugador
        motor.procesarInputSimulado("DERECHA");
        motor.procesarInputSimulado("ABAJO");
        
        // Volvemos a actualizar para ver los movimientos reflejados
        motor.actualizar();
        
        // Provocamos daño simulado para comprobar el Game Over
        System.out.println("\n=== SIMULANDO SEGUIDILLA DE DAÑO ===");
        motor.procesarInputSimulado("RECIBIR_DANIO");
        motor.procesarInputSimulado("RECIBIR_DANIO");
        motor.procesarInputSimulado("RECIBIR_DANIO");
        motor.procesarInputSimulado("RECIBIR_DANIO");
        
        motor.actualizar();
    }
}