package com.jorge.learning.module01_fundamentos;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ProcesadorTransaccionesTest {

    private ProcesadorTransacciones procesadorTransacciones;

    @BeforeEach
    void setUp() {
        procesadorTransacciones = new ProcesadorTransacciones();
    }

    @Test
    void clasificarTransaccion() {
        assertThat(procesadorTransacciones.clasificar(TipoTransaccion.DEPOSITO)).isEqualTo("Ingreso de fondos");
        assertThat(procesadorTransacciones.clasificar(TipoTransaccion.RETIRO)).isEqualTo("Salida de fondos");
        assertThat(procesadorTransacciones.clasificar(TipoTransaccion.TRANSFERENCIA)).isEqualTo("Movimiento entre cuentas");
    }

    @Test
    void calcularTotalDeMontos() {
        double[] montos = {100.0, 250.0, 50.0};
        assertThat(procesadorTransacciones.calcularTotal(montos)).isEqualTo(400.0);
    }

    @Test
    void calcularTotalDeMontosNull() {
        assertThat(procesadorTransacciones.calcularTotal(null)).isEqualTo(0.00);
        assertThat(procesadorTransacciones.calcularTotal(new double[0])).isEqualTo(0.00);
    }


    @Test
    void filtrarPorTipo() {
        Transaccion[] transacciones = new Transaccion[]{new Transaccion("001", TipoTransaccion.DEPOSITO, 125.00),
        new Transaccion("002", TipoTransaccion.DEPOSITO, 125.00),
        new Transaccion("003", TipoTransaccion.RETIRO, 500)};

        Transaccion[] transaccionesFiltradas = procesadorTransacciones.filtrarPorTipo(transacciones, TipoTransaccion.DEPOSITO);
        assertThat(transaccionesFiltradas.length).isEqualTo(2);

    }

    @Test
    void filtrarPorTipoVacio() {
        Transaccion[] transacciones = new Transaccion[0];

        Transaccion[] transaccionesFiltradas = procesadorTransacciones.filtrarPorTipo(transacciones, TipoTransaccion.DEPOSITO);
        assertThat(transaccionesFiltradas.length).isEqualTo(0);

        Transaccion[] transaccionesFiltradasNull = procesadorTransacciones.filtrarPorTipo(null, TipoTransaccion.DEPOSITO);
        assertThat(transaccionesFiltradasNull.length).isEqualTo(0);

    }

    @Test
    void sobreCargaComision() {

        assertThat(procesadorTransacciones.aplicarComision(1000)).isEqualTo(10.0);
        assertThat(procesadorTransacciones.aplicarComision(1000, 0.05)).isEqualTo(50.0);
    }




}