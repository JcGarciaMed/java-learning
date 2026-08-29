package pe.forjix.leccion04;

import java.util.Arrays;

/**
 * Referencia de la Lección 4: métodos, paso por valor, sobrecarga y scope.
 */
public class Metodos {

    // Constante de clase: una sola fuente de verdad para la regla de negocio.
    private static final int MIN_PASSING_SCORE = 11;

    public static void main(String[] args) {
        final int[] scores = {15, 8, 18, 11, 20, 7, 13};

        // Fíjate en cómo se LEE este main: es un resumen, no una implementación.
        System.out.println("Promedio  : " + average(scores));
        System.out.println("Aprobados : " + countPassing(scores));
        System.out.println("¿15 pasa? : " + isPassing(15));

        demoPassByValue();
        demoOverloading();
        demoScope();
    }

    // ---- 1. Un método = una responsabilidad ----

    /** Devuelve el promedio. El cast va en el punto exacto donde hace falta. */
    private static double average(int[] values) {
        int sum = 0;
        for (final int v : values) {
            sum += v;
        }
        return (double) sum / values.length;
    }

    /** Nombre con 'is': el lector sabe que devuelve boolean sin mirar la firma. */
    private static boolean isPassing(int score) {
        return score >= MIN_PASSING_SCORE;
    }

    /** Reutiliza isPassing: la regla de aprobación vive en UN solo lugar. */
    private static int countPassing(int[] values) {
        int count = 0;
        for (final int v : values) {
            if (isPassing(v)) {
                count++;
            }
        }
        return count;
    }

    // ---- 2. Paso por valor: SIEMPRE se copia el valor de la variable ----

    private static void demoPassByValue() {
        System.out.println("\n--- Paso por valor ---");

        int number = 5;
        tryToChange(number);
        System.out.println("primitivo tras tryToChange : " + number);   // sigue 5

        int[] array = {1, 2, 3};
        mutate(array);
        System.out.println("objeto tras mutate         : " + Arrays.toString(array)); // [99, 2, 3]

        reassign(array);
        System.out.println("objeto tras reassign       : " + Arrays.toString(array)); // sin cambios
    }

    /** Recibe una COPIA del int. Reasignarla no toca al original. */
    private static void tryToChange(int n) {
        n = 99;
    }

    /** Recibe una COPIA de la REFERENCIA. Apunta al mismo array -> sí lo modifica. */
    private static void mutate(int[] arr) {
        arr[0] = 99;
    }

    /** Reasignar la copia de la referencia NO cambia a dónde apunta el original. */
    private static void reassign(int[] arr) {
        arr = new int[]{-1, -1, -1};
        // Aquí dentro 'arr' apunta a otro array; fuera nadie se entera.
    }

    // ---- 3. Sobrecarga: mismo nombre, distintos parámetros ----

    private static void demoOverloading() {
        System.out.println("\n--- Sobrecarga ---");
        System.out.println("describe(int)    : " + describe(42));
        System.out.println("describe(double) : " + describe(4.2));
        System.out.println("describe(String) : " + describe("hola"));
        System.out.println("describe(varargs): " + describe(1, 2, 3));
    }

    private static String describe(int value)    { return "un entero: " + value; }
    private static String describe(double value) { return "un decimal: " + value; }
    private static String describe(String value) { return "un texto de " + value.length() + " chars"; }

    /** varargs: 'int...' acepta 0, 1 o N argumentos. Dentro se comporta como un array. */
    private static String describe(int... values) {
        return values.length + " enteros sueltos";
    }

    // ---- 4. Scope: dónde vive cada variable ----

    private static void demoScope() {
        System.out.println("\n--- Scope ---");
        final int outer = 1;

        for (int i = 0; i < 2; i++) {
            final int inner = outer + i;   // 'inner' e 'i' solo existen dentro del for
            System.out.println("  inner = " + inner);
        }
        // System.out.println(inner);      // <- descomenta: NO COMPILA, 'inner' murió
        System.out.println("  outer sigue vivo = " + outer);
    }
}
