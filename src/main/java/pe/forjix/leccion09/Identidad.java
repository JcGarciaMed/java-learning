package pe.forjix.leccion09;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Referencia de la Leccion 9, parte 1:
 * el contrato equals/hashCode y por que romperlo NO da error, solo resultados falsos.
 */
public class Identidad {

    // =================================================================
    // A) Sin equals ni hashCode: dos objetos con el mismo contenido
    //    son DISTINTOS, porque Object compara referencias.
    // =================================================================
    static final class PuntoSinNada {
        private final int x;
        private final int y;

        PuntoSinNada(int x, int y) { this.x = x; this.y = y; }
    }

    // =================================================================
    // B) equals SIN hashCode: el error mas peligroso de los tres.
    //    Compila, parece correcto, y rompe las colecciones en silencio.
    // =================================================================
    static final class PuntoSoloEquals {
        private final int x;
        private final int y;

        PuntoSoloEquals(int x, int y) { this.x = x; this.y = y; }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;                       // atajo: misma referencia
            if (!(o instanceof PuntoSoloEquals otro)) return false;  // pattern matching (Leccion 10)
            return x == otro.x && y == otro.y;
        }
        // FALTA hashCode() a proposito.
    }

    // =================================================================
    // C) Bien hecho: equals + hashCode coherentes.
    // =================================================================
    static final class PuntoCorrecto {
        private final int x;
        private final int y;

        PuntoCorrecto(int x, int y) { this.x = x; this.y = y; }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof PuntoCorrecto otro)) return false;
            return x == otro.x && y == otro.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);      // mismos campos que en equals, SIEMPRE
        }

        @Override
        public String toString() {
            return "PuntoCorrecto[x=%d, y=%d]".formatted(x, y);
        }
    }

    // =================================================================
    // D) record: Java genera equals, hashCode y toString por ti.
    // =================================================================
    record PuntoRecord(int x, int y) { }

    // =================================================================
    public static void main(String[] args) {

        System.out.println("=== A) Sin equals: identidad por referencia ===");
        final PuntoSinNada a1 = new PuntoSinNada(1, 2);
        final PuntoSinNada a2 = new PuntoSinNada(1, 2);
        System.out.println("  a1.equals(a2) : " + a1.equals(a2));   // false
        System.out.println("  toString()    : " + a1);              // basura ilegible

        System.out.println("\n=== B) equals SIN hashCode: el bug silencioso ===");
        final PuntoSoloEquals b1 = new PuntoSoloEquals(1, 2);
        final PuntoSoloEquals b2 = new PuntoSoloEquals(1, 2);
        System.out.println("  b1.equals(b2)   : " + b1.equals(b2));   // true  <- parece bien
        System.out.println("  hashCode iguales: " + (b1.hashCode() == b2.hashCode()));

        final Set<PuntoSoloEquals> setB = new HashSet<>();
        setB.add(b1);
        System.out.println("  setB.contains(b2) : " + setB.contains(b2) + "   <-- deberia ser true");
        setB.add(b2);
        System.out.println("  tamano del Set    : " + setB.size() + "   <-- deberia ser 1");
        System.out.println("  El Set tiene DOS copias de un punto que el mismo considera igual.");

        System.out.println("\n=== C) equals + hashCode: funciona ===");
        final PuntoCorrecto c1 = new PuntoCorrecto(1, 2);
        final PuntoCorrecto c2 = new PuntoCorrecto(1, 2);
        final Set<PuntoCorrecto> setC = new HashSet<>();
        setC.add(c1);
        setC.add(c2);
        System.out.println("  setC.contains(c2) : " + setC.contains(c2));
        System.out.println("  tamano del Set    : " + setC.size());
        System.out.println("  toString()        : " + c1);

        System.out.println("\n=== D) record: los tres metodos, gratis ===");
        final PuntoRecord d1 = new PuntoRecord(1, 2);
        final PuntoRecord d2 = new PuntoRecord(1, 2);
        final Set<PuntoRecord> setD = new HashSet<>();
        setD.add(d1);
        setD.add(d2);
        System.out.println("  d1.equals(d2)     : " + d1.equals(d2));
        System.out.println("  tamano del Set    : " + setD.size());
        System.out.println("  toString()        : " + d1);

        System.out.println("""

                Moraleja: el caso B no lanza ninguna excepcion ni avisa de nada.
                Simplemente el Set responde que un elemento no esta cuando si esta.
                Por eso la regla es absoluta: si redefines equals, redefine hashCode.
                Y si solo quieres un contenedor de datos, usa un record y olvidate.
                """);
    }
}
