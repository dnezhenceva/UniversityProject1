package org.example.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Kafedras {
    private List<Kafedra> kafedraList;

    public Kafedras(Connection connection) throws SQLException {
        this.kafedraList = new ArrayList<>();
        loadData(connection);
    }

    private void loadData(Connection connection) throws SQLException {
        String query = "SELECT * FROM kafedra";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String info = resultSet.getString("info_o_nagruzke");
                kafedraList.add(new Kafedra(id, name, info));
            }
        }
    }

    public List<Kafedra> getAll() {
        return kafedraList;
    }
}
