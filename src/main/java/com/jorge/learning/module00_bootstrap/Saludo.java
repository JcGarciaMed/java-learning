package com.jorge.learning.module00_bootstrap;

public class Saludo {
    public String saludo(String nombre) {
        validar(nombre);
        return "Hola, " + nombre + "!";
    }

    public String saludoMultilinea(String nombre) {
        validar(nombre);
        return """
            Hola, %s!
            Bienvenido al curso Java 21.
            """.formatted(nombre).stripTrailing();
    }

    public void validar(String nombre) {{
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío");
        }}
    }
}
