package org.example.view;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.db.DatabaseManager;
import org.example.model.Users;

import java.sql.Connection;
import java.sql.SQLException;

public class RegisterView {

    public void show(Stage stage) {
        VBox root = new VBox(10);
        root.setPadding(new Insets(15));

        Label title = new Label("Регистрация");

        TextField usernameField = new TextField();
        usernameField.setPromptText("Имя пользователя");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Пароль");

        ComboBox<String> roleBox = new ComboBox<>();
        roleBox.getItems().addAll("user", "admin");
        roleBox.setValue("user");

        Button registerBtn = new Button("Зарегистрироваться");

        registerBtn.setOnAction(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();
            String role = roleBox.getValue();

            try (Connection conn = DatabaseManager.getInstance().getConnection()) {
                Users users = new Users(conn);
                users.addUser(username, password, role);
                showAlert("Успех", "Пользователь зарегистрирован.");
                stage.close();
            } catch (SQLException ex) {
                showAlert("Ошибка", "Не удалось зарегистрировать: " + ex.getMessage());
            }
        });

        root.getChildren().addAll(title, usernameField, passwordField, roleBox, registerBtn);
        Scene scene = new Scene(root, 300, 250);
        stage.setTitle("Регистрация");
        stage.setScene(scene);
        stage.show();
    }

    private void showAlert(String title, String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}