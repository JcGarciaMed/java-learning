package pe.forjix.leccion11;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * Referencia de la Leccion 11: List, Set, Map, Deque, inmutabilidad,
 * complejidad y las trampas habituales.
 */
public class Colecciones {

    record Parcel(String code, String city, double weightKg) { }

    public static void main(String[] args) {
        demoList();
        demoSetOrden();
        demoMap();
        demoDeque();
        demoInmutables();
        demoModificarMientrasRecorres();
        demoOrdenarConComparator();
        demoComplejidad();
    }

    // ---- 1. List: crece sola. Adios removeNulls ----
    private static void demoList() {
        System.out.println("=== List ===");
        final List<String> codes = new ArrayList<>();
        codes.add("ST-1");
        codes.add("EX-2");
        codes.add("ST-1");                       // admite duplicados
        codes.add(1, "IN-9");                    // insertar en una posicion
        System.out.println("  " + codes + "  size=" + codes.size());
        System.out.println("  get(0)=" + codes.get(0) + "  indexOf(EX-2)=" + codes.indexOf("EX-2"));
    }

    // ---- 2. Set: tres implementaciones, tres garantias de orden ----
    private static void demoSetOrden() {
        System.out.println("\n=== Set: sin duplicados, y el orden depende de la implementacion ===");
        final List<String> entrada = List.of("Lima", "Cusco", "Arequipa", "Lima", "Piura", "Cusco");

        final Set<String> hash = new HashSet<>(entrada);
        final Set<String> linked = new LinkedHashSet<>(entrada);
        final Set<String> tree = new TreeSet<>(entrada);

        System.out.println("  entrada       : " + entrada);
        System.out.println("  HashSet       : " + hash + "   <- orden NO garantizado");
        System.out.println("  LinkedHashSet : " + linked + "   <- orden de insercion");
        System.out.println("  TreeSet       : " + tree + "   <- siempre ordenado");
    }

    // ---- 3. Map: contar y agrupar sin if/else ----
    private static void demoMap() {
        System.out.println("\n=== Map: merge y computeIfAbsent ===");
        final List<Parcel> parcels = List.of(
                new Parcel("P1", "Lima", 2.0),
                new Parcel("P2", "Cusco", 5.5),
                new Parcel("P3", "Lima", 1.2),
                new Parcel("P4", "Piura", 8.0),
                new Parcel("P5", "Lima", 3.3));

        // Contar por ciudad
        final Map<String, Integer> countByCity = new TreeMap<>();     // TreeMap: claves ordenadas
        for (final Parcel p : parcels) {
            countByCity.merge(p.city(), 1, Integer::sum);
        }
        System.out.println("  conteo   : " + countByCity);

        // Agrupar por ciudad
        final Map<String, List<Parcel>> byCity = new HashMap<>();
        for (final Parcel p : parcels) {
            byCity.computeIfAbsent(p.city(), k -> new ArrayList<>()).add(p);
        }
        System.out.println("  Lima     : " + byCity.get("Lima").size() + " paquetes");

        // Recorrer un Map: entrySet da clave y valor a la vez
        System.out.println("  recorrido:");
        for (final Map.Entry<String, Integer> e : countByCity.entrySet()) {
            System.out.printf("    %-8s -> %d%n", e.getKey(), e.getValue());
        }

        System.out.println("  getOrDefault(Tacna, 0) = " + countByCity.getOrDefault("Tacna", 0));
    }

    // ---- 4. Deque: cola Y pila. Nunca Stack ----
    private static void demoDeque() {
        System.out.println("\n=== ArrayDeque como COLA (FIFO) ===");
        final Deque<String> cola = new ArrayDeque<>();
        cola.offerLast("primero");
        cola.offerLast("segundo");
        cola.offerLast("tercero");
        System.out.println("  sale: " + cola.pollFirst() + ", luego: " + cola.pollFirst());

        System.out.println("=== ArrayDeque como PILA (LIFO) ===");
        final Deque<String> pila = new ArrayDeque<>();
        pila.push("abajo");
        pila.push("medio");
        pila.push("arriba");
        System.out.println("  sale: " + pila.pop() + ", luego: " + pila.pop());
    }

