package com.jorge.learning.module02_oop;

public final class TransferenciaBancaria implements FormaPago{
    private String banco;

    public TransferenciaBancaria(String banco) {
        this.banco = banco;
    }

    @Override
    public double calcularTotal(double subtotal) {
        return subtotal;
    }

    @Override
    public String descripcion() {
        return "Transferencia " + banco;
    }
}
