package org.example;
import java.util.Random;

public class PagamentoPix implements EstrategiaDePagamento {
    @Override
    public void processarPagamento(double valor) {
        // Gerar um código de Pix aleatório
        String codigoPix = "PIX-" + new Random().nextInt(1000000);
        System.out.println("Pagamento via Pix realizado! Código: " + codigoPix);
    }
}

