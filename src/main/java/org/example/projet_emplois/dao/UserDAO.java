package org.example.projet_emplois.dao;

import org.example.projet_emplois.model.*;
import org.example.projet_emplois.tools.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {

    public static User findByEmailAndPassword(String email, String password) {
        String query = "SELECT * FROM users WHERE email = ? AND password = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, email);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String role = rs.getString("role");

                switch (role) {
                    case "admin":
                        return new Admin(id, name, email, password);
                    case "teacher":
                        String teacherId = "T-" + id;
                        return new Teacher(id, name, email, password, teacherId);
                    case "student":
                        String studentId = "S-" + id;
                        return new Student(id, name, email, password, studentId);
                    default:
                        return null;
                }
            }
        } catch (SQLException e) {
            System.err.println("[UserDAO] Error: " + e.getMessage());
        }
        return null;
    }
}
