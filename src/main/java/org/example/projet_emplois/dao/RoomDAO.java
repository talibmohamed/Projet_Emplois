package org.example.projet_emplois.dao;

import org.example.projet_emplois.model.Equipment;
import org.example.projet_emplois.model.Room;
import org.example.projet_emplois.tools.Database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RoomDAO {

    public static List<Room> getAllRooms() {
        List<Room> rooms = new ArrayList<>();

        String query = """
        SELECT r.id AS room_id, r.name AS room_name, r.capacity,
               e.id AS equipment_id, e.name AS equipment_name, re.quantity
        FROM rooms r
        LEFT JOIN room_equipment re ON r.id = re.room_id
        LEFT JOIN equipment e ON re.equipment_id = e.id
        ORDER BY r.id
    """;

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            int currentRoomId = -1;
            Room currentRoom = null;

            while (rs.next()) {
                int roomId = rs.getInt("room_id");

                if (currentRoom == null || currentRoom.getId() != roomId) {
                    if (currentRoom != null) {
                        rooms.add(currentRoom);
                    }

                    currentRoom = new Room(
                            roomId,
                            rs.getString("room_name"),
                            rs.getInt("capacity"),
                            new ArrayList<>()
                    );
                }

                int equipmentId = rs.getInt("equipment_id");
                if (!rs.wasNull()) {
                    Equipment equipment = new Equipment(
                            equipmentId,
                            rs.getString("equipment_name"),
                            rs.getInt("quantity")  // from room_equipment
                    );
                    currentRoom.getEquipmentList().add(equipment);
                }
            }

            if (currentRoom != null) {
                rooms.add(currentRoom);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return rooms;
    }

    public static boolean addRoom(String name, int capacity) {
        String query = "INSERT INTO rooms (name, capacity) VALUES (?, ?)";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, name);
            stmt.setInt(2, capacity);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean deleteRoom(int roomId) {
        String deleteEquip = "DELETE FROM room_equipment WHERE room_id = ?";
        String deleteRoom = "DELETE FROM rooms WHERE id = ?";

        try (Connection conn = Database.getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement stmt1 = conn.prepareStatement(deleteEquip);
                 PreparedStatement stmt2 = conn.prepareStatement(deleteRoom)) {

                stmt1.setInt(1, roomId);
                stmt1.executeUpdate();

                stmt2.setInt(1, roomId);
                boolean success = stmt2.executeUpdate() > 0;

                conn.commit();
                return success;
            } catch (SQLException e) {
                conn.rollback();
                e.printStackTrace();
            } finally {
                conn.setAutoCommit(true);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public static boolean assignEquipment(int roomId, int equipmentId, int quantity) {
        String query = "INSERT INTO room_equipment (room_id, equipment_id, quantity) VALUES (?, ?, ?) " +
                "ON CONFLICT (room_id, equipment_id) DO UPDATE SET quantity = EXCLUDED.quantity";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, roomId);
            stmt.setInt(2, equipmentId);
            stmt.setInt(3, quantity);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean removeEquipment(int roomId, int equipmentId) {
        String query = "DELETE FROM room_equipment WHERE room_id = ? AND equipment_id = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, roomId);
            stmt.setInt(2, equipmentId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}
