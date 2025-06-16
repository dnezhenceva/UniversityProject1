package org.example.Controller;

import org.example.model.Prepodavatel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PrepodavatelController {
    private final Connection connection;

    public PrepodavatelController(Connection connection) {
        this.connection = connection;
    }

    public List<Prepodavatel> getAllPrepodavateli() throws SQLException {
        List<Prepodavatel> list = new ArrayList<>();
        String query = "SELECT * FROM prepodavatel";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
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
        }
        return list;
    }
}
