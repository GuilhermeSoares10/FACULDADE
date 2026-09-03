package org.example.repository;

import org.example.exceptions.DatabaseException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseManager {
    private final String dbUrl;

    public DatabaseManager(String dbUrl) {
        this.dbUrl = dbUrl;
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(dbUrl);
    }

    public void initializeTables(Connection conn) {
        try (Statement stmt = conn.createStatement()) {
            String createServicesTable =
                    "CREATE TABLE IF NOT EXISTS services (" +
                            "uuid TEXT PRIMARY KEY," +
                            "description TEXT NOT NULL," +
                            "hourly_rate REAL NOT NULL" +
                            ");";
            stmt.executeUpdate(createServicesTable);

            String createUsersTable =
                    "CREATE TABLE IF NOT EXISTS users (" +
                            "uuid TEXT PRIMARY KEY," +
                            "name TEXT NOT NULL," +
                            "email TEXT NOT NULL UNIQUE," +
                            "password TEXT NOT NULL," +
                            "role TEXT NOT NULL" +
                            ");";
            stmt.executeUpdate(createUsersTable);

            String createOrdersTable =
                    "CREATE TABLE IF NOT EXISTS orders (" +
                            "uuid TEXT PRIMARY KEY," +
                            "user_id TEXT NOT NULL," +
                            "product_id TEXT NOT NULL," +
                            "hours REAL NOT NULL," +
                            "total_amount REAL NOT NULL," +
                            "status TEXT NOT NULL," +
                            "created_at TEXT NOT NULL," +
                            "FOREIGN KEY(user_id) REFERENCES users(uuid)," +
                            "FOREIGN KEY(product_id) REFERENCES services(uuid)" +
                            ");";
            stmt.executeUpdate(createOrdersTable);

        } catch (SQLException e) {
            throw new DatabaseException("Erro ao inicializar as tabelas do banco de dados", e);
        }
    }
}
