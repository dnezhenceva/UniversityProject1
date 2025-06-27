package org.example.model;

import org.example.db.DatabaseManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Gruppy {

    public List<Gruppa> getAll() throws SQLException {
        List<Gruppa> list = new ArrayList<>();
        String query = "SELECT * FROM gruppa";

        try (Connection conn = DatabaseManager.getInstance().getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                list.add(new Gruppa(
                        rs.getInt("id"),
                        rs.getString("name")
                ));
            }
        }

        return list;
    }

    public void add(String name) throws SQLException {
        String sql = "INSERT INTO gruppa (name) VALUES (?)";

        try (Connection conn = DatabaseManager.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, name);
            stmt.executeUpdate();
        }
    }

    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM gruppa WHERE id = ?";

        try (Connection conn = DatabaseManager.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}
