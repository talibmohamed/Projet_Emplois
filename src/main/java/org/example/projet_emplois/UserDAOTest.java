package org.example.projet_emplois;

import org.example.projet_emplois.util.Database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UserDAOTest {
    public static void main(String[] args) {
        Connection conn = Database.getConnection();

        // Test the connection and query the database
        if (conn != null) {
            try (Statement stmt = conn.createStatement()) {
                ResultSet rs = stmt.executeQuery("SELECT * FROM users");

                while (rs.next()) {
                    System.out.println("User: " + rs.getString("name") +
                            ", Email: " + rs.getString("email") +
                            ", Role: " + rs.getString("role"));
                }

            } catch (SQLException e) {
                System.err.println("Query failed: " + e.getMessage());
            } finally {
                Database.closeConnection();
            }
        }
    }
}
