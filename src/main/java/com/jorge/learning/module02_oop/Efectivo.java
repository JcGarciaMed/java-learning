package com.jorge.learning.module02_oop;

public final class Efectivo implements FormaPago{

    @Override
    public double calcularTotal(double subtotal) {
        return subtotal;
    }

    @Override
    public String descripcion() {
        return "Efectivo";
    }

    @Override
    public boolean requiereAutenticacion() {
        return false;
    }
}
