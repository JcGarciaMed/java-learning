package pe.forjix.leccion07;

/**
 * Referencia de la Leccion 7, parte 1: herencia bien usada.
 * "Un empleado asalariado ES UN empleado" es una relacion genuina y permanente.
 */
public class Empleados {

    // =================================================================
    // Clase ABSTRACTA: define el contrato y la logica comun.
    // No se puede instanciar: 'new Empleado(...)' no compila.
    // =================================================================
    static abstract class Empleado {

        // protected: visible para las subclases. Preferir private + getter
        // cuando se pueda; protected tambien es acoplamiento.
        private final String nombre;
        private final String id;

        protected Empleado(String nombre, String id) {
            if (nombre == null || nombre.isBlank()) {
                throw new IllegalArgumentException("El nombre es obligatorio");
            }
            this.nombre = nombre.strip();
            this.id = id;
        }

        /**
         * Cada tipo de empleado cobra distinto: el padre NO sabe como.
         */
        public abstract double pagoMensual();

        /**
         * Nombre del tipo, para el reporte.
         */
        public abstract String tipo();

        /**
         * TEMPLATE METHOD: el esqueleto vive aqui y usa los metodos abstractos.
         * final -> ningun hijo puede cambiar el formato de la boleta.
         */
        public final String boleta() {
            return "%-16s %-12s %10.2f".formatted(nombre, tipo(), pagoMensual());
        }

        public String nombre() {
            return nombre;
        }

        public String id() {
            return id;
        }

        @Override
        public String toString() {
            return "%s(%s)".formatted(nombre, id);
        }
    }

    // =================================================================
    // Subclases: cada una implementa SU forma de cobrar.
    // =================================================================
    static final class Asalariado extends Empleado {
        private final double sueldo;

        Asalariado(String nombre, String id, double sueldo) {
            super(nombre, id);              // PRIMERA sentencia, obligatorio
            this.sueldo = sueldo;
        }

        @Override
        public double pagoMensual() {
            return sueldo;
        }

        @Override
        public String tipo() {
            return "Asalariado";
        }
    }

    static final class PorHoras extends Empleado {
        private static final double RECARGO_EXTRA = 1.5;
        private static final int HORAS_NORMALES = 160;

        private final double tarifa;
        private final int horas;

        PorHoras(String nombre, String id, double tarifa, int horas) {
            super(nombre, id);
            this.tarifa = tarifa;
            this.horas = horas;
        }

        @Override
        public double pagoMensual() {
            if (horas <= HORAS_NORMALES) {
                return tarifa * horas;
            }
            final int extras = horas - HORAS_NORMALES;
            return tarifa * HORAS_NORMALES + tarifa * RECARGO_EXTRA * extras;
        }

        @Override
        public String tipo() {
            return "Por horas";
        }
    }

    static final class Comisionista extends Empleado {
        private final double base;
        private final double ventas;
        private final double porcentaje;

        Comisionista(String nombre, String id, double base, double ventas, double porcentaje) {
            super(nombre, id);
            this.base = base;
            this.ventas = ventas;
            this.porcentaje = porcentaje;
        }

        @Override
        public double pagoMensual() {
            return base + ventas * porcentaje;
        }

        @Override
        public String tipo() {
            return "Comisionista";
        }
    }

    // =================================================================
    public static void main(String[] args) {

        // Un array del tipo PADRE que contiene objetos de tipos HIJOS distintos.
        final Empleado[] planilla = {
                new Asalariado("Ana Torres", "E-001", 4500),
                new PorHoras("Luis Diaz", "E-002", 25, 180),
                new Comisionista("Sara Ruiz", "E-003", 1500, 40_000, 0.05),
                new Asalariado("Jorge Garcia", "E-004", 6200)
        };

        System.out.printf("%-16s %-12s %10s%n", "NOMBRE", "TIPO", "PAGO");
        System.out.println("-".repeat(40));

        double total = 0;
        for (final Empleado e : planilla) {
            // DESPACHO DINAMICO: la variable es Empleado, pero se ejecuta
            // el pagoMensual() del tipo REAL de cada objeto.
            System.out.println(e.boleta());
            total += e.pagoMensual();
        }

        System.out.println("-".repeat(40));
        System.out.printf("%-29s %10.2f%n", "TOTAL PLANILLA", total);

        System.out.println("""
                
                Fijate en el bucle: no hay ni un if ni un switch sobre el tipo.
                Anadir manana un 'Practicante' no obliga a tocar esta linea.
                Eso es la O de SOLID: abierto a extension, cerrado a modificacion.
                """);
    }
}
