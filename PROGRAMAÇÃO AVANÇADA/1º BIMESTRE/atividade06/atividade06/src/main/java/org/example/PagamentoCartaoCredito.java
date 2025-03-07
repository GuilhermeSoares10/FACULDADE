package org.example;
import java.util.Scanner;

public class PagamentoCartaoCredito implements EstrategiaDePagamento {
    @Override
    public void processarPagamento(double valor) {
        // Solicita um número fictício de cartão
        Scanner scanner = new Scanner(System.in);
        System.out.println("Digite o número do cartão de crédito (fictício):");
        String numeroCartao = scanner.nextLine(); // Só para fins de simulação
        System.out.println("Pagamento via Cartão de Crédito confirmado! Valor: R$" + valor);
    }
}

