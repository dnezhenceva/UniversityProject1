package org.example.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import org.example.Controller.PredmetController;
import org.example.model.Predmet;
import org.example.model.User;

import java.sql.SQLException;

public class PredmetView {

    private final PredmetController controller;
    private final User user;

    private TextField nameField;
    private ListView<Predmet> listView;

    public PredmetView(User user) {
        this.user = user;
        this.controller = new PredmetController(this);
    }

    public void show(Stage stage) {
        Label title = new Label("Список предметов");
        title.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        listView = new ListView<>();
        listView.setPrefHeight(200);
        listView.setStyle("-fx-font-size: 14px;");
        loadPredmeti();

        VBox layout = new VBox(15);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.CENTER);

        layout.getChildren().addAll(title, listView);

        if ("admin".equalsIgnoreCase(user.getRole())) {
            nameField = new TextField();
            nameField.setPromptText("Название предмета");
            nameField.setPrefWidth(250);

            Button addButton = createStyledButton("Добавить");
            Button deleteButton = createStyledButton("Удалить");
            Button refreshButton = createStyledButton("Обновить");

            HBox inputBox = new HBox(10, nameField, addButton);
            inputBox.setAlignment(Pos.CENTER);

            HBox actionBox = new HBox(10, deleteButton, refreshButton);
            actionBox.setAlignment(Pos.CENTER);

            addButton.setOnAction(new AddButtonHandler());
            deleteButton.setOnAction(new DeleteButtonHandler());
            refreshButton.setOnAction(new RefreshButtonHandler());

            layout.getChildren().addAll(inputBox, actionBox);
        }

        layout.setStyle("-fx-background-color: #f4f6f8; -fx-border-radius: 10; -fx-background-radius: 10;");

        Scene scene = new Scene(layout, 450, 400);
        stage.setTitle("Предметы");
        stage.setScene(scene);
        stage.show();
    }

    private void loadPredmeti() {
        try {
            listView.getItems().setAll(controller.getAllPredmeti());
        } catch (SQLException e) {
            showAlert("Ошибка", "Не удалось загрузить предметы:\n" + e.getMessage());
        }
    }

    private class AddButtonHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            String name = nameField.getText();
            if (name.isEmpty()) {
                showAlert("Ошибка", "Название предмета не может быть пустым.");
                return;
            }

            try {
                controller.addPredmet(name);
                loadPredmeti();
                nameField.clear();
            } catch (SQLException ex) {
                showAlert("Ошибка", "Не удалось добавить предмет:\n" + ex.getMessage());
            }
        }
    }

    private class DeleteButtonHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            Predmet selected = listView.getSelectionModel().getSelectedItem();
            if (selected != null) {
                try {
                    controller.deletePredmet(selected.getId());
                    loadPredmeti();
                } catch (SQLException ex) {
                    showAlert("Ошибка", "Не удалось удалить предмет:\n" + ex.getMessage());
                }
            } else {
                showAlert("Ошибка", "Сначала выберите предмет.");
            }
        }
    }

    private class RefreshButtonHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            loadPredmeti();
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private Button createStyledButton(String text) {
        Button btn = new Button(text);
        btn.setPrefWidth(100);
        btn.setStyle(
                "-fx-background-color: #3f51b5;" +
                        "-fx-text-fill: white;" +
                        "-fx-font-weight: bold;" +
                        "-fx-background-radius: 5;" +
                        "-fx-font-size: 13px;"
        );
        return btn;
    }
}
