package org.example;

class ContaReserva extends ContaBase {

    public ContaReserva(String numeroConta, String nomeTitular, double saldo) {
        super(numeroConta, nomeTitular, saldo);
    }

    @Override
    public void sacar(double valor) {
        if (valor <= saldo) {
            saldo -= valor;
            System.out.println("Saque de R$ " + valor + " realizado com sucesso.");
        } else {
            System.out.println("Saldo insuficiente.");
        }
    }
}

