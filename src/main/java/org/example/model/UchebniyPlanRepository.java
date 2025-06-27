package org.example.model;
import org.example.db.DatabaseManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UchebniyPlanRepository {

    public void add(int predmetId, int semestr, boolean obyazatelniy) throws SQLException {
        String sql = "INSERT INTO uchebniy_plan (predmet_id, semestr, obyazatelniy) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseManager.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, predmetId);
            stmt.setInt(2, semestr);
            stmt.setBoolean(3, obyazatelniy);
            stmt.executeUpdate();
        }
    }

    public List<UchebniyPlan> getAll() throws SQLException {
        List<UchebniyPlan> list = new ArrayList<>();
        String sql = """
            SELECT up.id, up.predmet_id, up.semestr, up.obyazatelniy, p.name AS predmet_name
            FROM uchebniy_plan up
            JOIN predmet p ON up.predmet_id = p.id
        """;

        try (Connection conn = DatabaseManager.getInstance().getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int id = rs.getInt("id");
                int predmetId = rs.getInt("predmet_id");
                int semestr = rs.getInt("semestr");
                boolean obyazatelniy = rs.getBoolean("obyazatelniy");
                String predmetName = rs.getString("predmet_name");

                list.add(new UchebniyPlan(id, predmetId, semestr, obyazatelniy, predmetName));
            }
        }

        return list;
    }

    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM uchebniy_plan WHERE id = ?";
        try (Connection conn = DatabaseManager.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}
