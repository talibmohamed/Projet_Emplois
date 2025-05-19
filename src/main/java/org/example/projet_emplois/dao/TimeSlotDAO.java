package org.example.projet_emplois.dao;

import org.example.projet_emplois.model.TimeSlot;
import org.example.projet_emplois.tools.Database;

import java.sql.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class TimeSlotDAO {

    public static List<TimeSlot> getAllTimeSlots() {
        List<TimeSlot> slots = new ArrayList<>();
        String query = "SELECT * FROM timeslots ORDER BY day, start_time";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                slots.add(new TimeSlot(
                        rs.getInt("id"),
                        rs.getString("day"),
                        rs.getTime("start_time").toLocalTime(),
                        rs.getTime("end_time").toLocalTime()
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return slots;
    }

    public static boolean addTimeSlot(String day, LocalTime start, LocalTime end) {
        String query = "INSERT INTO timeslots (day, start_time, end_time) VALUES (?, ?, ?)";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, day);
            stmt.setTime(2, Time.valueOf(start));
            stmt.setTime(3, Time.valueOf(end));
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean deleteTimeSlot(int id) {
        String query = "DELETE FROM timeslots WHERE id = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
