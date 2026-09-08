package pe.forjix.leccion08;

/**
 * Referencia de la Leccion 8: interfaces, default, static, multiples contratos,
 * programar contra abstracciones e interfaces funcionales.
 */
public class Contratos {

    // =================================================================
    // 1. INTERFAZ: contrato puro. Sin estado, sin constructor.
    //    Los metodos son 'public abstract' implicitamente.
    // =================================================================
    interface Vehiculo {
        String matricula();
        int ruedas();

        /** default: implementacion compartida. Existe para poder AMPLIAR
         *  una interfaz sin romper a quien ya la implementa. */
        default String ficha() {
            return "%s (%d ruedas)".formatted(matricula(), ruedas());
        }

        /** static en interfaz: utilidad ligada al concepto, no a un objeto. */
        static boolean matriculaValida(String m) {
            return m != null && m.matches("[A-Z]{3}-[0-9]{3}");
        }
    }

    // Segundo contrato, independiente del primero.
    interface Electrico {
        int autonomiaKm();

        default boolean necesitaCargaPara(int km) {
            return km > autonomiaKm();
        }
    }

    // =================================================================
    // 2. Una clase puede cumplir VARIOS contratos a la vez.
    //    Con 'extends' solo podria heredar de UNA clase.
    // =================================================================
    record Auto(String matricula) implements Vehiculo {
        @Override public int ruedas() { return 4; }
    }

    record Moto(String matricula) implements Vehiculo {
        @Override public int ruedas() { return 2; }
        @Override public String ficha() {                 // se puede sobrescribir el default
            return Vehiculo.super.ficha() + " [moto]";    // ...y llamar al original
        }
    }

    record AutoElectrico(String matricula, int autonomiaKm)
            implements Vehiculo, Electrico {              // DOS interfaces
        @Override public int ruedas() { return 4; }
    }

    // =================================================================
    // 3. INTERFAZ FUNCIONAL: un solo metodo abstracto -> admite lambda.
    // =================================================================
    @FunctionalInterface
    interface Descuento {
        double aplicar(double monto);

        /** Componer comportamientos: dos descuentos encadenados. */
        default Descuento luego(Descuento siguiente) {
            return monto -> siguiente.aplicar(this.aplicar(monto));
        }
    }

    // =================================================================
    public static void main(String[] args) {

        System.out.println("--- Multiples contratos ---");
        final Vehiculo[] flota = {
                new Auto("ABC-123"),
                new Moto("XY-9"),
                new AutoElectrico("EVA-777", 320)
        };
        for (final Vehiculo v : flota) {
            System.out.println("  " + v.ficha());
        }

        System.out.println("\n--- Metodo static de la interfaz ---");
        for (final Vehiculo v : flota) {
            System.out.printf("  %-8s matricula valida: %b%n",
                    v.matricula(), Vehiculo.matriculaValida(v.matricula()));
        }

        System.out.println("\n--- Un objeto que cumple DOS contratos ---");
        final AutoElectrico tesla = new AutoElectrico("EVA-777", 320);
        System.out.println("  como Vehiculo : " + tesla.ficha());
        System.out.println("  como Electrico: necesita carga para 500 km? "
                + tesla.necesitaCargaPara(500));

        System.out.println("\n--- Interfaz funcional: comportamiento como dato ---");
        final Descuento ninguno   = monto -> monto;
        final Descuento navidad   = monto -> monto * 0.85;          // -15%
        final Descuento cupon10   = monto -> Math.max(0, monto - 10);
        final Descuento combinado = navidad.luego(cupon10);         // se componen

        final double precio = 100.0;
        System.out.printf("  sin descuento : %.2f%n", ninguno.aplicar(precio));
        System.out.printf("  navidad       : %.2f%n", navidad.aplicar(precio));
        System.out.printf("  cupon 10      : %.2f%n", cupon10.aplicar(precio));
        System.out.printf("  ambos         : %.2f%n", combinado.aplicar(precio));

        System.out.println("""
                
                Fijate en lo ultimo: 'Descuento' no lo implementa ninguna clase.
                Cada regla es una LINEA que se pasa como parametro.
                Eso solo es posible porque la interfaz tiene UN metodo abstracto.
                """);
    }
}
