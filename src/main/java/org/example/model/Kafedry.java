//package org.example.model;
//
//import org.example.db.DatabaseManager;
//
//import java.sql.*;
//import java.util.ArrayList;
//import java.util.List;
//
//public class Kafedry {
//    public List<Kafedra> getAll() throws SQLException {
//        List<Kafedra> list = new ArrayList<>();
//        String query = "SELECT id, name FROM kafedra";
//        try (Connection conn = DatabaseManager.getInstance().getConnection();
//             Statement stmt = conn.createStatement();
//             ResultSet rs = stmt.executeQuery(query)) {
//            while (rs.next()) {
//                list.add(new Kafedra(
//                        rs.getInt("id"),
//                        rs.getString("name")
//                ));
//            }
//        }
//        return list;
//    }
//
//    public void add(String name) throws SQLException {
//        String sql = "INSERT INTO kafedra (name) VALUES (?)";
//        try (Connection conn = DatabaseManager.getInstance().getConnection();
//             PreparedStatement stmt = conn.prepareStatement(sql)) {
//            stmt.setString(1, name);
//            stmt.executeUpdate();
//        }
//    }
//
//    public void delete(int id) throws SQLException {
//        String sql = "DELETE FROM kafedra WHERE id = ?";
//        try (Connection conn = DatabaseManager.getInstance().getConnection();
//             PreparedStatement stmt = conn.prepareStatement(sql)) {
//            stmt.setInt(1, id);
//            stmt.executeUpdate();
//        }
//    }
//}
