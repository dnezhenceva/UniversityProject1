package org.example.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Prepodavateli {
    private Connection connection;

    public Prepodavateli(Connection connection) {
        this.connection = connection;
    }

    public List<Prepodavatel> getAll() throws SQLException {
        List<Prepodavatel> list = new ArrayList<>();
        String query = "SELECT * FROM prepodavatel";
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery(query);

        while (rs.next()) {
            list.add(new Prepodavatel(
                    rs.getInt("id"),
                    rs.getString("fio"),
                    rs.getString("dolzhnost"),
                    rs.getString("uchenaya_stepen"),
                    rs.getInt("stazh"),
                    rs.getInt("kafedra_id")
            ));
        }

        return list;
    }
}
