package com.jorge.learning.module00_bootstrap;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class SaludoTest {

    @Test
    void saludarRetornaMensajeConNombre() {
        assertThat(new Saludo().saludo("Jorge")).isEqualTo("Hola, Jorge!");
    }

    @Test
    void saludarRechazaNombreVacio() {
        assertThatThrownBy(() -> new Saludo().saludo(""))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("vacío");
    }

    @Test
    void saludoMultilineaIncluyeNombre() {
        String resultado = new Saludo().saludoMultilinea("Ana");

        assertThat(resultado).contains("Hola, Ana!");
        assertThat(resultado).contains("Bienvenido al curso Java 21.");
    }
}
