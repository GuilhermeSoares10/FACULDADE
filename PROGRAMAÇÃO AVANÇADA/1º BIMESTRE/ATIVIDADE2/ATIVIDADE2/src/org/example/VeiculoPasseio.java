package org.example;

class VeiculoPasseio extends TransporteBase {
    private String categoria;

    public VeiculoPasseio(String marca, String modelo, int ano, int capacidadePassageiros, String combustivel, String categoria) {
        super(marca, modelo, ano, capacidadePassageiros, combustivel);
        this.categoria = categoria;
    }

    @Override
    public double calcularAutonomia() {
        double capacidadeTanque = 50; // Litros
        double consumo = 12; // km/L
        return capacidadeTanque * consumo;
    }

    @Override
    public void exibirDetalhes() {
        super.exibirDetalhes();
        System.out.println("Categoria do Veículo: " + categoria);
    }
}

