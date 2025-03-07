package org.example;

public class SMSNotification implements Notification {
    @Override
    public void send(String message) {
        System.out.println("Enviando SMS: " + message); // Simula o envio de um SMS
    }
}

