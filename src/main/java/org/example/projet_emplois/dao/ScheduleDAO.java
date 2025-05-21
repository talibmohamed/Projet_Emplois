package org.example.projet_emplois.dao;

import org.example.projet_emplois.model.Schedule;
import org.example.projet_emplois.tools.Database;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class ScheduleDAO {

    public static List<Schedule> getAllSchedules() {
        List<Schedule> schedules = new ArrayList<>();

        String query = """
            SELECT s.id, c.name AS course_name, r.name AS room_name,
                   t.day, t.date, t.start_time, t.end_time
            FROM schedule s
            JOIN courses c ON s.course_id = c.id
            LEFT JOIN rooms r ON s.room_id = r.id
            JOIN timeslots t ON s.timeslot_id = t.id
            ORDER BY t.date, t.start_time
        """;

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                schedules.add(new Schedule(
                        rs.getInt("id"),
                        rs.getString("course_name"),
                        rs.getString("room_name"),
                        rs.getString("day"),
                        rs.getDate("date").toLocalDate(),
                        rs.getTime("start_time").toLocalTime(),
                        rs.getTime("end_time").toLocalTime()
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return schedules;
    }

    public static boolean addSchedule(int courseId, Integer roomId, int timeslotId) {
        String conflictQuery = """
        SELECT COUNT(*) FROM schedule
        WHERE timeslot_id = ? AND (
            (room_id = ?)
            OR (room_id IS NULL AND ? IS NULL)
        )
    """;

        String insertQuery = "INSERT INTO schedule (course_id, room_id, timeslot_id) VALUES (?, ?, ?)";

        try (Connection conn = Database.getConnection()) {

            // Check for room conflict
            try (PreparedStatement checkStmt = conn.prepareStatement(conflictQuery)) {
                checkStmt.setInt(1, timeslotId);
                if (roomId != null) {
                    checkStmt.setInt(2, roomId);
                    checkStmt.setInt(3, roomId);
                } else {
                    checkStmt.setNull(2, Types.INTEGER);
                    checkStmt.setNull(3, Types.INTEGER);
                }

                ResultSet rs = checkStmt.executeQuery();
                if (rs.next() && rs.getInt(1) > 0) {
                    System.out.println("⛔ Conflit : cette salle est déjà utilisée à ce créneau.");
                    return false;
                }
            }

            // No conflict — proceed with insert
            try (PreparedStatement stmt = conn.prepareStatement(insertQuery)) {
                stmt.setInt(1, courseId);
                if (roomId != null) {
                    stmt.setInt(2, roomId);
                } else {
                    stmt.setNull(2, Types.INTEGER);
                }
                stmt.setInt(3, timeslotId);

                return stmt.executeUpdate() > 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }


    public static boolean deleteSchedule(int scheduleId) {
        String query = "DELETE FROM schedule WHERE id = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, scheduleId);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}
