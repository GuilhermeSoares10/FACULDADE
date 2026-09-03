package org.example.repository;

import org.example.entities.User;
import org.example.exceptions.DatabaseException;

import java.sql.*;
import java.util.*;

public class UserRepository implements EntityRepository<User> {
    private final Connection connection;

    public UserRepository(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void save(User user) {
        String query = "INSERT INTO users (uuid, name, email, password, role) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, user.getUuid().toString());
            stmt.setString(2, user.getName());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getPassword());
            stmt.setString(5, user.getRole());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException("Erro ao salvar usuário no banco de dados", e);
        }
    }

    public void update(User user) {
        String query = "UPDATE users SET name = ?, email = ?, password = ?, role = ? WHERE uuid = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, user.getName());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getPassword());
            stmt.setString(4, user.getRole());
            stmt.setString(5, user.getUuid().toString());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException("Erro ao atualizar usuário", e);
        }
    }

    @Override
    public Optional<User> findById(UUID id) {
        String query = "SELECT * FROM users WHERE uuid = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, id.toString());
            try (ResultSet resultSet = stmt.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(mapResultSetToUser(resultSet));
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException("Erro ao buscar usuário por ID", e);
        }
        return Optional.empty();
    }

    public Optional<User> findByEmail(String email) {
        String query = "SELECT * FROM users WHERE LOWER(email) = LOWER(?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, email);
            try (ResultSet resultSet = stmt.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(mapResultSetToUser(resultSet));
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException("Erro ao buscar usuário por e-mail", e);
        }
        return Optional.empty();
    }

    @Override
    public List<User> findAll() {
        String query = "SELECT * FROM users";
        List<User> users = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet resultSet = stmt.executeQuery()) {
            while (resultSet.next()) {
                users.add(mapResultSetToUser(resultSet));
            }
        } catch (SQLException e) {
            throw new DatabaseException("Erro ao listar usuários", e);
        }
        return users;
    }

    @Override
    public void deleteById(UUID id) {
        String query = "DELETE FROM users WHERE uuid = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, id.toString());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException("Erro ao deletar usuário por ID", e);
        }
    }

    public void deleteAll() {
        String query = "DELETE FROM users";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException("Erro ao remover todos os usuários", e);
        }
    }

    public boolean existsByEmail(String email) {
        String query = "SELECT COUNT(*) FROM users WHERE LOWER(email) = LOWER(?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, email);
            try (ResultSet resultSet = stmt.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException("Erro ao verificar existência de e-mail", e);
        }
        return false;
    }

    private User mapResultSetToUser(ResultSet resultSet) throws SQLException {
        return new User(
                UUID.fromString(resultSet.getString("uuid")),
                resultSet.getString("name"),
                resultSet.getString("email"),
                resultSet.getString("password"),
                resultSet.getString("role")
        );
    }
}
