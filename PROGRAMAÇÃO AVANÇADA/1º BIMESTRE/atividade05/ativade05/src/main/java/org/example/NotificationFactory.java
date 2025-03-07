package org.example;

public class NotificationFactory {

    // Método estático que cria a notificação com base no tipo
    public static Notification createNotification(String type) {
        switch (type) {
            case "1":
                return new EmailNotification(); // Cria uma instância de EmailNotification
            case "2":
                return new SMSNotification(); // Cria uma instância de SMSNotification
            case "3":
                return new PushNotification(); // Cria uma instância de PushNotification
            default:
                throw new IllegalArgumentException("Tipo de notificação inválido."); // Caso o tipo seja inválido
        }
    }
}

