package org.example.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Predmety {
    private Connection connection;

    public Predmety(Connection connection) {
        this.connection = connection;
    }

    public List<Predmet> getAll() throws SQLException {
        List<Predmet> list = new ArrayList<>();
        String query = "SELECT * FROM predmet";
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery(query);

        while (rs.next()) {
            list.add(new Predmet(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getInt("kafedra_id")
            ));
        }

        return list;
    }
}

