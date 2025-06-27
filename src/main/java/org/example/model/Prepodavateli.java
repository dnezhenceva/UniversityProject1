package org.example.model;

import org.example.db.DatabaseManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Prepodavateli {

    public List<Prepodavatel> getAll() throws SQLException {
        List<Prepodavatel> list = new ArrayList<>();
        String sql = "SELECT * FROM prepodavatel";
        try (Connection conn = DatabaseManager.getInstance().getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new Prepodavatel(
                        rs.getInt("id"),
                        rs.getString("familiya"),
                        rs.getString("imya"),
                        rs.getString("otchestvo"),
                        rs.getString("dolzhnost"),
                        rs.getString("uchenaya_stepen"),
                        rs.getInt("stazh")
                ));
            }
        }
        return list;
    }

    public void add(String familiya, String imya, String otchestvo, String stepen, String dolzhnost, int stazh) throws SQLException {
        String sql = "INSERT INTO prepodavatel (familiya, imya, otchestvo, uchenaya_stepen, dolzhnost, stazh) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseManager.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, familiya);
            stmt.setString(2, imya);
            stmt.setString(3, otchestvo);
            stmt.setString(4, stepen);
            stmt.setString(5, dolzhnost);
            stmt.setInt(6, stazh);
            stmt.executeUpdate();
        }
    }

    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM prepodavatel WHERE id = ?";
        try (Connection conn = DatabaseManager.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    public List<String> getNagruzkaByPrepod(int prepodId) throws SQLException {
        List<String> nagruzkaList = new ArrayList<>();
        String sql = "SELECT predmet.name, raspred_nagruzka.vid, raspred_nagruzka.chasy " +
                "FROM raspred_nagruzka " +
                "JOIN predmet_nagruzka ON raspred_nagruzka.id = predmet_nagruzka.id_nagruzki " +
                "JOIN predmet ON predmet.id = predmet_nagruzka.id_predmeta " +
                "WHERE raspred_nagruzka.prepod_id = ?";
        try (Connection conn = DatabaseManager.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, prepodId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    String predmet = rs.getString("name");
                    String vid = rs.getString("vid");
                    int chasy = rs.getInt("chasy");
                    nagruzkaList.add(predmet + " (" + vid + "): " + chasy + " ч.");
                }
            }
        }
        return nagruzkaList;
    }
}
