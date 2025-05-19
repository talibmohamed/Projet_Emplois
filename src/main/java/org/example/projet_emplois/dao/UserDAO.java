package org.example.projet_emplois.dao;

import org.example.projet_emplois.model.*;
import org.example.projet_emplois.tools.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    public static User findByEmailAndPassword(String email, String password) {
        String query = "SELECT * FROM users WHERE email = ? AND password = ?";
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = Database.getConnection();
            stmt = conn.prepareStatement(query);
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
                        return new Teacher(id, name, email, password);
                    case "student":
                        return new Student(id, name, email, password);
                    default:
                        return null;
                }
            }
        } catch (SQLException e) {
            System.err.println("[UserDAO] Error: " + e.getMessage());
        }

        return null;
    }

    public static List<Student> getAllStudents() {
        List<Student> students = new ArrayList<>();
        String query = "SELECT * FROM users WHERE role = 'student'";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                students.add(new Student(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("password")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return students;
    }

    public static List<Teacher> getAllTeachers() {
        List<Teacher> teachers = new ArrayList<>();
        String query = "SELECT * FROM users WHERE role = 'teacher'";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                teachers.add(new Teacher(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("password")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return teachers;
    }

    public static boolean addStudent(int id, String name, String email, String password) {
        String sql = "INSERT INTO users (id, name, email, password, role) VALUES (?, ?, ?, ?, 'student')";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.setString(2, name);
            stmt.setString(3, email.toLowerCase());
            stmt.setString(4, password);
            return stmt.executeUpdate() == 1;

        } catch (SQLException e) {
            System.err.println("[addStudent] SQL Error: " + e.getMessage());
            return false;
        }
    }

    public static boolean deleteUserById(int id) {
        String sql = "DELETE FROM users WHERE id = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            return stmt.executeUpdate() == 1;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}