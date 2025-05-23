package org.example.projet_emplois.tools;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    private static final String URL = "jdbc:postgresql://db.ccrxqxjndnxzwltvpyge.supabase.co:5432/postgres?user=postgres&password=AayqYCkTGUMCGP9z";

    private static Connection connection = null;

    public static Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(URL);
                System.out.println("[DB] Connected to Supabase PostgreSQL.");
            }
        } catch (SQLException e) {
            System.err.println("[DB] Connection failed: " + e.getMessage());
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