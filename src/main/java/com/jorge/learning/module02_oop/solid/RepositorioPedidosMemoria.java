package com.jorge.learning.module02_oop.solid;

public class RepositorioPedidosMemoria implements RepositorioPedidos{
    @Override
    public void guardar(Pedido pedido) {
        System.out.println("Pedido guardado en memoria: " + pedido.id());
    }
}
