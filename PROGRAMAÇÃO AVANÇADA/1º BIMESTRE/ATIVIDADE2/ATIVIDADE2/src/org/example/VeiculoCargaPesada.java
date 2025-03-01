package org.example;

class VeiculoCargaPesada extends TransporteBase {
    private double carga;

    public VeiculoCargaPesada(String marca, String modelo, int ano, int capacidadePassageiros, String combustivel, double carga) {
        super(marca, modelo, ano, capacidadePassageiros, combustivel);
        this.carga = carga;
    }

    @Override
    public double calcularAutonomia() {
        double capacidadeTanque = 300; // Litros
        double consumoBase = 6; // km/L
        double consumoFinal = consumoBase - (carga * 0.01 * consumoBase);
        if (carga > 25) {
            consumoFinal = consumoBase * 0.75; // Limita a redução para no máximo 25%
        }
        return capacidadeTanque * consumoFinal;
    }

    @Override
    public void exibirDetalhes() {
        super.exibirDetalhes();
        System.out.println("Carga: " + carga + " toneladas");
    }
}
