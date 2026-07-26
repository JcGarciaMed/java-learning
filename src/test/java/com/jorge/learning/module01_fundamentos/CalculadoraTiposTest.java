package com.jorge.learning.module01_fundamentos;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CalculadoraTiposTest {

    private CalculadoraTipos calculadora;

    @BeforeEach
    void setUp() {
        calculadora = new CalculadoraTipos();
    }

    @Test
    void sumarEnteros() {
        assertThat(calculadora.sumarEnteros(3, 5)).isEqualTo(8);
    }

    @Test
    void sumarDecimales() {
        assertThat(calculadora.sumarDecimales(1.5, 2.5)).isEqualTo(4.0);
    }

    @Test
    void convertirAWrapper() {
        assertThat(calculadora.convertirAWrapper(42)).isEqualTo(42);
    }

    @Test
    void esParConWrapper() {
        assertThat(calculadora.esPar(4)).isTrue();
        assertThat(calculadora.esPar(7)).isFalse();
    }

    @Test
    void esParRechazaNull() {
        assertThatThrownBy(() -> calculadora.esPar(null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void describirTipoUsaSwitchExpression() {
        assertThat(calculadora.describirTipo(0)).contains("cero");
        assertThat(calculadora.describirTipo(3)).contains("pequeño");
        assertThat(calculadora.describirTipo(150)).contains("grande");
        assertThat(calculadora.describirTipo(50)).contains("mediano");
    }
}
