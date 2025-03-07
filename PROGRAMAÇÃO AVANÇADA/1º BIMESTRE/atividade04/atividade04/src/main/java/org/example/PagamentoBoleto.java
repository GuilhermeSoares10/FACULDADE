package org.example;

public class PagamentoBoleto implements EstrategiaDePagamento {
    @Override
    public void processarPagamento(double valor) {
        // Exibe um código de boleto fictício
        String codigoBoleto = "BOLETO-" + System.currentTimeMillis();
        System.out.println("Pagamento via Boleto gerado! Código do boleto: " + codigoBoleto);
    }
}

