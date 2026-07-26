package com.jorge.learning.module01_fundamentos;

import java.util.Objects;
import java.util.Optional;

/**
 * Lección 1.2 — Tipos y variables.
 * Implementa cada método. Los tests en CalculadoraTiposTest definen el comportamiento esperado.
 */
public class CalculadoraTipos {

    /** Suma dos enteros primitivos. */
    public int sumarEnteros(int a, int b) {
        return a + b;
    }

    /** Suma dos decimales (tipo double). */
    public double sumarDecimales(double a, double b) {
        return a + b;
    }

    /** Convierte un int primitivo a Integer (boxing). */
    public Integer convertirAWrapper(int valor) {
        return Integer.valueOf(valor);
    }

    /** Retorna true si el número es par. Lanza IllegalArgumentException si numero es null. */
    public boolean esPar(Integer numero) {
        if (Objects.isNull(numero))
            throw new IllegalArgumentException();
        return numero % 2 == 0;
    }

    /**
     * Describe el valor usando switch expression (Java 14+) y var (Java 10+):
     * - 0 → contiene "cero"
     * - 1,2,3,4,5 → contiene "pequeño"
     * - mayor a 100 → contiene "grande"
     * - resto → contiene "mediano"
     */
    public String describirTipo(int valor) {
        return switch (valor) {
            case 0 -> "cero";
            case 1, 2, 3, 4, 5 -> "pequeño";
            default -> valor > 100 ? "grande" : "mediano";
        };
    }
}
