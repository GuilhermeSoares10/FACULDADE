package org.example.repository;

import org.example.entities.Order;
import org.example.entities.OrderStatus;
import org.example.entities.Product;
import org.example.entities.User;
import org.example.exceptions.EntityNotFoundException;
import org.example.exceptions.ValidationException;
import org.example.services.OrderService;
import org.example.services.ProductService;
import org.example.services.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ServicesIntegrationTest {

    private Connection connection;
    private UserService userService;
    private ProductService productService;
    private OrderService orderService;

    @BeforeEach
    void setUp() throws SQLException {
        connection = DriverManager.getConnection("jdbc:sqlite::memory:");
        DatabaseManager dbManager = new DatabaseManager("jdbc:sqlite::memory:");
        dbManager.initializeTables(connection);

        UserRepository userRepository = new UserRepository(connection);
        ProductRepository productRepository = new ProductRepository(connection);
        OrderRepository orderRepository = new JdbcOrderRepository(connection);

        userService = new UserService(userRepository);
        productService = new ProductService(productRepository);
        orderService = new OrderService(orderRepository, userRepository, productRepository);
    }

    @AfterEach
    void tearDown() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }

    @Test
    @DisplayName("Deve cadastrar e autenticar usuário com sucesso")
    void testUserRegistrationAndAuthentication() {
        User user = userService.registerUser("João Silva", "joao@email.com", "senha123", "CLIENTE");
        assertNotNull(user.getUuid());

        User authenticated = userService.authenticate("joao@email.com", "senha123");
        assertEquals(user.getUuid(), authenticated.getUuid());
        assertEquals("João Silva", authenticated.getName());
    }

    @Test
    @DisplayName("Deve rejeitar cadastro de usuário com e-mail duplicado")
    void testDuplicateUserRegistration() {
        userService.registerUser("João Silva", "joao@email.com", "senha123", "CLIENTE");
        assertThrows(ValidationException.class, () ->
                userService.registerUser("Outro João", "joao@email.com", "senha456", "CLIENTE")
        );
    }

    @Test
    @DisplayName("Deve cadastrar e buscar serviço/produto")
    void testProductManagement() {
        Product product = productService.registerProduct("Consultoria em Java", 150.0);
        assertNotNull(product.getUuid());

        Product fetched = productService.getProductById(product.getUuid());
        assertEquals("Consultoria em Java", fetched.getDescription());
        assertEquals(150.0, fetched.getHourlyRate());

        List<Product> results = productService.searchByDescription("Java");
        assertEquals(1, results.size());
    }

    @Test
    @DisplayName("Deve criar e atualizar status do pedido")
    void testOrderCreationAndLifecycle() {
        User user = userService.registerUser("Maria Souza", "maria@email.com", "senha123", "CLIENTE");
        Product product = productService.registerProduct("Desenvolvimento Web", 100.0);

        Order order = orderService.createOrder(user.getUuid(), product.getUuid(), 10.0);
        assertNotNull(order.getUuid());
        assertEquals(1000.0, order.getTotalAmount());
        assertEquals(OrderStatus.PENDING, order.getStatus());

        Order updatedOrder = orderService.updateOrderStatus(order.getUuid(), OrderStatus.IN_PROGRESS);
        assertEquals(OrderStatus.IN_PROGRESS, updatedOrder.getStatus());

        orderService.updateOrderStatus(order.getUuid(), OrderStatus.COMPLETED);

        // Tentar alterar status de um pedido concluído deve falhar
        assertThrows(ValidationException.class, () ->
                orderService.updateOrderStatus(order.getUuid(), OrderStatus.CANCELLED)
        );
    }

    @Test
    @DisplayName("Deve lançar EntityNotFoundException ao criar pedido para usuário inexistente")
    void testCreateOrderWithNonExistentUser() {
        Product product = productService.registerProduct("Desenvolvimento Mobile", 120.0);
        UUID nonExistentUserId = UUID.randomUUID();

        assertThrows(EntityNotFoundException.class, () ->
                orderService.createOrder(nonExistentUserId, product.getUuid(), 5.0)
        );
    }
}
