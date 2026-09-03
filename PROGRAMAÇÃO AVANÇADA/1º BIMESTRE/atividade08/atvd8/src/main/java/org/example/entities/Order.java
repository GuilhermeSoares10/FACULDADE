package org.example.entities;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class Order {
    private UUID uuid;
    private UUID userId;
    private UUID productId;
    private double hours;
    private double totalAmount;
    private OrderStatus status;
    private LocalDateTime createdAt;

    public Order(UUID uuid, UUID userId, UUID productId, double hours, double totalAmount, OrderStatus status, LocalDateTime createdAt) {
        this.uuid = uuid;
        this.userId = userId;
        this.productId = productId;
        this.hours = hours;
        this.totalAmount = totalAmount;
        this.status = status;
        this.createdAt = createdAt;
    }

    public Order(UUID userId, UUID productId, double hours, double hourlyRate) {
        this.uuid = UUID.randomUUID();
        this.userId = userId;
        this.productId = productId;
        this.hours = hours;
        this.totalAmount = hours * hourlyRate;
        this.status = OrderStatus.PENDING;
        this.createdAt = LocalDateTime.now();
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public UUID getProductId() {
        return productId;
    }

    public void setProductId(UUID productId) {
        this.productId = productId;
    }

    public double getHours() {
        return hours;
    }

    public void setHours(double hours) {
        this.hours = hours;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        String formattedDate = (createdAt != null) ? createdAt.format(formatter) : "N/A";
        return "Pedido{" +
                "uuid=" + uuid +
                ", usuarioId=" + userId +
                ", servicoId=" + productId +
                ", horas=" + hours +
                ", valorTotal=R$" + String.format("%.2f", totalAmount) +
                ", status=" + status.getDescription() +
                ", dataCriacao=" + formattedDate +
                '}';
    }
}
