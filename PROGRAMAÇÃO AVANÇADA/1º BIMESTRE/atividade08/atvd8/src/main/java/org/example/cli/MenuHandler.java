package org.example.cli;

import org.example.services.OrderService;
import org.example.services.ProductService;
import org.example.services.UserService;

import java.util.Scanner;

public class MenuHandler {
    private final UserConsoleView userConsoleView;
    private final ProductConsoleView productConsoleView;
    private final OrderConsoleView orderConsoleView;

    public MenuHandler(UserService userService, ProductService productService, OrderService orderService) {
        this.userConsoleView = new UserConsoleView(userService);
        this.productConsoleView = new ProductConsoleView(productService);
        this.orderConsoleView = new OrderConsoleView(orderService);
    }

    public void start() {
        try (Scanner scanner = new Scanner(System.in)) {
            int option;
            do {
                printMainMenu();
                option = ConsoleInputUtils.readInt(scanner);

                switch (option) {
                    case 1 -> userConsoleView.runMenu(scanner);
                    case 2 -> productConsoleView.runMenu(scanner);
                    case 3 -> orderConsoleView.runMenu(scanner);
                    case 0 -> System.out.println("Saindo do sistema... Até logo!");
                    default -> System.out.println("Opção inválida. Tente novamente.");
                }
            } while (option != 0);
        } catch (Exception e) {
            System.out.println("Erro inesperado no menu principal: " + e.getMessage());
        }
    }

    private void printMainMenu() {
        System.out.println("\n==================================");
        System.out.println("   SISTEMA DE E-COMMERCE CLI     ");
        System.out.println("==================================");
        System.out.println("1 - Gerenciar Usuários");
        System.out.println("2 - Gerenciar Serviços / Produtos");
        System.out.println("3 - Gerenciar Pedidos");
        System.out.println("0 - Sair");
        System.out.print("Escolha uma opção: ");
    }
}
