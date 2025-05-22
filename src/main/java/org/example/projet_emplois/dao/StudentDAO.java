package org.example.projet_emplois.dao;

import org.example.projet_emplois.tools.Database;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.example.projet_emplois.model.Schedule;

public class StudentDAO {

    public static List<Schedule> getScheduleForWeek(int studentId, LocalDate weekStart) {
        List<Schedule> list = new ArrayList<>();
        LocalDate weekEnd = weekStart.plusDays(6);

        String sql = """
        SELECT s.id,
               c.name AS course_name,
               r.name AS room_name,
               t.day, t.date, t.start_time, t.end_time,
               u.name AS teacher
        FROM schedule s
        JOIN courses c ON s.course_id = c.id
        LEFT JOIN rooms r ON s.room_id = r.id
        JOIN timeslots t ON s.timeslot_id = t.id
        LEFT JOIN users u ON c.teacher_id = u.id
        WHERE t.date BETWEEN ? AND ?
          AND s.id IN (
              SELECT schedule.id
              FROM schedule
              JOIN enrollment e ON e.course_id = schedule.course_id
              WHERE e.student_id = ?
          )
        ORDER BY t.date, t.start_time
    """;

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // 🟢 Correct order of parameters
            stmt.setDate(1, Date.valueOf(weekStart));
            stmt.setDate(2, Date.valueOf(weekEnd));
            stmt.setInt(3, studentId);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Schedule s = new Schedule(
                        rs.getInt("id"),
                        rs.getString("course_name"),
                        rs.getString("room_name"),
                        rs.getString("day"),
                        rs.getDate("date").toLocalDate(),
                        rs.getTime("start_time").toLocalTime(),
                        rs.getTime("end_time").toLocalTime(),
                        rs.getString("teacher")
                );
                list.add(s);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }



}
