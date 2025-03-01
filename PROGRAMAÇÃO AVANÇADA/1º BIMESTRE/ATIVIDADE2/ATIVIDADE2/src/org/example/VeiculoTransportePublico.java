package org.example;

class VeiculoTransportePublico extends TransporteBase {
    private int eixos;

    public VeiculoTransportePublico(String marca, String modelo, int ano, int capacidadePassageiros, String combustivel, int eixos) {
        super(marca, modelo, ano, capacidadePassageiros, combustivel);
        if (eixos < 6 || eixos > 8) {
            throw new IllegalArgumentException("Número de eixos deve ser entre 6 e 8.");
        }
        this.eixos = eixos;
    }

    @Override
    public double calcularAutonomia() {
        double capacidadeTanque = 200; // Litros
        double consumoBase = 5; // km/L
        return capacidadeTanque * consumoBase;
    }

    @Override
    public void exibirDetalhes() {
        super.exibirDetalhes();
        System.out.println("Eixos: " + eixos);
    }
}

