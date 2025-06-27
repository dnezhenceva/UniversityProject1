package org.example.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import org.example.Controller.UchebniyPlanController;
import org.example.model.UchebniyPlan;
import org.example.model.User;

import java.sql.SQLException;
import java.util.List;

public class UchebniyPlanView {

    private final User user;
    private final UchebniyPlanController controller;
    private final ListView<UchebniyPlan> listView;

    private TextField predmetIdField;
    private TextField semestrField;
    private CheckBox obyazatelniyCheck;

    public UchebniyPlanView(User user) {
        this.user = user;
        this.controller = new UchebniyPlanController();
        this.listView = new ListView<>();
    }

    public void show(Stage stage) {
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(15));
        layout.setAlignment(Pos.TOP_CENTER);

        Label title = new Label("Учебный план");
        title.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        layout.getChildren().addAll(title, listView);
        refreshList();

        if ("admin".equalsIgnoreCase(user.getRole())) {
            createAdminControls(layout);
        }

        Scene scene = new Scene(layout, 500, 500);
        stage.setTitle("Учебный план");
        stage.setScene(scene);
        stage.show();
    }

    private void createAdminControls(VBox layout) {
        predmetIdField = new TextField();
        predmetIdField.setPromptText("ID предмета");

        semestrField = new TextField();
        semestrField.setPromptText("Семестр");

        obyazatelniyCheck = new CheckBox("Обязательный");

        Button addBtn = new Button("Добавить");
        Button deleteBtn = new Button("Удалить выбранное");

        addBtn.setOnAction(e -> handleAdd());
        deleteBtn.setOnAction(e -> handleDelete());

        HBox inputBox = new HBox(10, predmetIdField, semestrField, obyazatelniyCheck, addBtn, deleteBtn);
        inputBox.setAlignment(Pos.CENTER);
        inputBox.setPadding(new Insets(10, 0, 0, 0));

        layout.getChildren().add(inputBox);
    }

    private void handleAdd() {
        try {
            int predmetId = Integer.parseInt(predmetIdField.getText().trim());
            int semestr = Integer.parseInt(semestrField.getText().trim());
            boolean obyazatelniy = obyazatelniyCheck.isSelected();

            controller.addPlan(predmetId, semestr, obyazatelniy);
            refreshList();

            predmetIdField.clear();
            semestrField.clear();
            obyazatelniyCheck.setSelected(false);
        } catch (NumberFormatException e) {
            showAlert("Ошибка", "ID предмета и семестр должны быть целыми числами.");
        } catch (SQLException e) {
            showAlert("Ошибка", "Ошибка при добавлении плана: " + e.getMessage());
        }
    }

    private void handleDelete() {
        UchebniyPlan selected = listView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            try {
                controller.deletePlan(selected.getId());
                refreshList();
            } catch (SQLException e) {
                showAlert("Ошибка", "Ошибка при удалении: " + e.getMessage());
            }
        } else {
            showAlert("Ошибка", "Выберите строку для удаления.");
        }
    }

    private void refreshList() {
        try {
            List<UchebniyPlan> plans = controller.getAllPlans();
            listView.getItems().setAll(plans);
        } catch (SQLException e) {
            showAlert("Ошибка", "Ошибка при загрузке учебного плана: " + e.getMessage());
        }
    }

    private void showAlert(String title, String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
