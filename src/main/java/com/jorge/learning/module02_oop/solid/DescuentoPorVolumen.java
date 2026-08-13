package com.jorge.learning.module02_oop.solid;

public class DescuentoPorVolumen implements DescuentoStrategy{
    @Override
    public double calcular(double monto) {
        if (monto>1000){
            return monto*0.1;
        } else if (monto > 500) {
            return monto*0.05;
        } else {
            return 0;
        }
    }
}
