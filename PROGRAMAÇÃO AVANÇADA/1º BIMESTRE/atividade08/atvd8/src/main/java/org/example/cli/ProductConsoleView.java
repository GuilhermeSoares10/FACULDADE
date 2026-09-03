package org.example.cli;

import org.example.entities.Product;
import org.example.services.ProductService;
import org.example.utils.ValidationUtils;

import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class ProductConsoleView {
    private final ProductService productService;

    public ProductConsoleView(ProductService productService) {
        this.productService = productService;
    }

    public void runMenu(Scanner scanner) {
        int option;
        do {
            printProductMenu();
            option = readInt(scanner);

            try {
                switch (option) {
                    case 1 -> registerProduct(scanner);
                    case 2 -> listProducts();
                    case 3 -> searchProductsByDescription(scanner);
                    case 4 -> updateProduct(scanner);
                    case 5 -> deleteProduct(scanner);
                    case 6 -> clearAllProducts();
                    case 0 -> System.out.println("Voltando ao menu principal...");
                    default -> System.out.println("Opção inválida. Tente novamente.");
                }
            } catch (Exception e) {
                System.out.println("Erro: " + e.getMessage());
            }
        } while (option != 0);
    }

    private void printProductMenu() {
        System.out.println("\n--- GESTÃO DE SERVIÇOS / PRODUTOS ---");
        System.out.println("1 - Cadastrar Serviço");
        System.out.println("2 - Listar Todos os Serviços");
        System.out.println("3 - Buscar Serviço por Descrição");
        System.out.println("4 - Atualizar Serviço");
        System.out.println("5 - Remover Serviço");
        System.out.println("6 - Limpar Todos os Serviços");
        System.out.println("0 - Voltar ao Menu Principal");
        System.out.print("Escolha uma opção: ");
    }

    private void registerProduct(Scanner scanner) {
        System.out.print("Descrição do serviço: ");
        String description = scanner.nextLine();
        System.out.print("Valor por hora (R$): ");
        double hourlyRate = readDouble(scanner);

        Product product = productService.registerProduct(description, hourlyRate);
        System.out.println("Serviço cadastrado com sucesso!");
        System.out.println(product);
    }

    private void listProducts() {
        List<Product> products = productService.listAllProducts();
        if (products.isEmpty()) {
            System.out.println("Nenhum serviço cadastrado.");
        } else {
            System.out.println("\n--- Lista de Serviços ---");
            for (Product product : products) {
                System.out.println(product);
            }
        }
    }

    private void searchProductsByDescription(Scanner scanner) {
        System.out.print("Digite o termo de busca: ");
        String term = scanner.nextLine();
        List<Product> results = productService.searchByDescription(term);
        if (results.isEmpty()) {
            System.out.println("Nenhum serviço encontrado com a palavra: " + term);
        } else {
            System.out.println("\n--- Resultados Encontrados ---");
            for (Product product : results) {
                System.out.println(product);
            }
        }
    }

    private void updateProduct(Scanner scanner) {
        System.out.print("Digite o UUID do serviço a ser atualizado: ");
        String uuidStr = scanner.nextLine();
        UUID uuid = ValidationUtils.parseUuid(uuidStr);

        System.out.print("Nova descrição (pressione Enter para manter a atual): ");
        String description = scanner.nextLine();
        System.out.print("Novo valor por hora (ou 0 para manter o atual): ");
        double rate = readDouble(scanner);

        Product updated = productService.updateProduct(uuid, description, rate);
        System.out.println("Serviço atualizado com sucesso!");
        System.out.println(updated);
    }

    private void deleteProduct(Scanner scanner) {
        System.out.print("Digite o UUID do serviço a ser removido: ");
        String uuidStr = scanner.nextLine();
        UUID uuid = ValidationUtils.parseUuid(uuidStr);
        productService.deleteProduct(uuid);
        System.out.println("Serviço removido com sucesso.");
    }

    private void clearAllProducts() {
        productService.clearAllProducts();
        System.out.println("Todos os serviços foram removidos com sucesso.");
    }

    private int readInt(Scanner scanner) {
        try {
            int val = scanner.nextInt();
            scanner.nextLine();
            return val;
        } catch (Exception e) {
            scanner.nextLine();
            return -1;
        }
    }

    private double readDouble(Scanner scanner) {
        try {
            double val = scanner.nextDouble();
            scanner.nextLine();
            return val;
        } catch (Exception e) {
            scanner.nextLine();
            return -1.0;
        }
    }
}
