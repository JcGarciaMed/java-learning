package com.jorge.learning.module02_oop;

public final class TarjetaCredito implements FormaPago{
    private final String ultimosCuatroDigitos;
    private final double comision;

    public TarjetaCredito(String ultimosCuatroDigitos, double comision) {
        this.ultimosCuatroDigitos = ultimosCuatroDigitos;
        this.comision = comision;
    }

    @Override
    public double calcularTotal(double subtotal) {
        return subtotal * (1 + comision);
    }

    @Override
    public String descripcion() {
        return "Tarjeta ****" + ultimosCuatroDigitos;
    }
}
