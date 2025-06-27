package org.example.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import org.example.Controller.RaspredNagruzkaController;
import org.example.model.User;
import org.example.model.RaspredNagruzka;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class RaspredNagruzkaView {
    private final RaspredNagruzkaController controller;
    private final User user;
    private Stage stage;

    private ListView<RaspredNagruzka> listView;
    private TextField prepodIdField;
    private TextField predmetyField;
    private TextField gruppyField;
    private TextField vidZanyatiyaField;
    private TextField nagruzkaField;
    private TextField kolichestvoField;

    public RaspredNagruzkaView(User user) {
        this.user = user;
        this.controller = new RaspredNagruzkaController(this);
    }

    public void show(Stage stage) {
        this.stage = stage;
        setupUI();
    }

    private void setupUI() {
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #f5f5f5;");

        HBox headerBox = createHeaderBox();
        root.setTop(headerBox);

        VBox centerBox = createCenterBox();
        root.setCenter(centerBox);

        if ("admin".equalsIgnoreCase(user.getRole())) {
            GridPane controlPanel = createControlPanel();
            root.setBottom(controlPanel);
        }

        Scene scene = new Scene(root, 600, 600);
        stage.setTitle("Распределенная нагрузка");
        stage.setScene(scene);
        stage.show();
    }

    private HBox createHeaderBox() {
        Label title = new Label("Распределенная нагрузка");
        title.setFont(Font.font("System", FontWeight.BOLD, 20));
        title.setTextFill(Color.web("#2E7D32"));

        HBox headerBox = new HBox(title);
        headerBox.setAlignment(Pos.CENTER);
        headerBox.setPadding(new Insets(15));
        headerBox.setStyle("-fx-background-color: white; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 5, 0, 0, 0);");
        return headerBox;
    }

    private VBox createCenterBox() {
        listView = new ListView<>();
        listView.setStyle("-fx-background-color: white; -fx-border-color: #e0e0e0; -fx-border-radius: 5;");
        listView.setPadding(new Insets(10));

        loadZanyatia();

        VBox centerBox = new VBox(listView);
        centerBox.setPadding(new Insets(15));
        return centerBox;
    }

    private GridPane createControlPanel() {
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(15));
        grid.setStyle("-fx-background-color: white; -fx-border-color: #e0e0e0; -fx-border-radius: 5;");

        prepodIdField = createStyledTextField("ID преподавателя");
        predmetyField = createStyledTextField("ID предметов через запятую");
        gruppyField = createStyledTextField("ID групп через запятую");
        vidZanyatiyaField = createStyledTextField("Вид занятия");
        nagruzkaField = createStyledTextField("Нагрузка (в часах)");
        kolichestvoField = createStyledTextField("Количество занятий");

        Button addBtn = createStyledButton("Добавить");
        Button deleteBtn = createStyledButton("Удалить");
        Button refreshBtn = createStyledButton("Обновить");

        addBtn.setOnAction(e -> handleAddZanyatie());
        deleteBtn.setOnAction(e -> handleDeleteZanyatie());
        refreshBtn.setOnAction(e -> loadZanyatia());

        grid.addRow(0, new Label("ID преподавателя:"), prepodIdField);
        grid.addRow(1, new Label("ID предметов:"), predmetyField);
        grid.addRow(2, new Label("ID групп:"), gruppyField);
        grid.addRow(3, new Label("Вид занятия:"), vidZanyatiyaField);
        grid.addRow(4, new Label("Нагрузка (часы):"), nagruzkaField);
        grid.addRow(5, new Label("Количество:"), kolichestvoField);

        HBox buttonsBox = new HBox(10, addBtn, deleteBtn, refreshBtn);
        buttonsBox.setAlignment(Pos.CENTER);
        grid.add(buttonsBox, 0, 6, 2, 1);

        return grid;
    }

    private void loadZanyatia() {
        try {
            var list = controller.getAllZanyatia();
            listView.getItems().setAll(list);
            System.out.println("Загружено занятий: " + list.size());
        } catch (SQLException e) {
            showAlert("Ошибка", "Ошибка при загрузке распределенной нагрузки: " + e.getMessage());
        }
    }



    private void handleAddZanyatie() {
        try {
            String prepodText = prepodIdField.getText().trim();
            String chasyText = nagruzkaField.getText().trim();
            String kolText = kolichestvoField.getText().trim();
            String vid = vidZanyatiyaField.getText().trim();
            String predmetText = predmetyField.getText().trim();
            String gruppaText = gruppyField.getText().trim();

            if (prepodText.isEmpty() || chasyText.isEmpty() || kolText.isEmpty()
                    || vid.isEmpty() || predmetText.isEmpty() || gruppaText.isEmpty()) {
                showAlert("Ошибка", "Пожалуйста, заполните все поля.");
                return;
            }

            int prepodId = Integer.parseInt(prepodText);
            int chasy = Integer.parseInt(chasyText);
            int kolichestvo = Integer.parseInt(kolText);

            var predmetIds = Arrays.stream(predmetText.split(","))
                    .map(String::trim)
                    .map(Integer::parseInt)
                    .collect(Collectors.toList());

            var gruppaIds = Arrays.stream(gruppaText.split(","))
                    .map(String::trim)
                    .map(Integer::parseInt)
                    .collect(Collectors.toList());

            controller.addZanyatie(prepodId, vid, chasy, kolichestvo,
                    new ArrayList<>(predmetIds), gruppaIds);

            loadZanyatia();
            clearFields();

        } catch (NumberFormatException ex) {
            showAlert("Ошибка", "ID, часы и количество занятий, а также ID предметов и групп должны быть целыми числами.");
        } catch (SQLException ex) {
            showAlert("Ошибка", "Ошибка при добавлении нагрузки: " + ex.getMessage());
        }
    }



    private void handleDeleteZanyatie() {
        RaspredNagruzka selected = listView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            try {
                controller.deleteZanyatie(selected.getId());
                loadZanyatia();
            } catch (SQLException ex) {
                showAlert("Ошибка", "Ошибка при удалении нагрузки: " + ex.getMessage());
            }
        } else {
            showAlert("Ошибка", "Сначала выберите нагрузку.");
        }
    }

    private void clearFields() {
        prepodIdField.clear();
        predmetyField.clear();
        gruppyField.clear();
        vidZanyatiyaField.clear();
        nagruzkaField.clear();
        kolichestvoField.clear();
    }

    private TextField createStyledTextField(String prompt) {
        TextField textField = new TextField();
        textField.setPromptText(prompt);
        textField.setStyle("-fx-background-color: white; -fx-border-color: #e0e0e0; " +
                "-fx-border-radius: 5; -fx-padding: 8; -fx-font-size: 14px;");
        textField.setMaxWidth(200);
        return textField;
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

    private void showAlert(String title, String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
