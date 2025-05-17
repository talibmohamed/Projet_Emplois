package org.example.projet_emplois.dao;

import org.example.projet_emplois.model.Course;
import org.example.projet_emplois.tools.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CourseDAO {

    public static List<Course> getAllCourses() {
        List<Course> courses = new ArrayList<>();
        String query = "SELECT c.id, c.name, c.type, u.name AS teacher_name " +
                "FROM courses c LEFT JOIN users u ON c.teacher_id = u.id";

        Connection conn = Database.getConnection();
        if (conn == null) {
            System.err.println("[CourseDAO] Connection is null.");
            return courses;
        }

        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                courses.add(new Course(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("type") != null ? rs.getString("type") : "Aucun",
                        rs.getString("teacher_name") != null ? rs.getString("teacher_name") : "Aucun"
                ));
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return courses;
    }

    public static boolean assignTeacherToCourse(int courseId, int teacherId) {
        String query = "UPDATE courses SET teacher_id = ? WHERE id = ?";
        Connection conn = Database.getConnection();
        if (conn == null) {
            System.err.println("[CourseDAO] Connection is null.");
            return false;
        }

        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, teacherId);
            stmt.setInt(2, courseId);
            boolean success = stmt.executeUpdate() > 0;
            stmt.close();
            return success;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean addCourse(String name, String type) {
        String query = "INSERT INTO courses (name, type) VALUES (?, ?)";
        try {
            PreparedStatement stmt = Database.getConnection().prepareStatement(query);
            stmt.setString(1, name);
            stmt.setString(2, type);
            boolean success = stmt.executeUpdate() > 0;
            stmt.close();
            return success;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean updateCourse(int id, String name, String type) {
        String query = "UPDATE courses SET name = ?, type = ? WHERE id = ?";
        try {
            PreparedStatement stmt = Database.getConnection().prepareStatement(query);
            stmt.setString(1, name);
            stmt.setString(2, type);
            stmt.setInt(3, id);
            boolean success = stmt.executeUpdate() > 0;
            stmt.close();
            return success;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean deleteCourse(int id) {
        String query = "DELETE FROM courses WHERE id = ?";
        try {
            PreparedStatement stmt = Database.getConnection().prepareStatement(query);
            stmt.setInt(1, id);
            boolean success = stmt.executeUpdate() > 0;
            stmt.close();
            return success;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
