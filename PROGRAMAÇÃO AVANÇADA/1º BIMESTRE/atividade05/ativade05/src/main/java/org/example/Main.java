package org.example;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Exibe o menu de opções
        System.out.println("Escolha o tipo de notificação:");
        System.out.println("1: E-mail");
        System.out.println("2: SMS");
        System.out.println("3: Push Notification");

        // Lê a escolha do usuário
        String choice = scanner.nextLine();

        // Solicita a mensagem
        System.out.println("Digite a mensagem a ser enviada:");
        String message = scanner.nextLine();

        // Cria a notificação com base na escolha
        Notification notification = NotificationFactory.createNotification(choice);

        // Envia a mensagem
        notification.send(message);

        scanner.close();
    }
}
