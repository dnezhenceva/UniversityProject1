package org.example.Controller;

import org.example.model.Zanyatie;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ZanyatieController {
    private final Connection connection;

    public ZanyatieController(Connection connection) {
        this.connection = connection;
    }

    public List<Zanyatie> getAllZanyatia() throws SQLException {
        List<Zanyatie> list = new ArrayList<>();
        String query = "SELECT * FROM zanyatie";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                list.add(new Zanyatie(
                        rs.getInt("id"),
                        rs.getInt("prepod_id"),
                        rs.getInt("gruppa_id"),
                        rs.getInt("predmet_id"),
                        rs.getInt("vid_id"),
                        rs.getInt("chasy"),
                        rs.getString("uchebniy_god")
                ));
            }
        }
        return list;
    }
}
