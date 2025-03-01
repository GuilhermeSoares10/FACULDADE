package org.example;

class ContaInvestimentoDeAltoRisco extends ContaCapital {
    private final double saldoMinimo;

    public ContaInvestimentoDeAltoRisco(String numeroConta, String nomeTitular, double saldo, double saldoMinimo) {
        super(numeroConta, nomeTitular, saldo);
        this.saldoMinimo = saldoMinimo;
    }

    @Override
    public void sacar(double valor) {
        if (saldo >= saldoMinimo) {
            double taxa = valor * 0.05;
            double valorComTaxa = valor + taxa;

            if (valorComTaxa <= saldo) {
                saldo -= valorComTaxa;
                System.out.println("Saque de R$ " + valor + " com uma taxa de R$ " + taxa + " realizado com sucesso.");
            } else {
                System.out.println("Saldo insuficiente para realizar o saque.");
            }
        } else {
            System.out.println("Saldo mínimo de R$ " + saldoMinimo + " não alcançado para realizar o saque.");
        }
    }
}

