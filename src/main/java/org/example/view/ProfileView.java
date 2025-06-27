package org.example.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import org.example.db.DatabaseManager;
import org.example.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ProfileView {
    private final User user;

    private TextField newUsername;
    private PasswordField newPassword;
    private Stage stage;

    public ProfileView(User user) {
        this.user = user;
    }

    public void show(Stage stage) {
        this.stage = stage;

        Label title = new Label("Изменение профиля");
        title.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        newUsername = new TextField(user.getUsername());
        newUsername.setPromptText("Новое имя пользователя");

        newPassword = new PasswordField();
        newPassword.setPromptText("Новый пароль");

        Button saveBtn = new Button("Сохранить изменения");
        saveBtn.setStyle(
                "-fx-background-color: #4CAF50;" +
                        "-fx-text-fill: white;" +
                        "-fx-font-size: 13px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-background-radius: 6;"
        );
        saveBtn.setOnAction(new SaveButtonHandler());

        VBox root = new VBox(15);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(25));
        root.setStyle("-fx-background-color: #f0f4f8;");
        root.getChildren().addAll(title, newUsername, newPassword, saveBtn);

        Scene scene = new Scene(root, 350, 250);
        stage.setTitle("Профиль пользователя");
        stage.setScene(scene);
        stage.show();
    }

    private class SaveButtonHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            try (Connection conn = DatabaseManager.getInstance().getConnection()) {
                String sql = "UPDATE users SET username = ?, password = ? WHERE id = ?";
                try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                    stmt.setString(1, newUsername.getText());
                    stmt.setString(2, newPassword.getText());
                    stmt.setInt(3, user.getId());
                    stmt.executeUpdate();
                    showAlert("Успех", "Данные успешно обновлены.");
                    stage.close();
                }
            } catch (SQLException ex) {
                showAlert("Ошибка", "Не удалось обновить данные: " + ex.getMessage());
            }
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
