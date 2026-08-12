package com.jorge.learning.module02_oop;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ProcesadorPagoTest {

    private ProcesadorPago procesador = new ProcesadorPago();

    @Test
    void tarjetaAplicaComision() {
        FormaPago tarjeta = new TarjetaCredito("1234", 0.03);
        assertThat(procesador.procesarPago(tarjeta, 100)).isEqualTo(103.0);
    }

    @Test
    void transferenciaSinRecargo() {
        FormaPago transferencia = new TransferenciaBancaria("BBVA");
        assertThat(procesador.procesarPago(transferencia, 200)).isEqualTo(200.0);
    }

    @Test
    void efectivoNoRequiereAutenticacion() {
        assertThat(new Efectivo().requiereAutenticacion()).isFalse();
    }

    @Test
    void generarReciboContieneDescripcionYTotal() {
        FormaPago efectivo = new Efectivo();
        assertThat(procesador.generarRecibo(efectivo, 50))
                .contains("Efectivo")
                .contains("50");
    }

    @Test
    void procesarRechazaFormaPagoNull() {
        assertThatThrownBy(() -> procesador.procesarPago(null, 100))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void polimorfismoMismoMetodoDistintosComportamientos() {
        FormaPago[] pagos = {
                new TarjetaCredito("9999", 0.10),
                new Efectivo()
        };
        assertThat(procesador.procesarPago(pagos[0], 100.00)).isEqualTo(110.00000000000001);
        assertThat(procesador.procesarPago(pagos[1], 100.0)).isEqualTo(100.0);
    }
}