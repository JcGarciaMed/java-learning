package com.jorge.learning.module01_fundamentos;

import java.util.Objects;

public class ProcesadorTransacciones {

    public String clasificar(TipoTransaccion tipo) {
        return switch (tipo) {
            case TipoTransaccion.DEPOSITO -> "Ingreso de fondos";
            case TipoTransaccion.RETIRO -> "Salida de fondos";
            case TipoTransaccion.TRANSFERENCIA -> "Movimiento entre cuentas";
        };
    }

    public double calcularTotal(double[] montos){
        if(Objects.isNull(montos) || montos.length == 0) {
            return 0.00;
        }

        double total = 0.00;

        for (double monto : montos) {
            total += monto;
        }
        return total;
    }

    public Transaccion[] filtrarPorTipo(Transaccion[] transacciones, TipoTransaccion tipo) {
        if (Objects.isNull(transacciones) || transacciones.length == 0){
            return new Transaccion[0];
        }

        int contador = 0;

        for (Transaccion transaccion : transacciones) {
            if (transaccion.tipo().equals(tipo)) {
                contador++;
            }
        }

        Transaccion[] transaccionesFiltrada = new Transaccion[contador];

        if (contador == 0){
            return transacciones;
        }

        contador = 0;
        for (Transaccion transaccion : transacciones) {
            if (transaccion.tipo().equals(tipo)) {
                transaccionesFiltrada[contador] = transaccion;
                contador++;
            }
        }

        return transaccionesFiltrada;

    }

    public double aplicarComision(double monto){
        return monto * 0.01;
    }

    public double aplicarComision(double monto, double porcentaje){
        return monto * porcentaje;
    }


}