    // ---- 5. Inmutables: vista vs copia ----
    private static void demoInmutables() {
        System.out.println("\n=== Inmutables: vista vs copia ===");
        final List<String> original = new ArrayList<>(List.of("a", "b"));
        final List<String> vista = Collections.unmodifiableList(original);
        final List<String> copia = List.copyOf(original);

        original.add("c");                        // modifico el ORIGINAL
        System.out.println("  original : " + original);
        System.out.println("  vista    : " + vista + "   <- se entero del cambio");
        System.out.println("  copia    : " + copia + "      <- independiente");

        try {
            copia.add("x");
        } catch (UnsupportedOperationException e) {
            System.out.println("  copia.add() -> UnsupportedOperationException");
        }
    }

    // ---- 6. La trampa: modificar mientras recorres ----
    private static void demoModificarMientrasRecorres() {
        System.out.println("\n=== Modificar mientras recorres ===");
        final List<String> lista = new ArrayList<>(List.of("ok", "", "ok", "  ", "ok"));
        try {
            for (final String s : lista) {
                if (s.isBlank()) {
                    lista.remove(s);
                }
            }
        } catch (java.util.ConcurrentModificationException e) {
            System.out.println("  for-each + remove -> ConcurrentModificationException");
        }

        final List<String> limpia = new ArrayList<>(List.of("ok", "", "ok", "  ", "ok"));
        limpia.removeIf(String::isBlank);
        System.out.println("  removeIf          -> " + limpia);
    }

    // ---- 7. Ordenar: Comparator compuesto ----
    private static void demoOrdenarConComparator() {
        System.out.println("\n=== Ordenar con Comparator ===");
        final List<Parcel> parcels = new ArrayList<>(List.of(
                new Parcel("P1", "Lima", 2.0),
                new Parcel("P2", "Cusco", 5.5),
                new Parcel("P3", "Lima", 9.1),
                new Parcel("P4", "Cusco", 1.0)));

        // Por ciudad y, dentro de cada ciudad, del mas pesado al mas ligero
        parcels.sort(Comparator.comparing(Parcel::city)
                .thenComparing(Parcel::weightKg, Comparator.reverseOrder()));

        for (final Parcel p : parcels) {
            System.out.printf("  %-6s %-3s %5.1f kg%n", p.city(), p.code(), p.weightKg());
        }
    }

    // ---- 8. Complejidad: contains en List vs Set ----
    private static void demoComplejidad() {
        System.out.println("\n=== contains(): List O(n) vs HashSet O(1) ===");
        final int n = 100_000;
        final List<Integer> lista = new ArrayList<>();
        final Set<Integer> set = new HashSet<>();
        for (int i = 0; i < n; i++) {
            lista.add(i);
            set.add(i);
        }

        final int busquedas = 10_000;

        long t0 = System.nanoTime();
        int hitsLista = 0;
        for (int i = 0; i < busquedas; i++) {
            if (lista.contains(n - 1 - i)) {     // busco cerca del final: el peor caso
                hitsLista++;
            }
        }
        final long tLista = System.nanoTime() - t0;

        t0 = System.nanoTime();
        int hitsSet = 0;
        for (int i = 0; i < busquedas; i++) {
            if (set.contains(n - 1 - i)) {
                hitsSet++;
            }
        }
        final long tSet = System.nanoTime() - t0;

        System.out.printf("  %,d busquedas en %,d elementos%n", busquedas, n);
        System.out.printf("  ArrayList.contains : %,10.2f ms  (%d encontrados)%n", tLista / 1e6, hitsLista);
        System.out.printf("  HashSet.contains   : %,10.2f ms  (%d encontrados)%n", tSet / 1e6, hitsSet);
        System.out.printf("  ratio              : %,.0fx%n", (double) tLista / tSet);
    }
}
