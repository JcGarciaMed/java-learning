package pe.forjix.leccion05;

import java.util.Arrays;
import java.util.Objects;

/**
 * Referencia de la Lección 5: inmutabilidad, == vs equals, StringBuilder,
 * text blocks y formateo.
 */
public class Texto {

    public static void main(String[] args) {
        demoInmutabilidad();
        demoIgualdad();
        demoMetodosEsenciales();
        demoStringBuilder();
        demoTextBlockYFormato();
    }

    // ---- 1. Un String NUNCA cambia ----
    private static void demoInmutabilidad() {
        System.out.println("--- Inmutabilidad ---");

        String name = "jorge";
        name.toUpperCase();                 // el resultado se tira a la basura
        System.out.println("sin reasignar : " + name);   // sigue "jorge"

        name = name.toUpperCase();          // hay que reasignar
        System.out.println("reasignando   : " + name);   // "JORGE"
    }

    // ---- 2. == compara referencias, equals compara contenido ----
    private static void demoIgualdad() {
        System.out.println("\n--- == vs equals ---");

        final String a = "Java";
        final String b = "Java";                  // el compilador reutiliza el literal (string pool)
        final String c = new String("Java");      // 'new' fuerza un objeto NUEVO

        System.out.println("a == b       : " + (a == b));        // true  <- engañoso
        System.out.println("a == c       : " + (a == c));        // false <- la realidad
        System.out.println("a.equals(c)  : " + a.equals(c));     // true  <- lo correcto

        // El mismo texto, construido en tiempo de EJECUCIÓN: el pool no aplica.
        final String runtime = build("Ja", "va");
        System.out.println("a == runtime : " + (a == runtime));  // false
        System.out.println("a.equals(rt) : " + a.equals(runtime));

        // Blindaje contra null: el literal va PRIMERO.
        final String role = null;
        System.out.println("literal.equals(null) : " + "ADMIN".equals(role));  // false, no explota
        System.out.println("Objects.equals       : " + Objects.equals(role, "ADMIN"));
        // System.out.println(role.equals("ADMIN"));  // <- descomenta: NullPointerException
    }

    private static String build(String left, String right) {
        return left + right;
    }

    // ---- 3. Los métodos que usarás todos los días ----
    private static void demoMetodosEsenciales() {
        System.out.println("\n--- Métodos esenciales ---");

        final String raw = "   Jorge,Garcia,15   ";

        System.out.println("strip()        : [" + raw.strip() + "]");   // strip > trim (Unicode)
        System.out.println("length()       : " + raw.strip().length()); // CON paréntesis
        System.out.println("toUpperCase()  : " + raw.strip().toUpperCase());
        System.out.println("contains()     : " + raw.contains("Garcia"));
        System.out.println("indexOf(',')   : " + raw.strip().indexOf(','));
        System.out.println("substring(0,5) : " + raw.strip().substring(0, 5));
        System.out.println("replace()      : " + raw.strip().replace(',', '|'));
        System.out.println("isBlank()      : " + "   ".isBlank());      // true: solo espacios
        System.out.println("repeat(3)      : " + "ab".repeat(3));

        // split devuelve un ARRAY
        final String[] parts = raw.strip().split(",");
        System.out.println("split -> " + parts.length + " partes, la 2ª es: " + parts[1]);

        // join es la operación inversa
        System.out.println("join  -> " + String.join(" | ", parts));
    }

    // ---- 4. Concatenar en bucle: StringBuilder ----
    private static void demoStringBuilder() {
        System.out.println("\n--- StringBuilder ---");

        final String[] words = {"Java", "es", "inmutable"};

        // ❌ Crea un String nuevo en CADA vuelta. Con 1000 palabras es un problema real.
        String slow = "";
        for (final String w : words) {
            slow += w + " ";
        }

        // ✅ Un solo buffer mutable que crece.
        final StringBuilder sb = new StringBuilder();
        for (final String w : words) {
            sb.append(w).append(' ');       // append devuelve el propio sb -> se encadena
        }

        System.out.println("con + en bucle   : " + slow.strip());
        System.out.println("con StringBuilder: " + sb.toString().strip());
    }

    // ---- 5. Text blocks y formateo ----
    private static void demoTextBlockYFormato() {
        System.out.println("\n--- Text block ---");

        // Java calcula la indentación mínima y la quita: la sangría del código no ensucia el texto.
        final String csv = """
                Jorge,15
                Maria,18
                Luis,8
                """;
        System.out.print(csv);

        System.out.println("\n--- Formateo en columnas ---");
        System.out.printf("%-10s %6s %8s%n", "ALUMNO", "NOTA", "ESTADO");
        for (final String line : csv.strip().split("\n")) {
            final String[] cell = line.split(",");
            if (Arrays.stream(cell).toList().isEmpty()) {
                continue;
            }
            final String student = cell[0];
            final int score = Integer.parseInt(cell[1]);   // texto -> número
            //                ^^^^^^^^^^^^^^^^ explota si el texto no es un número
            System.out.printf("%-10s %6d %8s%n", student, score, score >= 11 ? "OK" : "FAIL");
        }
        //  %-10s -> texto, 10 posiciones, alineado a la IZQUIERDA
        //  %6d   -> entero, 6 posiciones, alineado a la DERECHA
        //  %n    -> salto de línea portable (mejor que \n)
    }
}
