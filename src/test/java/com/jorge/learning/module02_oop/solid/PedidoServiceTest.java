package com.jorge.learning.module02_oop.solid;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class PedidoServiceTest {

    @Test
    void procesarPedidoConDescuentoPorVolumen() {
        var service = new PedidoService(
                new RepositorioPedidosMemoria (),
                new DescuentoPorVolumen (),
                new NotificadorEmail());
        Pedido pedido = service.procesar("P001", "cliente@test.com", 1200);

        assertEquals(1080.0, pedido.total());
    }


    @Test
    void procesarPedidoSinDescuento() {
        var service = new PedidoService(
                new RepositorioPedidosMemoria (),
                new DescuentoNinguno (),
                new NotificadorEmail());
        Pedido pedido = service.procesar("P001", "cliente@test.com", 1200);

        assertEquals(1200.0, pedido.total());
    }

    @Test
    void procesarRechazaDatosInvalidos() {
        var service = new PedidoService(
                new RepositorioPedidosMemoria (),
                new DescuentoNinguno (),
                new NotificadorEmail());
        assertThatThrownBy(() -> service.procesar("", "hola@gmail", 0))
                .isInstanceOf(IllegalArgumentException.class);
    }


    @Test
    void descuentoPorVolumenUmbral500() {
        var service = new PedidoService(
                new RepositorioPedidosMemoria (),
                new DescuentoPorVolumen(),
                new NotificadorEmail());
        Pedido pedido = service.procesar("P001", "cliente@test.com", 600);

        assertEquals(570.0, pedido.total());
    }




}