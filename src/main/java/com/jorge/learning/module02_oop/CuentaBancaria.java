package com.jorge.learning.module02_oop;

import java.util.Objects;

public class CuentaBancaria {
    private final String numeroCuenta;
    private String titular;
    private double saldo;

    public CuentaBancaria(String numeroCuenta, String titular, double saldo) {
        validarCuenta(numeroCuenta, saldo);
        this.numeroCuenta = numeroCuenta;
        this.titular = titular;
        this.saldo = saldo;
    }

    private void validarCuenta(String numeroCuenta, double saldo){
        if (Objects.isNull(numeroCuenta) || numeroCuenta.isBlank()){
            throw new IllegalArgumentException("Número de cuenta no puede ser blanco o nulo");
        }

        if (saldo<0){
            throw new IllegalArgumentException("El saldo inicial no puede ser negativo");
        }
    }

    public String getNumeroCuenta() {
        return numeroCuenta;
    }

    public String getTitular() {
        return titular;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setTitular(String titular) {
        if (Objects.isNull(titular) || titular.isBlank()){
            throw new IllegalArgumentException("El nombre del titular no puede ser nulo o blanco");
        }
        this.titular = titular;
    }
    public void depositar(double monto){
        if (monto<=0){
            throw new IllegalArgumentException("El monto a depositar debe ser mayor a cero");
        }
        this.saldo += monto;
    }

    public void retirar(double monto){
        if (monto<=0){
            throw new IllegalArgumentException("El monto a retirar debe ser mayor a cero");
        }
        if (monto>saldo){
            throw new IllegalStateException("El monto a retirar no puede ser mayor al saldo disponible");
        }
        this.saldo -= monto;
    }

    public boolean transferirA(CuentaBancaria cuentaDestino, double monto){
        if (Objects.isNull(cuentaDestino)){
            throw new IllegalArgumentException("La cuenta destino no puede ser nula");
        }
        this.retirar(monto);
        cuentaDestino.depositar(monto);
        return true;
    }
}
