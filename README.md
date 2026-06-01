
# 🎮 RPG Core Engine - Motor de Videojuego 2D por Cuadrícula

Este proyecto implementa la lógica interna de control de un núcleo o motor básico para un videojuego de rol (RPG) medieval en 2D basado en cuadrícula. El diseño se ha realizado de forma asistida por Inteligencia Artificial, priorizando una arquitectura orientada a objetos minimalista, limpia y desacoplada de cualquier interfaz gráfica.

---

## 🛠️ 1. Arquitectura del Software

El sistema respeta la restricción estricta de un máximo de 6 clases, estructurándose de la siguiente manera dentro del paquete `juego`:

* **`Main`**: Clase conductora del sistema. Actúa como el simulador del ciclo de juego (Game Loop) y de las entradas táctiles del usuario.
* **`MotorJuego`**: El cerebro del motor. Administra el estado global de la partida (`MENU`, `JUGANDO`, `GAME_OVER`) y contiene el bucle principal que actualiza las entidades y procesa las colisiones.
* **`EntidadVideojuego`**: Clase abstracta base que unifica las propiedades espaciales básicas (x, y, w, h), el estado de salud (`vida`) y el comportamiento genérico de cualquier elemento del mundo.
* **`Jugador`**: Extensión de `EntidadVideojuego` que representa al héroe controlado por el usuario, respondiendo a los comandos de movimiento.
* **`Enemigo`**: Extensión de `EntidadVideojuego` que implementa la toma de decisiones autónoma mediante una IA de persecución de estados dinámicos.

---

## 📊 2. Diagramas UML (en formato Mermaid)

### A. Diagrama de Clases
```mermaid
classDiagram
    class MotorJuego {
        -String estadoActual
        -List~EntidadVideojuego~ entidades
        +MotorJuego()
        +iniciarPartida() void
        +añadirEntidad(EntidadVideojuego e) void
        +actualizar() void
        -comprobarColisiones() void
        -resolverColision(EntidadVideojuego e1, EntidadVideojuego e2) void
        +verificarGameOver() void
        +procesarInputSimulado(String comando) void
        +getEntidades() List~EntidadVideojuego~
        +getEstadoActual() String
    }

    class EntidadVideojuego {
        <<abstract>>
        -String nombre
        -int x
        -int y
        -int ancho
        -int alto
        -int vida
        +EntidadVideojuego(String nombre, int x, int y, int w, int h, int vida)
        +mover(int dx, int dy) void
        +actualizarComportamiento(MotorJuego motor)* void
        +getX() int
        +getY() int
        +getAncho() int
        +getAlto() int
        +getVida() int
        +setVida(int vida) void
        +getNombre() String
    }

    class Jugador {
        +Jugador(String nombre, int x, int y)
        +actualizarComportamiento(MotorJuego motor) void
    }

    class Enemigo {
        -String comportamiento
        +Enemigo(String nombre, int x, int y)
        +actualizarComportamiento(MotorJuego motor) void
        +getComportamiento() String
    }

    Main ..> MotorJuego : Conduce e interactúa
    MotorJuego "1" --> "*" EntidadVideojuego : Almacena y gestiona
    EntidadVideojuego <|-- Jugador : Hereda de
    EntidadVideojuego <|-- Enemigo : Hereda de

graph LR
    Jugador((Actor: Jugador))
    
    CU01(CU-01: Iniciar Partida)
    CU02(CU-02: Mover Héroe e Interceptación)
    
    Jugador --> CU01
    Jugador --> CU02

## 📝 3. Especificación de Casos de Uso

### CU-01: Iniciar Partida
* **Objetivo**: Cambiar el estado del motor para comenzar el flujo de simulación del bucle de juego.
* **Actor Principal**: Jugador.
* **Precondiciones**: El sistema debe estar inicializado en el estado `MENU`.
* **Flujo Principal**:
    1. El jugador solicita iniciar la simulación.
    2. El motor cambia su parámetro `estadoActual` a `JUGANDO`.
    3. El sistema imprime por consola el log de confirmación del inicio.
* **Flujos Alternativos**: No aplica.
* **Postcondiciones**: El motor pasa a estado `JUGANDO` y queda habilitado el procesamiento de ticks.
* **Reglas de Negocio**: No se puede cambiar a estado `JUGANDO` si el estado actual es `GAME_OVER`.

### CU-02: Mover Héroe e Interceptación
* **Objetivo**: Desplazar al jugador por la cuadrícula 2D, provocando la reacción de la IA enemiga y calculando colisiones.
* **Actor Principal**: Jugador.
* **Precondiciones**: El motor debe encontrarse en estado `JUGANDO`.
* **Flujo Principal**:
    1. El jugador introduce un input simulado (`DERECHA`).
    2. La clase `Jugador` actualiza sus coordenadas en el tablero.
    3. El `MotorJuego` ejecuta el método `actualizar()`.
    4. La entidad `Enemigo` evalúa la posición del jugador, modifica su estado a `PERSEGUIR` y acorta distancias vectoriales hacia él.
    5. El motor ejecuta el algoritmo matemático AABB, detecta superposición de coordenadas y resta vida al héroe.
* **Flujos Alternativos**: Si la colisión reduce la vida del jugador a 0 o menos, se invoca inmediatamente el método `verificarGameOver()`.
* **Postcondiciones**: Las posiciones se actualizan y se penaliza la salud del jugador en caso de colisión física.
* **Reglas de Negocio**: Una entidad no puede ejecutar movimientos fuera de los límites lógicos establecidos en los parámetros del ciclo.

---

## 🤖 4. Bitácora del Uso de Inteligencia Artificial

* **Herramienta utilizada**: Gemini.
* **Rol asignado**: Arquitecto de Software Senior y Líder Técnico de Desarrollo en Java.

### Muestra de Prompts Exactos:
1. *"Necesito diseñar la estructura base de un motor de juego en Java limitándome a un máximo de 6 clases. Debe tener una clase abstracta EntidadVideojuego con coordenadas x, y, ancho, alto y dos hijas (Jugador y Enemigo), además de la clase cerebro MotorJuego y el Main conductor."*
2. *"Añade al motor un algoritmo matemático de detección de colisiones AABB utilizando las dimensiones (x, y, w, h) de las entidades y cambia la IA del enemigo para que calcule el vector de dirección y persiga activamente al jugador en lugar de patrullar al azar."*

### Control de Errores de la IA:
Durante el desarrollo, la IA intentó en primera instancia estructurar un sistema complejo segregando un `InputManager` independiente de la clase del motor. Al notar que esto consumía clases adicionales de manera innecesaria poniendo en riesgo la limitación técnica estricta (máximo 6 clases), se le ordenó explícitamente simplificar el diseño fusionando el procesamiento de inputs directamente dentro de la clase conductora `Main` y los métodos delegados de `MotorJuego`. Esto permitió mantener el proyecto en un total optimizado de 5 clases.

### Reflexión Crítica:
* **Ventajas**: El uso de la IA acelera exponencialmente el planteamiento de algoritmos específicos, como la fórmula de superposición matemática de cajas delimitadoras (AABB), y agiliza la resolución de incidencias en el entorno de desarrollo (como desajustes del Build Path o errores de caché en Eclipse).
* **Peligros**: El programador bajo presión de tiempo puede caer en el error de copiar y pegar código de forma automatizada sin comprender el flujo de dependencias. Esto genera problemas de acoplamiento o fallos al reconfigurar el árbol de proyectos en entornos locales si no se mantiene un criterio técnico crítico.