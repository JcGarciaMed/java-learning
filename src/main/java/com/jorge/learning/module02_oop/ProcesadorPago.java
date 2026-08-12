package com.jorge.learning.module02_oop;

import java.util.Objects;

public class ProcesadorPago {
    public double procesarPago(FormaPago formaPago, double subtotal) {
        if (Objects.isNull(formaPago)) {
            throw new IllegalArgumentException("Forma de pago no puede ser nula");
        }
        if (subtotal < 0){
            throw new IllegalArgumentException("El subtotal debe ser mayor a cero");
        }

        return formaPago.calcularTotal(subtotal);
    }

    public String generarRecibo(FormaPago formaPago, double subtotal) {
        return "Recibo generado " + formaPago.descripcion() + " con el monto de $" + procesarPago(formaPago, subtotal);
    }
}
