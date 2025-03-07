package org.example;

public class ProcessadorDePagamento {
    private EstrategiaDePagamento estrategiaDePagamento;

    // Construtor que recebe a estratégia de pagamento
    public ProcessadorDePagamento(EstrategiaDePagamento estrategiaDePagamento) {
        this.estrategiaDePagamento = estrategiaDePagamento;
    }

    // Método que processa o pagamento com a estratégia fornecida
    public void processar(double valor) {
        estrategiaDePagamento.processarPagamento(valor); // Chama o método processarPagamento da estratégia
    }
}

