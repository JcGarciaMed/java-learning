package pe.forjix.leccion09;

import java.util.Objects;

public final class Warehouse {
    private final String code;      // identidad: "LIM-01"
    private final String name;      // descripción, puede cambiar
    private final int capacity;

    public Warehouse(String code, String name, int capacity) {
        Objects.requireNonNull(code);
        Objects.requireNonNull(name);
        if (code.isBlank() || name.isBlank()) {
            throw new IllegalArgumentException("Invalid warehouse");
        }
        this.code = code;
        this.name = name;
        this.capacity = capacity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Warehouse otro)) return false;
        return Objects.equals(code, otro.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code);
    }
}