package pe.forjix.leccion10;

/**
 * Referencia de la Leccion 10: instanceof moderno, switch con patrones,
 * guards, record patterns, sealed y el problema de la expresion.
 */
public class Patrones {

    // =================================================================
    // 1. Jerarquia SELLADA: estas son TODAS las variantes posibles.
    //    Cada una con datos de forma distinta (cosa que un enum no puede).
    // =================================================================
    sealed interface Expr permits Num, Add, Mul, Neg {
    }

    record Num(double value) implements Expr {
    }

    record Add(Expr left, Expr right) implements Expr {
    }

    record Mul(Expr left, Expr right) implements Expr {
    }

    record Neg(Expr inner) implements Expr {
    }

    // =================================================================
    // 2. OPERACIONES: cada una es una funcion con un switch exhaustivo.
    //    Anadir una operacion nueva = anadir un metodo. Los records no se tocan.
    // =================================================================

    /**
     * Evaluar: record patterns, desestructurando cada variante.
     */
    static double eval(Expr e) {
        return switch (e) {
            case Num(double v) -> v;
            case Add(Expr l, Expr r) -> eval(l) + eval(r);
            case Mul(Expr l, Expr r) -> eval(l) * eval(r);
            case Neg(Expr inner) -> -eval(inner);
            // SIN default: si anades una variante al 'permits', esto no compila.
        };
    }

    /**
     * Imprimir: otra operacion, cero cambios en los tipos.
     */
    static String show(Expr e) {
        return switch (e) {
            case Num(double v) -> v == Math.floor(v) ? String.valueOf((long) v) : String.valueOf(v);
            case Add(Expr l, Expr r) -> "(" + show(l) + " + " + show(r) + ")";
            case Mul(Expr l, Expr r) -> show(l) + " * " + show(r);
            case Neg(Expr inner) -> "-" + show(inner);
        };
    }

    /**
     * Simplificar: aqui brillan los patrones ANIDADOS y los GUARDS.
     * Cada regla algebraica es una linea que se lee como la regla misma.
     */
    static Expr simplify(Expr e) {
        return switch (e) {
            // x * 0 = 0   y   0 * x = 0
            case Mul(Expr l, Num(double v)) when v == 0 -> new Num(0);
            case Mul(Num(double v), Expr r) when v == 0 -> new Num(0);
            // x * 1 = x   y   1 * x = x
            case Mul(Expr l, Num(double v)) when v == 1 -> simplify(l);
            case Mul(Num(double v), Expr r) when v == 1 -> simplify(r);
            // x + 0 = x
            case Add(Expr l, Num(double v)) when v == 0 -> simplify(l);
            // --x = x   (patron anidado: un Neg cuyo interior es otro Neg)
            case Neg(Neg(Expr inner)) -> simplify(inner);
            // Casos generales: simplificar recursivamente las partes
            case Add(Expr l, Expr r) -> new Add(simplify(l), simplify(r));
            case Mul(Expr l, Expr r) -> new Mul(simplify(l), simplify(r));
            case Neg(Expr inner) -> new Neg(simplify(inner));
            case Num n -> n;
        };
    }

    // =================================================================
    // 3. instanceof moderno y switch sobre Object (jerarquia ABIERTA)
    // =================================================================
    static String describir(Object o) {
        return switch (o) {
            case null -> "nulo (antes esto era un NullPointerException)";
            case Integer i when i > 100 -> "entero grande: " + i;
            case Integer i -> "entero: " + i;
            case String s -> "texto de " + s.length() + " caracteres";
            case Expr ex -> "una expresion que vale " + eval(ex);
            default -> "otra cosa: " + o.getClass().getSimpleName();
            // Aqui SI hace falta default: Object no esta sellado.
        };
    }

    // =================================================================
    public static void main(String[] args) {

        // (2 + 3) * 4
        final Expr e1 = new Mul(new Add(new Num(2), new Num(3)), new Num(4));
        // ((x * 1) + 0) con x = --7
        final Expr e2 = new Add(new Mul(new Neg(new Neg(new Num(7))), new Num(1)), new Num(0));
        // (5 + 2) * 0
        final Expr e3 = new Mul(new Add(new Num(5), new Num(2)), new Num(0));

        System.out.println("=== Tres operaciones sobre la misma jerarquia sellada ===");
        for (final Expr e : new Expr[]{e1, e2, e3}) {
            System.out.printf("  %-26s = %6.1f   simplificada: %s%n",
                    show(e), eval(e), show(simplify(e)));
        }

        System.out.println("\n=== El record se imprime solo (toString generado) ===");
        System.out.println("  " + e1);

        System.out.println("\n=== switch sobre Object: jerarquia abierta, necesita default ===");
        for (final Object o : new Object[]{42, 500, "Jorge", e1, 3.14, null}) {
            System.out.println("  " + describir(o));
        }

        System.out.println("""
                
                Fijate: anadir 'simplify' no obligo a tocar Num, Add, Mul ni Neg.
                Con polimorfismo, habria que anadir un metodo a cada uno de los cuatro.
                A cambio, anadir una variante nueva (p.ej. Div) obliga a tocar los
                tres switch -- y el compilador te dice exactamente donde.
                """);
    }
}
