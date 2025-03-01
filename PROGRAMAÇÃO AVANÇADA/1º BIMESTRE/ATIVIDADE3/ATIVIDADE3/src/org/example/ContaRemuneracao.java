package org.example;

class ContaRemuneracao extends ContaCheque {
    private boolean saqueGratisRealizado;

    public ContaRemuneracao(String numeroConta, String nomeTitular, double saldo, double limiteChequeEspecial) {
        super(numeroConta, nomeTitular, saldo, limiteChequeEspecial);
        this.saqueGratisRealizado = false;
    }

    @Override
    public void sacar(double valor) {
        if (!saqueGratisRealizado) {
            if (valor <= saldo + limiteChequeEspecial) {
                saldo -= valor;
                saqueGratisRealizado = true;
                System.out.println("Saque gratuito de R$ " + valor + " realizado com sucesso.");
            } else {
                System.out.println("Saldo insuficiente, incluindo o cheque especial.");
            }
        } else {
            if (valor <= saldo + limiteChequeEspecial) {
                saldo -= valor;
                saldo -= 5; // Cobrança de taxa
                System.out.println("Saque de R$ " + valor + " realizado com sucesso, com taxa de R$ 5,00.");
            } else {
                System.out.println("Saldo insuficiente, incluindo o cheque especial.");
            }
        }
    }
}

