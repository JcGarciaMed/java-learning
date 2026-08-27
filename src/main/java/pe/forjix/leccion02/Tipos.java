package pe.forjix.leccion02;

/**
 * Archivo de referencia de la Lección 2. Ejecútalo y compara la salida
 * con lo que TÚ esperabas antes de correrlo. Ahí está el aprendizaje.
 */
public class Tipos {

    // Constante de clase: 'static final' en MAYÚSCULAS_CON_GUION_BAJO.
    // Es la única convención de Java que no usa camelCase.
    private static final double IGV = 0.18;

    public static void main(String[] args) {

        // ---- 1. Los primitivos que realmente usarás ----
        int cantidad = 3;
        long poblacionMundial = 8_100_000_000L;   // el guion bajo es solo legibilidad
        double precio = 19.90;
        boolean activo = true;
        char inicial = 'J';                       // comilla SIMPLE: char. Doble: String

        System.out.println("--- Primitivos ---");
        System.out.println(cantidad + " | " + poblacionMundial + " | " + precio
                + " | " + activo + " | " + inicial);

        // ---- 2. La imprecisión del double ----
        System.out.println("\n--- Imprecisión binaria ---");
        System.out.println("0.1 + 0.2 = " + (0.1 + 0.2));   // NO da 0.3

        // ---- 3. División entera: la trampa clásica ----
        System.out.println("\n--- División ---");
        System.out.println("7 / 2   = " + (7 / 2));         // 3   (int / int -> int)
        System.out.println("7 / 2.0 = " + (7 / 2.0));       // 3.5 (int / double -> double)
        System.out.println("7 % 2   = " + (7 % 2));         // 1   (el resto)

        // ---- 4. Precedencia del '+' con Strings ----
        System.out.println("\n--- Precedencia ---");
        System.out.println("Suma: " + 1 + 2);               // "Suma: 12"  <- izq. a derecha
        System.out.println("Suma: " + (1 + 2));             // "Suma: 3"   <- correcto

        // ---- 5. Casting ----
        System.out.println("\n--- Casting ---");
        int entero = 5;
        double aDouble = entero;                            // implícito (widening), seguro
        double pi = 3.99;
        int truncado = (int) pi;                            // explícito (narrowing), pierde datos
        System.out.println("int->double: " + aDouble + " | (int) 3.99 = " + truncado);

        // ---- 6. var: inferencia, NO tipado dinámico ----
        var subtotal = 100.0;             // el compilador fija: double
        var total = subtotal * (1 + IGV);
        // subtotal = "texto";            // <- descomenta esto: NO COMPILA. Sigue siendo double.
        System.out.println("\nTotal con IGV: " + total);

        // ---- 7. Primitivo vs objeto: null ----
        Integer puedeSerNulo = null;      // un objeto SÍ puede ser null
        // int noPuedeSerNulo = null;     // <- descomenta: NO COMPILA
        System.out.println("Wrapper nulo: " + puedeSerNulo);

        // ---- 8. Cortocircuito lógico (&& y ||) ----
        // Si el lado izquierdo ya decide el resultado, el derecho NI SE EVALÚA.
        // Por eso este orden evita una división por cero:
        int divisor = 0;
        if (divisor != 0 && 10 / divisor > 1) {
            System.out.println("nunca llega aquí");
        }
        System.out.println("\nSin explotar: el cortocircuito salvó la división por cero.");
    }
}
