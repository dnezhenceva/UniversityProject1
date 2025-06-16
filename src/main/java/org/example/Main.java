package org.example;
import org.example.model.Kafedra;
import org.example.model.Kafedras;


import java.sql.Connection;
import java.sql.DriverManager;

public class Main {
    public static void main(String[] args) {
        String url = "jdbc:mysql://127.0.0.1:3306/university";
        String user = "root";
        String password = "";

        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            Kafedras kafedras = new Kafedras(connection);
            for (Kafedra k : kafedras.getAll()) {
                System.out.println(k);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
