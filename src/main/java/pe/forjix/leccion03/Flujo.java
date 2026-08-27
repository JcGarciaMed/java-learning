package pe.forjix.leccion03;

/**
 * Referencia de la Lección 3: condicionales, switch moderno, bucles y arrays.
 */
public class Flujo {

    public static void main(String[] args) {

        // ---- 1. Ternario: un if/else que PRODUCE un valor ----
        final int score = 14;
        final String status = score >= 11 ? "Aprobado" : "Desaprobado";
        System.out.println("Ternario -> " + score + ": " + status);

        // ---- 2. switch expression (Java 14+) ----
        // Sin break, sin caída entre casos, y devuelve un valor.
        final int dayNumber = 6;
        final String dayType = switch (dayNumber) {
            case 1, 2, 3, 4, 5 -> "Laborable";
            case 6, 7          -> "Fin de semana";
            default            -> "Número de día inválido";
        };
        System.out.println("switch  -> día " + dayNumber + ": " + dayType);

        // Rama de varias líneas: llaves + 'yield' para devolver el valor.
        final String detail = switch (dayNumber) {
            case 7 -> {
                System.out.println("          (domingo detectado)");
                yield "Descanso total";
            }
            default -> "Día normal";
        };
        System.out.println("yield   -> " + detail);

        // ---- 3. Arrays ----
        final int[] scores = {15, 8, 18, 11, 20, 7};
        System.out.println("\nArray de " + scores.length + " elementos.");  // .length SIN ()

        // for-each: cuando solo necesitas los VALORES. Es la opción por defecto.
        int sum = 0;
        for (final int s : scores) {
            sum += s;                       // equivale a: sum = sum + s
        }
        System.out.println("for-each -> suma = " + sum);

        // El divisor es un int (length), así que hay que castear para no truncar.
        final double average = (double) sum / scores.length;
        System.out.println("promedio = " + average);

        // for indexado: cuando necesitas la POSICIÓN.
        System.out.println("\nfor indexado:");
        for (int i = 0; i < scores.length; i++) {
            System.out.println("  Nota " + (i + 1) + ": " + scores[i]);
            //                            ^^^^^^^ paréntesis obligatorios:
            // sin ellos, "Nota " + i + 1 concatenaría el 1 como texto.
        }

        // ---- 4. break y continue ----
        System.out.println("\nPrimer desaprobado encontrado:");
        for (final int s : scores) {
            if (s >= 11) {
                continue;                   // salta a la siguiente vuelta
            }
            System.out.println("  " + s);
            break;                          // abandona el bucle
        }

        // ---- 5. while: cuando no sabes cuántas vueltas ----
        int remaining = 3;
        while (remaining > 0) {
            System.out.println("\nQuedan " + remaining);
            remaining--;                    // sin esto: bucle infinito
        }
    }
}
