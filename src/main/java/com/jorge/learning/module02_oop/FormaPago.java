package com.jorge.learning.module02_oop;

public sealed interface FormaPago permits Efectivo, TarjetaCredito, TransferenciaBancaria {

    double calcularTotal(double subtotal);
    String descripcion();
    default boolean requiereAutenticacion() {
        return true;
    }
}
