package org.example.Controller;

import org.example.model.Predmet;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PredmetController {
    private final Connection connection;

    public PredmetController(Connection connection) {
        this.connection = connection;
    }

    public List<Predmet> getAllPredmeti() throws SQLException {
        List<Predmet> list = new ArrayList<>();
        String query = "SELECT * FROM predmet";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                list.add(new Predmet(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("kafedra_id")
                ));
            }
        }
        return list;
    }
}
