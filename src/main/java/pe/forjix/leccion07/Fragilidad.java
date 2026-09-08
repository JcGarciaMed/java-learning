package pe.forjix.leccion07;

import java.util.ArrayList;
import java.util.List;

/**
 * Referencia de la Leccion 7, parte 2:
 * por que la herencia acopla a los DETALLES INTERNOS del padre.
 */
public class Fragilidad {

    // =================================================================
    // Una bolsa sencilla. Su API publica: add(x) y addAll(coleccion).
    // =================================================================
    static class Bolsa {
        private final List<String> items = new ArrayList<>();

        public void add(String item) {
            items.add(item);
        }

        public void addAll(List<String> nuevos) {
            for (final String n : nuevos) {
                add(n);            // <-- DETALLE INTERNO: addAll se apoya en add()
            }
        }

        public int size() {
            return items.size();
        }
    }

    // =================================================================
    // HERENCIA: quiero lo mismo, pero contando cuantos elementos se anadieron.
    // Parece impecable... y esta roto.
    // =================================================================
    static class BolsaContadoraHerencia extends Bolsa {
        private int anadidos = 0;

        @Override
        public void add(String item) {
            anadidos++;
            super.add(item);
        }

        @Override
        public void addAll(List<String> nuevos) {
            anadidos += nuevos.size();
            super.addAll(nuevos);       // ...que por dentro llama a add(), ya sobrescrito
        }

        public int anadidos() {
            return anadidos;
        }
    }

    // =================================================================
    // COMPOSICION: en vez de SER una Bolsa, TENGO una Bolsa.
    // =================================================================
    static class BolsaContadoraComposicion {
        private final Bolsa bolsa = new Bolsa();     // relacion "tiene un"
        private int anadidos = 0;

        public void add(String item) {
            anadidos++;
            bolsa.add(item);
        }

        public void addAll(List<String> nuevos) {
            anadidos += nuevos.size();
            bolsa.addAll(nuevos);        // llama a la API PUBLICA; no hereda nada
        }

        public int anadidos() {
            return anadidos;
        }

        public int size() {
            return bolsa.size();
        }
    }

    public static void main(String[] args) {
        final List<String> tres = List.of("a", "b", "c");

        final BolsaContadoraHerencia h = new BolsaContadoraHerencia();
        h.addAll(tres);

        final BolsaContadoraComposicion c = new BolsaContadoraComposicion();
        c.addAll(tres);

        System.out.println("Se anadieron 3 elementos con addAll().\n");
        System.out.printf("  HERENCIA    -> size=%d  contados=%d   %s%n",
                h.size(), h.anadidos(), h.anadidos() == 3 ? "OK" : "<-- MAL");
        System.out.printf("  COMPOSICION -> size=%d  contados=%d   %s%n",
                c.size(), c.anadidos(), c.anadidos() == 3 ? "OK" : "<-- MAL");

        System.out.println("""
                
                Por que falla la herencia:
                  addAll() suma 3 al contador y llama a super.addAll(),
                  que por dentro llama a add() -- pero add() esta SOBRESCRITO,
                  asi que suma 3 veces mas. Total: 6.
                
                La subclase no depende solo de la API de Bolsa:
                depende de COMO esta implementada por dentro.
                Si manana Bolsa deja de usar add() dentro de addAll(),
                la subclase cambia de comportamiento sin que nadie la toque.
                """);
    }
}
