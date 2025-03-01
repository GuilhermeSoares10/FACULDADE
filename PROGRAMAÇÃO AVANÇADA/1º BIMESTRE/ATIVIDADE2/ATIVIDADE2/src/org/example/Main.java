package org.example;

public class Main {
    public static void main(String[] args) {
        TransporteBase veiculo1 = new VeiculoPasseio("Toyota", "Corolla", 2020, 5, "Gasolina", "Sedan");
        TransporteBase veiculo2 = new VeiculoCargaPesada("Volvo", "FH", 2022, 2, "Diesel", 10);
        TransporteBase veiculo3 = new VeiculoTransportePublico("Mercedes", "O500", 2018, 50, "Diesel", 7);
        TransporteBase veiculo4 = new VeiculoEletrico("Tesla", "Model 3", 2023, 5, "Eletrico", "Sedan", 75);
        TransporteBase veiculo5 = new CaminhaoTemperado("Scania", "R450", 2022, 2, "Diesel", 15, -5);

        System.out.println("Detalhes do Veículo Passeio:");
        veiculo1.exibirDetalhes();
        System.out.println("Autonomia: " + veiculo1.calcularAutonomia() + " km\n");

        System.out.println("Detalhes do Veículo Carga Pesada:");
        veiculo2.exibirDetalhes();
        System.out.println("Autonomia: " + veiculo2.calcularAutonomia() + " km\n");

        System.out.println("Detalhes do Veículo Transporte Público:");
        veiculo3.exibirDetalhes();
        System.out.println("Autonomia: " + veiculo3.calcularAutonomia() + " km\n");

        System.out.println("Detalhes do Veículo Eletrico:");
        veiculo4.exibirDetalhes();
        System.out.println("Autonomia: " + veiculo4.calcularAutonomia() + " km\n");

        System.out.println("Detalhes do Caminhão Temperado:");
        veiculo5.exibirDetalhes();
        System.out.println("Autonomia: " + veiculo5.calcularAutonomia() + " km\n");
    }
}

