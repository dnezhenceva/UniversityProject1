package org.example.view;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.example.model.User;

import java.sql.Connection;

public class MainView {
    private final Connection connection;
    private final User user;

    public MainView(Connection connection, User user) {
        this.connection = connection;
        this.user = user;
    }

    public void show(Stage stage) {
        Label welcome = new Label("Добро пожаловать, " + user.getUsername() + " (" + user.getRole() + ")");
        StackPane root = new StackPane(welcome);
        Scene scene = new Scene(root, 400, 300);

        stage.setTitle("Главное окно");
        stage.setScene(scene);
        stage.show();
    }
}
