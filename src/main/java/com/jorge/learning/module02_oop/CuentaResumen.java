package com.jorge.learning.module02_oop;

public record CuentaResumen(String numeroCuenta, String titular, double saldo) {
    public static CuentaResumen from(CuentaBancaria cuenta) {
        return new CuentaResumen(
                cuenta.getNumeroCuenta(),
                cuenta.getTitular(),
                cuenta.getSaldo()
        );
    }
}
