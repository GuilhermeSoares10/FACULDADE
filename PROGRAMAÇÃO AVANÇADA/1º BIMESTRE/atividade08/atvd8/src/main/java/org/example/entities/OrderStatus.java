package org.example.entities;

import org.example.exceptions.ValidationException;

public enum OrderStatus {
    PENDING("Pendente"),
    IN_PROGRESS("Em Andamento"),
    COMPLETED("Concluído"),
    CANCELLED("Cancelado");

    private final String description;

    OrderStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public static OrderStatus fromString(String statusStr) {
        if (statusStr == null || statusStr.trim().isEmpty()) {
            throw new ValidationException("Status do pedido não pode ser vazio.");
        }
        for (OrderStatus status : OrderStatus.values()) {
            if (status.name().equalsIgnoreCase(statusStr.trim()) || status.getDescription().equalsIgnoreCase(statusStr.trim())) {
                return status;
            }
        }
        throw new ValidationException("Status do pedido inválido: " + statusStr);
    }
}
