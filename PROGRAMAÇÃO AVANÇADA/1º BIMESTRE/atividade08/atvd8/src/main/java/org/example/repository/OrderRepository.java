package org.example.repository;

import org.example.entities.Order;
import org.example.entities.OrderStatus;

import java.util.List;
import java.util.UUID;

public interface OrderRepository extends EntityRepository<Order> {
    List<Order> findByUserId(UUID userId);
    List<Order> findByStatus(OrderStatus status);
    void updateStatus(UUID orderId, OrderStatus status);
    void deleteAll();
}
