package org.example;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int opcao;
        Map<Integer, Produto> produtos = new HashMap<>();

        do{
            System.out.println("\n---Menu---");
            System.out.println("1 - Cadastrar Produto");
            System.out.println("2 - Buscar produto por código");
            System.out.println("3 - Sair");
            System.out.println("Escolha uma opção: ");
            opcao = scanner.nextInt();


            switch (opcao){
                case 1:
                    System.out.print("\nDigite o código do produto: ");
                    int codigo = scanner.nextInt();
                    scanner.nextLine();

                    System.out.print("Digite o nome: ");
                    String nome = scanner.next();

                    System.out.print("Digite o preço do produto: ");
                    double preco = scanner.nextDouble();

                    Produto produto = new Produto(codigo, nome, preco);
                    produtos.put(codigo, produto);
                    System.out.println("Produto cadastrado com sucesso");
                    break;

                case 2:
                    System.out.println("Digite o código do produto para buscar:");
                    int codigoBusca = scanner.nextInt();
                    scanner.nextLine();

                    Produto produtoBuscado = produtos.get(codigoBusca);
                    if (produtoBuscado != null) {
                        System.out.println("Produto encontrado: " + produtoBuscado);
                    } else {
                        System.out.println("Produto não encontrado!");
                    }
                    break;

                case 3:
                    System.out.println("Saindo...");
                    break;

                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        } while (opcao != 3);
        scanner.close();
    }
}