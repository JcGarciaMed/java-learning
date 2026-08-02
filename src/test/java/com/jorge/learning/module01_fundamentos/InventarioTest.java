package com.jorge.learning.module01_fundamentos;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

class InventarioTest {

    private Inventario inventario;

    @BeforeEach
    void setUp() {
        inventario = new Inventario();
        Inventario.Producto producto = new Inventario.Producto("P001", "Lapiceros", 140.58, 26);
        inventario.agregar(producto);
    }

    @Test
    void agregaProductoCorrectamente() {
        Inventario.Producto productoAgregado = inventario.buscar("P001").orElse(null);
        assertThat(productoAgregado).isNotNull();
        assertThat(productoAgregado.codigo()).isEqualTo("P001");
    }

    @Test
    void agregaProductoNuloLanzarError() {
        assertThatThrownBy(() -> inventario.agregar(null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void agregaProductoCodigoVacioLanzarError() {
        assertThatThrownBy(() -> inventario.agregar(new Inventario.Producto("", "Lapiceros", 140.58, 26)))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void buscarProducto() {
        assertThat(inventario.buscar("P001").orElse(null)).isNotNull();
        assertThat(inventario.buscar("P001").get().codigo()).isEqualTo("P001");
    }

    @Test
    void buscarProductoInexistente() {
        assertThat(inventario.buscar("P002").orElse(null)).isNull();
        assertThat(inventario.buscar("P002")).isEqualTo(Optional.empty());
    }

    @Test
    void actualizarStock() {
        assertThat(inventario.actualizarStock("P001", 12).stock()).isEqualTo(12);
    }

    @Test
    void actualizarStockProductoInvalido() {
        assertThatThrownBy(() -> inventario.actualizarStock("P002", 12))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void listarProductos() {
        assertThat(inventario.listarTodos().isEmpty()).isFalse();
        assertThat(inventario.listarTodos().size()).isEqualTo(1);
    }

    @Test
    void listarCodigosInmutable(){
        assertThat(inventario.listarCodigosInmutable().isEmpty()).isFalse();
        assertThat(inventario.listarCodigosInmutable().size()).isEqualTo(1);
    }

    @Test
    void cambiarListaInmutable(){
        List<String> codigosInmutable = inventario.listarCodigosInmutable();
        assertThatThrownBy(() -> codigosInmutable.add("P002"))
                .isInstanceOf(UnsupportedOperationException.class);
    }

    @Test
    void listarTodosNoModificaInventarioInterno(){
        List<Inventario.Producto> productos = inventario.listarTodos();
        productos.clear();
        assertThat(inventario.cantidadProductos()).isEqualTo(1 );
    }

    @Test
    void eliminarProductoInvalido() {
        assertThat(inventario.eliminar("P002")).isFalse();
    }


    @Test
    void eliminarProducto() {
        assertThat(inventario.eliminar("P001")).isTrue();
    }



}