package org.example;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Exibe o menu de opções
        System.out.println("Escolha o metodo de pagamento:");
        System.out.println("1: Pix");
        System.out.println("2: Cartao de Credito");
        System.out.println("3: Boleto");

        // Lê a escolha do usuário
        String escolha = scanner.nextLine();

        // Solicita o valor da transação
        System.out.println("Digite o valor da transacao:");
        double valor = scanner.nextDouble();

        // Cria a estratégia de pagamento com a PaymentFactory
        EstrategiaDePagamento estrategia = PaymentFactory.criarPagamento(escolha);

        // Cria o processador de pagamento e processa a transação
        ProcessadorDePagamento processador = new ProcessadorDePagamento(estrategia);
        processador.processar(valor); // Processa o pagamento

        scanner.close();
    }
}

