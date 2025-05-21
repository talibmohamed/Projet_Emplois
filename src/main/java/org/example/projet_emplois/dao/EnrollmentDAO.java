package org.example.projet_emplois.dao;

import org.example.projet_emplois.model.Enrollment;
import org.example.projet_emplois.tools.Database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EnrollmentDAO {

    public static List<Enrollment> getAllEnrollments() {
        List<Enrollment> list = new ArrayList<>();

        String query = """
            SELECT e.id, u.name AS student_name, c.name AS course_name
            FROM enrollment e
            JOIN users u ON e.student_id = u.id
            JOIN courses c ON e.course_id = c.id
            ORDER BY student_name
        """;

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                list.add(new Enrollment(
                        rs.getInt("id"),
                        rs.getString("student_name"),
                        rs.getString("course_name")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public static boolean addEnrollment(int studentId, int courseId) {
        String check = "SELECT COUNT(*) FROM enrollment WHERE student_id = ? AND course_id = ?";
        String insert = "INSERT INTO enrollment (student_id, course_id) VALUES (?, ?)";

        try (Connection conn = Database.getConnection();
             PreparedStatement checkStmt = conn.prepareStatement(check)) {

            checkStmt.setInt(1, studentId);
            checkStmt.setInt(2, courseId);
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next() && rs.getInt(1) > 0) {
                return false; // already exists
            }

            try (PreparedStatement insertStmt = conn.prepareStatement(insert)) {
                insertStmt.setInt(1, studentId);
                insertStmt.setInt(2, courseId);
                return insertStmt.executeUpdate() > 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean removeEnrollment(int enrollmentId) {
        String query = "DELETE FROM enrollment WHERE id = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, enrollmentId);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
