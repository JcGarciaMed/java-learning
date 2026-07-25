package com.jorge.learning.module00_bootstrap;

public class Main {
    public static void main(String[] args) {
        Saludo saludo = new Saludo();
        System.out.println(saludo.saludo("Jorge Carlos"));
        System.out.println(saludo.saludoMultilinea("Jorge Carlos Garcia Medina"));
    }
}
