package org.example;

class ContaCapital extends ContaBase {

    public ContaCapital(String numeroConta, String nomeTitular, double saldo) {
        super(numeroConta, nomeTitular, saldo);
    }

    @Override
    public void sacar(double valor) {
        double taxa = valor * 0.02;
        double valorComTaxa = valor + taxa;

        if (valorComTaxa <= saldo) {
            saldo -= valorComTaxa;
            System.out.println("Saque de R$ " + valor + " com uma taxa de R$ " + taxa + " realizado com sucesso.");
        } else {
            System.out.println("Saldo insuficiente para realizar o saque.");
        }
    }
}

