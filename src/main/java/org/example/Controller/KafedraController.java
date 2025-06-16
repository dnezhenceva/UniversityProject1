package org.example.Controller;

import org.example.model.Kafedra;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class KafedraController {
    private final Connection connection;

    public KafedraController(Connection connection) {
        this.connection = connection;
    }

    public List<Kafedra> getAllKafedras() throws SQLException {
        List<Kafedra> kafedras = new ArrayList<>();
        String query = "SELECT * FROM kafedra";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                kafedras.add(new Kafedra(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("info_o_nagruzke")
                ));
            }
        }
        return kafedras;
    }
}
