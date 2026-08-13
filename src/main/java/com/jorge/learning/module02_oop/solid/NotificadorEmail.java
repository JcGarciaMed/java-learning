package com.jorge.learning.module02_oop.solid;

public class NotificadorEmail implements Notificador{

    @Override
    public void enviar(String destinatario, String mensaje) {
        System.out.println("Email a " + destinatario + ": " + mensaje);
    }
}
