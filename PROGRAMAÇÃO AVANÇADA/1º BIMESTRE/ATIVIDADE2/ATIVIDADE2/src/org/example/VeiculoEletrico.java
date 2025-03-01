package org.example;

class VeiculoEletrico extends VeiculoPasseio {
    private double capacidadeBateria;

    public VeiculoEletrico(String marca, String modelo, int ano, int capacidadePassageiros, String combustivel, String categoria, double capacidadeBateria) {
        super(marca, modelo, ano, capacidadePassageiros, combustivel, categoria);
        this.capacidadeBateria = capacidadeBateria;
    }

    @Override
    public double calcularAutonomia() {
        double consumoPorKWh = 5; // km por kWh
        return capacidadeBateria * consumoPorKWh;
    }

    @Override
    public void exibirDetalhes() {
        super.exibirDetalhes();
        System.out.println("Capacidade da Bateria: " + capacidadeBateria + " kWh");
    }
}

