package pe.forjix.leccion09;

import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Map;
import java.util.Set;

/**
 * Referencia de la Leccion 9, partes 4 y 5: enums con estado y comportamiento,
 * y clases anidadas.
 */
public class Estados {

    // =================================================================
    // 1. ENUM CON CAMPOS Y METODOS
    //    Cada constante lleva sus propios datos.
    // =================================================================
    enum Prioridad {
        // Cada constante invoca al constructor con SUS valores.
        BAJA("Estandar", 5.0),
        MEDIA("Preferente", 12.0),
        ALTA("Urgente", 30.0);

        private final String etiqueta;
        private final double recargo;

        // El constructor de un enum es SIEMPRE private (implicitamente).
        Prioridad(String etiqueta, double recargo) {
            this.etiqueta = etiqueta;
            this.recargo = recargo;
        }

        public String etiqueta() {
            return etiqueta;
        }

        public double recargo() {
            return recargo;
        }

        public double aplicarA(double costoBase) {
            return costoBase + recargo;
        }
    }

    // =================================================================
    // 2. ENUM CON COMPORTAMIENTO DISTINTO POR CONSTANTE
    //    Cada constante implementa el metodo abstracto a su manera.
    //    Es un Strategy con los casos cerrados y garantizados por el compilador.
    // =================================================================
    enum Operacion {
        SUMA("+") {
            @Override
            public double aplicar(double a, double b) {
                return a + b;
            }
        },
        RESTA("-") {
            @Override
            public double aplicar(double a, double b) {
                return a - b;
            }
        },
        MULT("*") {
            @Override
            public double aplicar(double a, double b) {
                return a * b;
            }
        };

        private final String simbolo;

        Operacion(String simbolo) {
            this.simbolo = simbolo;
        }

        public abstract double aplicar(double a, double b);

        @Override
        public String toString() {
            return simbolo;
        }
    }

    // =================================================================
    // 3. ENUM COMO MAQUINA DE ESTADOS
    //    El enum es dueno de sus propias reglas de transicion.
    // =================================================================
    enum Estado {
        CREADO, EN_TRANSITO, ENTREGADO, CANCELADO;

        /**
         * Estados finales: de aqui no se sale.
         */
        public boolean esFinal() {
            return this == ENTREGADO || this == CANCELADO;   // '==' es CORRECTO con enums
        }

        public Set<Estado> siguientesPosibles() {
            return switch (this) {                            // sin 'default': el compilador
                case CREADO -> EnumSet.of(EN_TRANSITO, CANCELADO);  // exige cubrir todo
                case EN_TRANSITO -> EnumSet.of(ENTREGADO, CANCELADO);
                case ENTREGADO, CANCELADO -> EnumSet.noneOf(Estado.class);
            };
        }

        public boolean puedeIrA(Estado destino) {
            return siguientesPosibles().contains(destino);
        }
    }

    // =================================================================
    // 4. CLASES ANIDADAS
    // =================================================================

    /**
     * STATIC NESTED: es una clase normal que vive dentro de otra por organizacion.
     * NO guarda referencia a la clase externa. Es la que casi siempre quieres.
     */
    static class Etiqueta {
        private final String texto;

        Etiqueta(String texto) {
            this.texto = texto;
        }

        @Override
        public String toString() {
            return "[" + texto + "]";
        }
    }

    private final String almacen;

    Estados(String almacen) {
        this.almacen = almacen;
    }

    /**
     * INNER (sin static): guarda una referencia IMPLICITA al objeto externo.
     * Puede leer sus campos... y lo mantiene vivo en memoria mientras exista.
     */
    class Operario {
        private final String nombre;

        Operario(String nombre) {
            this.nombre = nombre;
        }

        String describir() {
            return nombre + " trabaja en " + almacen;   // accede al campo del objeto externo
        }
    }

    // =================================================================
    public static void main(String[] args) {

        System.out.println("=== Enum con campos y metodos ===");
        for (final Prioridad p : Prioridad.values()) {        // values(): todas las constantes
            System.out.printf("  %-6s %-12s recargo %5.2f  -> 100.00 queda en %6.2f%n",
                    p.name(), p.etiqueta(), p.recargo(), p.aplicarA(100.0));
        }

        System.out.println("\n=== Enum con comportamiento por constante ===");
        for (final Operacion op : Operacion.values()) {
            System.out.printf("  12 %s 4 = %.1f%n", op, op.aplicar(12, 4));
        }

        System.out.println("\n=== Enum como maquina de estados ===");
        for (final Estado e : Estado.values()) {
            System.out.printf("  %-12s final=%-5b  siguientes: %s%n",
                    e, e.esFinal(), e.siguientesPosibles());
        }
        System.out.println("  CREADO -> ENTREGADO permitido? " + Estado.CREADO.puedeIrA(Estado.ENTREGADO));
        System.out.println("  CREADO -> EN_TRANSITO permitido? " + Estado.CREADO.puedeIrA(Estado.EN_TRANSITO));

        System.out.println("\n=== EnumMap: un Map optimizado para claves enum ===");
        final Map<Prioridad, Integer> pedidos = new EnumMap<>(Prioridad.class);
        pedidos.put(Prioridad.ALTA, 3);
        pedidos.put(Prioridad.BAJA, 17);
        System.out.println("  " + pedidos);   // mantiene el orden de declaracion

        System.out.println("\n=== valueOf y el peligro de ordinal ===");
        System.out.println("  valueOf(\"ALTA\") -> " + Prioridad.valueOf("ALTA"));
        System.out.println("  ALTA.ordinal()   -> " + Prioridad.ALTA.ordinal()
                + "   <-- NUNCA persistas esto: cambia si reordenas las constantes");

        System.out.println("\n=== Clases anidadas ===");
        System.out.println("  static nested: " + new Etiqueta("FRAGIL"));
        final Estados almacenLima = new Estados("Lima Norte");
        final Operario operario = almacenLima.new Operario("Ana");   // sintaxis 'externa.new'
        System.out.println("  inner        : " + operario.describir());

        System.out.println("""
                
                Por defecto, haz tus clases anidadas 'static'.
                Una inner class guarda una referencia oculta al objeto externo:
                si la inner sobrevive (en una lista, un cache, un hilo), impide
                que el externo se libere. Es una fuga de memoria clasica.
                """);
    }
}
