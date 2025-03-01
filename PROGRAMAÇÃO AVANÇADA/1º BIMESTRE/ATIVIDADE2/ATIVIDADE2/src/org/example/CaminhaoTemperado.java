package org.example;

class CaminhaoTemperado extends VeiculoCargaPesada {
    private double temperatura;

    public CaminhaoTemperado(String marca, String modelo, int ano, int capacidadePassageiros, String combustivel, double carga, double temperatura) {
        super(marca, modelo, ano, capacidadePassageiros, combustivel, carga);
        this.temperatura = temperatura;
    }

    @Override
    public double calcularAutonomia() {
        double autonomiaBase = super.calcularAutonomia();
        return autonomiaBase * 0.90; // Reduz a autonomia em 10% devido ao sistema de refrigeração
    }

    @Override
    public void exibirDetalhes() {
        super.exibirDetalhes();
        System.out.println("Temperatura: " + temperatura + " °C");
    }
}
