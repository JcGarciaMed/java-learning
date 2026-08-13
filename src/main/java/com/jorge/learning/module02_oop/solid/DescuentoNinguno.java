package com.jorge.learning.module02_oop.solid;

public class DescuentoNinguno implements DescuentoStrategy{

    @Override
    public double calcular(double monto) {
        return 0;
    }
}
