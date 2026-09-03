package org.example.cli;

import org.example.entities.Order;
import org.example.entities.OrderStatus;
import org.example.services.OrderService;
import org.example.utils.ValidationUtils;

import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class OrderConsoleView {
    private final OrderService orderService;

    public OrderConsoleView(OrderService orderService) {
        this.orderService = orderService;
    }

    public void runMenu(Scanner scanner) {
        int option;
        do {
            printOrderMenu();
            option = readInt(scanner);

            try {
                switch (option) {
                    case 1 -> createOrder(scanner);
                    case 2 -> listOrders();
                    case 3 -> listOrdersByUser(scanner);
                    case 4 -> listOrdersByStatus(scanner);
                    case 5 -> updateOrderStatus(scanner);
                    case 6 -> cancelOrder(scanner);
                    case 0 -> System.out.println("Voltando ao menu principal...");
                    default -> System.out.println("Opção inválida. Tente novamente.");
                }
            } catch (Exception e) {
                System.out.println("Erro: " + e.getMessage());
            }
        } while (option != 0);
    }

    private void printOrderMenu() {
        System.out.println("\n--- GESTÃO DE PEDIDOS ---");
        System.out.println("1 - Criar Novo Pedido");
        System.out.println("2 - Listar Todos os Pedidos");
        System.out.println("3 - Listar Pedidos por Usuário");
        System.out.println("4 - Listar Pedidos por Status");
        System.out.println("5 - Atualizar Status do Pedido");
        System.out.println("6 - Cancelar Pedido");
        System.out.println("0 - Voltar ao Menu Principal");
        System.out.print("Escolha uma opção: ");
    }

    private void createOrder(Scanner scanner) {
        System.out.print("UUID do Usuário: ");
        UUID userId = ValidationUtils.parseUuid(scanner.nextLine());
        System.out.print("UUID do Serviço/Produto: ");
        UUID productId = ValidationUtils.parseUuid(scanner.nextLine());
        System.out.print("Quantidade de horas contratadas: ");
        double hours = readDouble(scanner);

        Order order = orderService.createOrder(userId, productId, hours);
        System.out.println("Pedido criado com sucesso!");
        System.out.println(order);
    }

    private void listOrders() {
        List<Order> orders = orderService.listAllOrders();
        printOrderList(orders);
    }

    private void listOrdersByUser(Scanner scanner) {
        System.out.print("UUID do Usuário: ");
        UUID userId = ValidationUtils.parseUuid(scanner.nextLine());
        List<Order> orders = orderService.listOrdersByUserId(userId);
        printOrderList(orders);
    }

    private void listOrdersByStatus(Scanner scanner) {
        System.out.println("Selecione o Status:");
        System.out.println("1 - PENDING (Pendente)");
        System.out.println("2 - IN_PROGRESS (Em Andamento)");
        System.out.println("3 - COMPLETED (Concluído)");
        System.out.println("4 - CANCELLED (Cancelado)");
        System.out.print("Escolha: ");
        int choice = readInt(scanner);

        OrderStatus status = switch (choice) {
            case 1 -> OrderStatus.PENDING;
            case 2 -> OrderStatus.IN_PROGRESS;
            case 3 -> OrderStatus.COMPLETED;
            case 4 -> OrderStatus.CANCELLED;
            default -> throw new IllegalArgumentException("Opção de status inválida.");
        };

        List<Order> orders = orderService.listOrdersByStatus(status);
        printOrderList(orders);
    }

    private void updateOrderStatus(Scanner scanner) {
        System.out.print("UUID do Pedido: ");
        UUID orderId = ValidationUtils.parseUuid(scanner.nextLine());

        System.out.println("Selecione o Novo Status:");
        System.out.println("1 - PENDING (Pendente)");
        System.out.println("2 - IN_PROGRESS (Em Andamento)");
        System.out.println("3 - COMPLETED (Concluído)");
        System.out.println("4 - CANCELLED (Cancelado)");
        System.out.print("Escolha: ");
        int choice = readInt(scanner);

        OrderStatus status = switch (choice) {
            case 1 -> OrderStatus.PENDING;
            case 2 -> OrderStatus.IN_PROGRESS;
            case 3 -> OrderStatus.COMPLETED;
            case 4 -> OrderStatus.CANCELLED;
            default -> throw new IllegalArgumentException("Opção de status inválida.");
        };

        Order updated = orderService.updateOrderStatus(orderId, status);
        System.out.println("Status do pedido atualizado com sucesso!");
        System.out.println(updated);
    }

    private void cancelOrder(Scanner scanner) {
        System.out.print("UUID do Pedido a ser cancelado: ");
        UUID orderId = ValidationUtils.parseUuid(scanner.nextLine());
        orderService.cancelOrder(orderId);
        System.out.println("Pedido cancelado com sucesso.");
    }

    private void printOrderList(List<Order> orders) {
        if (orders.isEmpty()) {
            System.out.println("Nenhum pedido encontrado.");
        } else {
            System.out.println("\n--- Lista de Pedidos ---");
            for (Order order : orders) {
                System.out.println(order);
            }
        }
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
