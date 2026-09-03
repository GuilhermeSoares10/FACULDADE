package org.example.entities;

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
        if (statusStr == null) {
            return PENDING;
        }
        for (OrderStatus status : OrderStatus.values()) {
            if (status.name().equalsIgnoreCase(statusStr) || status.getDescription().equalsIgnoreCase(statusStr)) {
                return status;
            }
        }
        return PENDING;
    }
}
