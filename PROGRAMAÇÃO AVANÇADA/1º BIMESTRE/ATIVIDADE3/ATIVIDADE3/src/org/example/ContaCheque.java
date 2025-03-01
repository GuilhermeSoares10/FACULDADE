package org.example;

class ContaCheque extends ContaBase {
    private double limiteChequeEspecial;

    public ContaCheque(String numeroConta, String nomeTitular, double saldo, double limiteChequeEspecial) {
        super(numeroConta, nomeTitular, saldo);
        this.limiteChequeEspecial = limiteChequeEspecial;
    }

    @Override
    public void sacar(double valor) {
        if (valor <= saldo + limiteChequeEspecial) {
            saldo -= valor;
            System.out.println("Saque de R$ " + valor + " realizado com sucesso.");
        } else {
            System.out.println("Saldo insuficiente, incluindo o cheque especial.");
        }
    }
}

