package org.example.repository;

import org.example.entities.Order;
import org.example.entities.OrderStatus;
import org.example.exceptions.DatabaseException;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class JdbcOrderRepository implements OrderRepository {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private final Connection connection;

    public JdbcOrderRepository(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void save(Order order) {
        String query = "INSERT INTO orders (uuid, user_id, product_id, hours, total_amount, status, created_at) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, order.getUuid().toString());
            stmt.setString(2, order.getUserId().toString());
            stmt.setString(3, order.getProductId().toString());
            stmt.setDouble(4, order.getHours());
            stmt.setDouble(5, order.getTotalAmount());
            stmt.setString(6, order.getStatus().name());
            stmt.setString(7, order.getCreatedAt().format(DATE_FORMATTER));
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException("Erro ao salvar pedido no banco de dados", e);
        }
    }

    @Override
    public Optional<Order> findById(UUID id) {
        String query = "SELECT * FROM orders WHERE uuid = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, id.toString());
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapResultSetToOrder(rs));
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException("Erro ao buscar pedido por ID", e);
        }
        return Optional.empty();
    }

    @Override
    public List<Order> findAll() {
        String query = "SELECT * FROM orders ORDER BY created_at DESC";
        List<Order> orders = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                orders.add(mapResultSetToOrder(rs));
            }
        } catch (SQLException e) {
            throw new DatabaseException("Erro ao listar todos os pedidos", e);
        }
        return orders;
    }

    @Override
    public List<Order> findByUserId(UUID userId) {
        String query = "SELECT * FROM orders WHERE user_id = ? ORDER BY created_at DESC";
        List<Order> orders = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, userId.toString());
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    orders.add(mapResultSetToOrder(rs));
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException("Erro ao buscar pedidos por usuário", e);
        }
        return orders;
    }

    @Override
    public List<Order> findByStatus(OrderStatus status) {
        String query = "SELECT * FROM orders WHERE status = ? ORDER BY created_at DESC";
        List<Order> orders = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, status.name());
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    orders.add(mapResultSetToOrder(rs));
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException("Erro ao buscar pedidos por status", e);
        }
        return orders;
    }

    @Override
    public void updateStatus(UUID orderId, OrderStatus status) {
        String query = "UPDATE orders SET status = ? WHERE uuid = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, status.name());
            stmt.setString(2, orderId.toString());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException("Erro ao atualizar status do pedido", e);
        }
    }

    @Override
    public void deleteById(UUID id) {
        String query = "DELETE FROM orders WHERE uuid = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, id.toString());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException("Erro ao deletar pedido por ID", e);
        }
    }

    @Override
    public void deleteAll() {
        String query = "DELETE FROM orders";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException("Erro ao remover todos os pedidos", e);
        }
    }

    private Order mapResultSetToOrder(ResultSet rs) throws SQLException {
        UUID uuid = UUID.fromString(rs.getString("uuid"));
        UUID userId = UUID.fromString(rs.getString("user_id"));
        UUID productId = UUID.fromString(rs.getString("product_id"));
        double hours = rs.getDouble("hours");
        double totalAmount = rs.getDouble("total_amount");
        OrderStatus status = OrderStatus.fromString(rs.getString("status"));

        String dateStr = rs.getString("created_at");
        LocalDateTime createdAt = (dateStr != null && !dateStr.isEmpty())
                ? LocalDateTime.parse(dateStr, DATE_FORMATTER)
                : LocalDateTime.now();

        return new Order(uuid, userId, productId, hours, totalAmount, status, createdAt);
    }
}
