package org.example;

import org.example.cli.MenuHandler;
import org.example.repository.*;
import org.example.services.OrderService;
import org.example.services.ProductService;
import org.example.services.UserService;

import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    private static final String DB_URL = "jdbc:sqlite:database.sqlite";

    public static void main(String[] args) {
        DatabaseManager databaseManager = new DatabaseManager(DB_URL);

        try (Connection conn = databaseManager.getConnection()) {
            if (conn != null) {
                databaseManager.initializeTables(conn);

                UserRepository userRepository = new UserRepository(conn);
                ProductRepository productRepository = new ProductRepository(conn);
                OrderRepository orderRepository = new JdbcOrderRepository(conn);

                UserService userService = new UserService(userRepository);
                ProductService productService = new ProductService(productRepository);
                OrderService orderService = new OrderService(orderRepository, userRepository, productRepository);

                MenuHandler menuHandler = new MenuHandler(userService, productService, orderService);
                menuHandler.start();
            } else {
                System.out.println("Falha na conexão com o banco de dados.");
            }
        } catch (SQLException e) {
            System.out.println("Erro na conexão ou banco de dados: " + e.getMessage());
        }
    }
}
