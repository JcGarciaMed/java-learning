package com.jorge.learning.module02_oop;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CuentaBancariaTest {
    private CuentaBancaria cuentaBancaria;

    @BeforeEach
    void setUp() {
        cuentaBancaria = new CuentaBancaria("001-4642892", "Jorge Garcia", 1000.0);
    }

    @Test
    void depositarYRetirar() {
        cuentaBancaria.depositar(100.0);
        assertThat(cuentaBancaria.getSaldo()).isEqualTo(1100.0);
        cuentaBancaria.retirar(50.0);
        assertThat(cuentaBancaria.getSaldo()).isEqualTo(1050.0);
    }

    @Test
    void retiraYLanzaExcepcionSiSaldoInsuficiente() {
        assertThatThrownBy(() -> cuentaBancaria.retirar(1200.0))
            .isInstanceOf(IllegalStateException.class);
    }

    @Test
    void constructorRechazaSaldoNegativo() {
        assertThatThrownBy(() -> new CuentaBancaria("001-4642892", "Jorge Garcia", -1000.0))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void setTitularRechazaVacio() {
        assertThatThrownBy(() -> cuentaBancaria.setTitular(""))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void transferirEntreCuentas() {
        CuentaBancaria cuentaBancariaDestino = new CuentaBancaria("001-4642331", "Jorge Garcia",
                500.0);

        cuentaBancaria.transferirA(cuentaBancariaDestino, 250.0);
        assertThat(cuentaBancaria.getSaldo()).isEqualTo(750.0);
        assertThat(cuentaBancariaDestino.getSaldo()).isEqualTo(750.0);
    }

    @Test
    void cuentaResumenFromRecord() {
        CuentaResumen cuentaResumen = CuentaResumen.from(cuentaBancaria);
        assertThat(cuentaResumen.numeroCuenta()).isEqualTo("001-4642892");
        assertThat(cuentaResumen.titular()).isEqualTo("Jorge Garcia");
        assertThat(cuentaResumen.saldo()).isEqualTo(1000.0 );
    }








}