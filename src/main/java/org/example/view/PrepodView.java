package org.example.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import org.example.Controller.PrepodavatelController;
import org.example.model.Prepodavatel;
import org.example.model.User;

import java.sql.SQLException;
import java.util.List;

public class PrepodView {

    private final PrepodavatelController controller;
    private final User user;

    private ListView<Prepodavatel> listView;
    private TextField familiyaField;
    private TextField imyaField;
    private TextField otchestvoField;
    private TextField stepField;
    private TextField dolzhnostField;
    private TextField stazhField;

    public PrepodView(User user) {
        this.user = user;
        this.controller = new PrepodavatelController(this);
    }

    public void show(Stage stage) {
        Label title = new Label("Список преподавателей");
        title.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        listView = new ListView<>();
        listView.setPrefHeight(200);
        listView.setStyle("-fx-font-size: 14px;");
        refreshList();

        VBox layout = new VBox(15);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.CENTER);
        layout.getChildren().addAll(title, listView);

        Button nagruzkaBtn = createStyledButton("Узнать нагрузку");
        nagruzkaBtn.setOnAction(new NagruzkaButtonHandler());
        layout.getChildren().add(nagruzkaBtn);

        if ("admin".equalsIgnoreCase(user.getRole())) {
            familiyaField = new TextField();
            familiyaField.setPromptText("Фамилия");

            imyaField = new TextField();
            imyaField.setPromptText("Имя");

            otchestvoField = new TextField();
            otchestvoField.setPromptText("Отчество");

            stepField = new TextField();
            stepField.setPromptText("Учёная степень");

            dolzhnostField = new TextField();
            dolzhnostField.setPromptText("Должность");

            stazhField = new TextField();
            stazhField.setPromptText("Стаж (лет)");

            Button addBtn = createStyledButton("Добавить");
            Button deleteBtn = createStyledButton("Удалить");
            Button refreshBtn = createStyledButton("Обновить");

            addBtn.setOnAction(new AddButtonHandler());
            deleteBtn.setOnAction(new DeleteButtonHandler());
            refreshBtn.setOnAction(new RefreshButtonHandler());

            VBox formBox = new VBox(10,
                    familiyaField, imyaField, otchestvoField,
                    stepField, dolzhnostField, stazhField);
            formBox.setAlignment(Pos.CENTER_LEFT);

            HBox buttonsBox = new HBox(10, addBtn, deleteBtn, refreshBtn);
            buttonsBox.setAlignment(Pos.CENTER);

            layout.getChildren().addAll(formBox, buttonsBox);
        }

        layout.setStyle("-fx-background-color: #f8f9fa; -fx-border-radius: 10; -fx-background-radius: 10;");

        Scene scene = new Scene(layout, 500, 600);
        stage.setTitle("Преподаватели");
        stage.setScene(scene);
        stage.show();
    }

    private void refreshList() {
        try {
            listView.getItems().setAll(controller.getAllPrepodavateli());
        } catch (SQLException e) {
            showAlert("Ошибка", "Ошибка при загрузке преподавателей: " + e.getMessage());
        }
    }

    private class NagruzkaButtonHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            Prepodavatel selected = listView.getSelectionModel().getSelectedItem();
            if (selected != null) {
                try {
                    List<String> nagruzka = controller.getNagruzkaForPrepod(selected.getId());
                    if (nagruzka.isEmpty()) {
                        showAlert("Нагрузка", "Для преподавателя нет записей о нагрузке.");
                    } else {
                        String title = String.format("Нагрузка для %s %s %s", selected.getFamiliya(), selected.getImya(), selected.getOtchestvo());
                        showAlert(title, String.join("\n", nagruzka));
                    }
                } catch (SQLException ex) {
                    showAlert("Ошибка", "Не удалось получить нагрузку: " + ex.getMessage());
                }
            } else {
                showAlert("Ошибка", "Сначала выберите преподавателя.");
            }
        }
    }

    private class AddButtonHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            String familiya = familiyaField.getText();
            String imya = imyaField.getText();
            String otchestvo = otchestvoField.getText();
            String step = stepField.getText();
            String dolzhnost = dolzhnostField.getText();
            String stazhStr = stazhField.getText();

            if (familiya.isEmpty() || imya.isEmpty() || otchestvo.isEmpty() ||
                    step.isEmpty() || dolzhnost.isEmpty() || stazhStr.isEmpty()) {
                showAlert("Ошибка", "Пожалуйста, заполните все поля.");
                return;
            }

            try {
                int stazh = Integer.parseInt(stazhStr);
                controller.addPrepodavatel(familiya, imya, otchestvo, dolzhnost, step, stazh);
                refreshList();

                familiyaField.clear();
                imyaField.clear();
                otchestvoField.clear();
                stepField.clear();
                dolzhnostField.clear();
                stazhField.clear();
            } catch (NumberFormatException ex) {
                showAlert("Ошибка", "Стаж должен быть числом.");
            } catch (SQLException ex) {
                showAlert("Ошибка", "Ошибка при добавлении преподавателя: " + ex.getMessage());
            }
        }
    }

    private class DeleteButtonHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            Prepodavatel selected = listView.getSelectionModel().getSelectedItem();
            if (selected != null) {
                try {
                    controller.deletePrepodavatel(selected.getId());
                    refreshList();
                } catch (SQLException ex) {
                    showAlert("Ошибка", "Ошибка при удалении преподавателя: " + ex.getMessage());
                }
            } else {
                showAlert("Ошибка", "Сначала выберите преподавателя.");
            }
        }
    }

    private class RefreshButtonHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            refreshList();
        }
    }

    private Button createStyledButton(String text) {
        Button btn = new Button(text);
        btn.setPrefWidth(130);
        btn.setStyle(
                "-fx-background-color: #3f51b5;" +
                        "-fx-text-fill: white;" +
                        "-fx-font-weight: bold;" +
                        "-fx-background-radius: 5;" +
                        "-fx-font-size: 13px;");
        return btn;
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}