package org.example.projet_emplois.dao;

import org.example.projet_emplois.model.Equipment;
import org.example.projet_emplois.tools.Database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EquipmentDAO {

    public static List<Equipment> getAllEquipments() {
        List<Equipment> list = new ArrayList<>();
        String query = "SELECT id, name FROM equipment ORDER BY name";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                list.add(new Equipment(
                        rs.getInt("id"),
                        rs.getString("name"),
                        0
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public static boolean addEquipment(String name) {
        String query = "INSERT INTO equipment (name) VALUES (?)";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, name);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public static boolean deleteEquipment(int id) {
        String query = "DELETE FROM equipment WHERE id = ?";
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
