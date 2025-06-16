package org.example.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Zanyatiya {
    private Connection connection;

    public Zanyatiya(Connection connection) {
        this.connection = connection;
    }

    public List<Zanyatie> getAll() throws SQLException {
        List<Zanyatie> list = new ArrayList<>();
        String query = "SELECT * FROM zanyatie";
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery(query);

        while (rs.next()) {
            list.add(new Zanyatie(
                    rs.getInt("id"),
                    rs.getInt("prepod_id"),
                    rs.getInt("gruppa_id"),
                    rs.getInt("predmet_id"),
                    rs.getInt("chasy"),
                    rs.getInt("chasy"), rs.getString("uchebniy_god")
            ));
        }

        return list;
    }
}
