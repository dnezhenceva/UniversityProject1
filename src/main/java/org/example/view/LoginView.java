package org.example.view;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.Controller.LoginController;
import org.example.model.User;

import java.sql.Connection;

public class LoginView {
    private final Connection connection;

    public LoginView(Connection connection) {
        this.connection = connection;
    }

    public void show(Stage stage) {
        Label usernameLabel = new Label("Имя пользователя:");
        TextField usernameField = new TextField();

        Label passwordLabel = new Label("Пароль:");
        PasswordField passwordField = new PasswordField();

        Button loginButton = new Button("Войти");

        Label messageLabel = new Label();

        loginButton.setOnAction(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();

            LoginController controller = new LoginController(connection);
            try {
                User user = controller.login(username, password);
                if (user != null) {
                    messageLabel.setText("Успешный вход! Роль: " + user.getRole());
                    new MainView(connection, user).show(new Stage());
                    stage.close();
                } else {
                    messageLabel.setText("Неверный логин или пароль.");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                messageLabel.setText("Ошибка входа.");
            }
        });

        VBox root = new VBox(10, usernameLabel, usernameField, passwordLabel, passwordField, loginButton, messageLabel);
        root.setPadding(new Insets(20));

        stage.setTitle("Вход в систему");
        stage.setScene(new Scene(root, 300, 250));
        stage.show();
    }
}
