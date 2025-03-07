package org.example;

public class PushNotification implements Notification {
    @Override
    public void send(String message) {
        System.out.println("Enviando Push Notification: " + message); // Simula o envio de uma push notification
    }
}

