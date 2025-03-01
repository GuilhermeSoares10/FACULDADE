package org.example;

public class Main {
    public static void main(String[] args) {
        ContaBase conta1 = new ContaCheque("12345", "João Silva", 1000, 500);
        ContaBase conta2 = new ContaReserva("54321", "Maria Souza", 1500);
        ContaBase conta3 = new ContaCapital("67890", "Carlos Santos", 2000);
        ContaBase conta4 = new ContaRemuneracao("11223", "Ana Costa", 3000, 1000);
        ContaBase conta5 = new ContaInvestimentoDeAltoRisco("44556", "Pedro Oliveira", 15000, 10000);

        System.out.println("Informações da Conta João Silva:");
        conta1.exibirInformacoes();
        conta1.sacar(1200); // Usa o cheque especial
        conta1.exibirInformacoes();

        System.out.println("\nInformações da Conta Maria Souza:");
        conta2.exibirInformacoes();
        conta2.sacar(1000);
        conta2.exibirInformacoes();

        System.out.println("\nInformações da Conta Carlos Santos:");
        conta3.exibirInformacoes();
        conta3.sacar(2100); // Exemplo com taxa
        conta3.exibirInformacoes();

        System.out.println("\nInformações da Conta Ana Costa:");
        conta4.exibirInformacoes();
        conta4.sacar(500); // Saque gratuito
        conta4.sacar(500); // Saque com taxa
        conta4.exibirInformacoes();

        System.out.println("\nInformações da Conta Pedro Oliveira:");
        conta5.exibirInformacoes();
        conta5.sacar(5000); // Saque com taxa
        conta5.exibirInformacoes();
    }
}

