package com.jorge.learning.module01_fundamentos;

import java.util.*;

public class Inventario {
    public record Producto(String codigo, String nombre, double precio, int stock) {}
    private final Map<String, Producto> productos = new HashMap<>();

    public void agregar(Producto producto) {
        if (Objects.isNull(producto) || Objects.isNull(producto.codigo) ||  producto.codigo.isBlank()) {
            throw new IllegalArgumentException("Producto no válido");
        }
        productos.put(producto.codigo, producto);
    }

    public Optional<Producto> buscar(String codigo) {
        return Optional.ofNullable(productos.get(codigo));
    }

    public boolean eliminar(String codigo) {
        return Optional.ofNullable(productos.remove(codigo)).isPresent();
    }

    public Producto actualizarStock(String codigo, int stock) {
        var producto = buscar(codigo).orElseThrow(() -> new IllegalArgumentException("Producto no encontrado " + codigo));

        Producto productoActualizado = new Producto(producto.codigo, producto.nombre, producto.precio, stock);

        agregar(productoActualizado);

        return productoActualizado;

    }

    public List<Producto> listarTodos() {
        return new  ArrayList<>(productos.values());
    }

    public int cantidadProductos() {
        return productos.size();
    }

    public List<String> listarCodigosInmutable() {
        return List.copyOf(productos.keySet());
    }
}
