package org.example.Controller;

import org.example.model.Gruppa;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GruppaController {
    private final Connection connection;

    public GruppaController(Connection connection) {
        this.connection = connection;
    }

    public List<Gruppa> getAllGruppy() throws SQLException {
        List<Gruppa> list = new ArrayList<>();
        String query = "SELECT * FROM gruppa";
        try (Statement stmt = connection.createStatement();
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
}
