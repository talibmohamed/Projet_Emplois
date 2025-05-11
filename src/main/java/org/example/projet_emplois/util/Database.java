package org.example.projet_emplois.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    private static final String URL = "jdbc:postgresql://aws-0-eu-west-3.pooler.supabase.com:5432/postgres";
    private static final String USER = "postgres.ccrxqxjndnxzwltvpyge";
    private static final String PASSWORD = "fZDM9O5j0kVRHkFU";

    private static Connection connection = null;

    public static Connection getConnection() {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("[DB] Connected to Supabase PostgreSQL.");
            } catch (SQLException e) {
                System.err.println("[DB] Connection failed: " + e.getMessage());
            }
        }
        return connection;
    }

    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                connection = null;
                System.out.println("[DB] Connection closed.");
            } catch (SQLException e) {
                System.err.println("[DB] Closing failed: " + e.getMessage());
            }
        }
    }
}
