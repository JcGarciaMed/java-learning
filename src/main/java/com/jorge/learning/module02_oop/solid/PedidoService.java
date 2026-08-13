package com.jorge.learning.module02_oop.solid;

import java.util.Objects;

public class PedidoService {
    private final RepositorioPedidos repositorioPedidos;
    private final DescuentoStrategy descuentoStrategy;
    private final Notificador notificador;

    public PedidoService(RepositorioPedidos repositorioPedidos, DescuentoStrategy descuentoStrategy, Notificador notificador) {
        this.repositorioPedidos = repositorioPedidos;
        this.descuentoStrategy = descuentoStrategy;
        this.notificador = notificador;
    }

    public Pedido procesar(String id, String email, double monto){
        if (Objects.isNull(id) || id.isBlank()){
            throw new IllegalArgumentException("id invalido");
        }

        var descuento = descuentoStrategy.calcular(monto);

        var total = monto - descuento;

        var pedido = new Pedido(id, email, monto, descuento, total);

        repositorioPedidos.guardar(pedido);

        notificador.enviar(email, "Pedido " + id + " confirmado. Total: " + total);

        return pedido;
    }


}
