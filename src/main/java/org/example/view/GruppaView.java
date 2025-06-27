package org.example.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import org.example.Controller.GruppaController;
import org.example.model.Gruppa;
import org.example.model.User;

import java.sql.SQLException;
import java.util.List;

public class GruppaView {
    private final GruppaController controller;
    private final User user;
    private ListView<Gruppa> listView;

    public GruppaView(User user) {
        this.user = user;
        this.controller = new GruppaController(this);
    }

    public void show(Stage stage) {
        Label title = new Label("Список групп");
        title.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        listView = new ListView<>();
        refreshList();
        listView.setPrefHeight(200);
        listView.setStyle("-fx-font-size: 14px;");

        VBox layout = new VBox(15);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.CENTER);

        layout.getChildren().addAll(title, listView);

        if ("admin".equalsIgnoreCase(user.getRole())) {
            Button addBtn = createStyledButton("Добавить");
            Button deleteBtn = createStyledButton("Удалить");
            Button refreshBtn = createStyledButton("Обновить");

            HBox buttonBox = new HBox(10, addBtn, deleteBtn, refreshBtn);
            buttonBox.setAlignment(Pos.CENTER);

            addBtn.setOnAction(e -> {
                TextInputDialog dialog = new TextInputDialog();
                dialog.setTitle("Добавление группы");
                dialog.setHeaderText("Введите название группы:");
                dialog.showAndWait().ifPresent(name -> {
                    try {
                        controller.addGruppa(name);
                        refreshList();
                    } catch (SQLException ex) {
                        showError("Ошибка при добавлении группы: " + ex.getMessage());
                    }
                });
            });

            deleteBtn.setOnAction(e -> {
                Gruppa selected = listView.getSelectionModel().getSelectedItem();
                if (selected != null) {
                    try {
                        controller.deleteGruppa(selected.getId());
                        refreshList();
                    } catch (SQLException ex) {
                        showError("Ошибка при удалении группы: " + ex.getMessage());
                    }
                } else {
                    showError("Пожалуйста, выберите группу для удаления.");
                }
            });

            refreshBtn.setOnAction(e -> refreshList());

            layout.getChildren().add(buttonBox);
        }

        layout.setStyle("-fx-background-color: #f4f6f8; -fx-border-radius: 10; -fx-background-radius: 10;");

        Scene scene = new Scene(layout, 420, 350);
        stage.setTitle("Группы");
        stage.setScene(scene);
        stage.show();
    }

    private void refreshList() {
        try {
            listView.getItems().clear();
            listView.getItems().addAll(controller.getAllGruppy());
        } catch (SQLException e) {
            showError("Ошибка при загрузке групп: " + e.getMessage());
        }
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Ошибка");
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