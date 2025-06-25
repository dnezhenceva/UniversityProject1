package org.example.model;

import org.example.db.DatabaseManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RaspredNagruzkaRepository {

    public List<RaspredNagruzka> getAll() throws SQLException {
        List<RaspredNagruzka> list = new ArrayList<>();

        String sql = """
            SELECT rn.id, rn.prepod_id, rn.gruppa_id, rn.predmet_id, rn.chasy, rn.god, rn.vid, rn.kolichestvo,
                   p.fio AS prepod_name, g.name AS gruppa_name, pr.name AS predmet_name
            FROM raspred_nagruzka rn
            JOIN prepodavatel p ON rn.prepod_id = p.id
            JOIN gruppa g ON rn.gruppa_id = g.id
            JOIN predmet pr ON rn.predmet_id = pr.id
        """;

        try (Connection conn = DatabaseManager.getInstance().getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                RaspredNagruzka r = new RaspredNagruzka(
                        rs.getInt("id"),
                        rs.getInt("prepod_id"),
                        rs.getInt("gruppa_id"),
                        rs.getInt("predmet_id"),
                        rs.getString("vid"),
                        rs.getInt("chasy"),
                        rs.getString("god"),
                        rs.getInt("kolichestvo"),
                        rs.getString("prepod_name"),
                        rs.getString("gruppa_name"),
                        rs.getString("predmet_name")
                );
                list.add(r);
            }
        }
        return list;
    }

    public void add(int predmetId, int prepodId, int gruppaId, String vid, int chasy, int kolichestvo) throws SQLException {
        String sql = """
            INSERT INTO raspred_nagruzka (predmet_id, prepod_id, gruppa_id, vid, chasy, god, kolichestvo)
            VALUES (?, ?, ?, ?, ?, YEAR(CURDATE()), ?)
        """;

        try (Connection conn = DatabaseManager.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, predmetId);
            stmt.setInt(2, prepodId);
            stmt.setInt(3, gruppaId);
            stmt.setString(4, vid);
            stmt.setInt(5, chasy);
            stmt.setInt(6, kolichestvo);
            stmt.executeUpdate();
        }
    }

    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM raspred_nagruzka WHERE id = ?";
        try (Connection conn = DatabaseManager.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}
