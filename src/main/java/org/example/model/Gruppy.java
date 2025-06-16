package org.example.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Gruppy {
    private Connection connection;

    public Gruppy(Connection connection) {
        this.connection = connection;
    }

    public List<Gruppa> getAll() throws SQLException {
        List<Gruppa> list = new ArrayList<>();
        String query = "SELECT * FROM gruppa";
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery(query);

        while (rs.next()) {
            list.add(new Gruppa(
                    rs.getInt("id"),
                    rs.getString("name")
            ));
        }

        return list;
    }
}
