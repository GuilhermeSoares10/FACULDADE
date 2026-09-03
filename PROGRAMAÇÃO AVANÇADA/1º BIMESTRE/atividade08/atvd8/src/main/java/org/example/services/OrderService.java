package org.example.services;

import org.example.entities.Order;
import org.example.entities.OrderStatus;
import org.example.entities.Product;
import org.example.entities.User;
import org.example.exceptions.EntityNotFoundException;
import org.example.exceptions.ValidationException;
import org.example.repository.OrderRepository;
import org.example.repository.ProductRepository;
import org.example.repository.UserRepository;
import org.example.utils.ValidationUtils;

import java.util.List;
import java.util.UUID;

public class OrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public OrderService(OrderRepository orderRepository, UserRepository userRepository, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    public Order createOrder(UUID userId, UUID productId, double hours) {
        ValidationUtils.validateNotNull(userId, "ID do usuário");
        ValidationUtils.validateNotNull(productId, "ID do serviço");
        ValidationUtils.validatePositive(hours, "quantidade de horas");

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado para criação de pedido: " + userId));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Serviço não encontrado para criação de pedido: " + productId));

        Order order = new Order(user.getUuid(), product.getUuid(), hours, product.getHourlyRate());
        orderRepository.save(order);
        return order;
    }

    public Order getOrderById(UUID orderId) {
        ValidationUtils.validateNotNull(orderId, "ID do pedido");
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Pedido com ID " + orderId + " não encontrado."));
    }

    public List<Order> listAllOrders() {
        return orderRepository.findAll();
    }

    public List<Order> listOrdersByUserId(UUID userId) {
        ValidationUtils.validateNotNull(userId, "ID do usuário");
        userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado: " + userId));
        return orderRepository.findByUserId(userId);
    }

    public List<Order> listOrdersByStatus(OrderStatus status) {
        ValidationUtils.validateNotNull(status, "status do pedido");
        return orderRepository.findByStatus(status);
    }

    public Order updateOrderStatus(UUID orderId, OrderStatus newStatus) {
        ValidationUtils.validateNotNull(newStatus, "novo status");
        Order order = getOrderById(orderId);

        if (order.getStatus() == OrderStatus.CANCELLED) {
            throw new ValidationException("Não é possível alterar o status de um pedido cancelado.");
        }

        if (order.getStatus() == OrderStatus.COMPLETED && newStatus != OrderStatus.COMPLETED) {
            throw new ValidationException("Não é possível alterar o status de um pedido já concluído.");
        }

        orderRepository.updateStatus(orderId, newStatus);
        order.setStatus(newStatus);
        return order;
    }

    public void cancelOrder(UUID orderId) {
        updateOrderStatus(orderId, OrderStatus.CANCELLED);
    }

    public void clearAllOrders() {
        orderRepository.deleteAll();
    }
}
