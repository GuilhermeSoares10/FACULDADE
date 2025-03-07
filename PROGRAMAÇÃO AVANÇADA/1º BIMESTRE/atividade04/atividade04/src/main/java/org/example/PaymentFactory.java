package org.example;

public class PaymentFactory {

    // Método estático que cria a estratégia de pagamento com base na escolha do usuário
    public static EstrategiaDePagamento criarPagamento(String tipo) {
        switch (tipo) {
            case "1":
                return new PagamentoPix(); // Retorna uma instância de PagamentoPix
            case "2":
                return new PagamentoCartaoCredito(); // Retorna uma instância de PagamentoCartaoCredito
            case "3":
                return new PagamentoBoleto(); // Retorna uma instância de PagamentoBoleto
            default:
                throw new IllegalArgumentException("Tipo de pagamento inválido."); // Caso o tipo seja inválido
        }
    }
}

