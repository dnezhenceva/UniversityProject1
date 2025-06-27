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
import org.example.Controller.LoginController;
import org.example.model.User;

import java.sql.SQLException;

public class LoginView {
    private final LoginController controller;
    private Stage stage;
    private Label messageLabel;
    private TextField usernameField;
    private PasswordField passwordField;

    public LoginView() throws SQLException {
        this.controller = new LoginController();
    }

    public void show(Stage stage) {
        this.stage = stage;
        setupUI();
    }

    private void setupUI() {
        // Создаем и настраиваем основные компоненты
        StackPane root = createRootPane();
        VBox formBox = createFormBox();

        // Добавляем компоненты в форму
        formBox.getChildren().addAll(
                createTitleLabel(),
                createFieldsBox(),
                createMessageLabel(),
                createButtonsBox()
        );

        root.getChildren().add(formBox);
        setupScene(root);
    }

    private StackPane createRootPane() {
        StackPane root = new StackPane();
        root.setStyle("-fx-background-color: #E8F5E9;");
        return root;
    }

    private VBox createFormBox() {
        VBox formBox = new VBox(20);
        formBox.setAlignment(Pos.CENTER);
        formBox.setPadding(new Insets(40, 50, 50, 50));
        formBox.setStyle("-fx-background-color: white; -fx-background-radius: 10; " +
                "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 10, 0, 0, 0);");
        formBox.setMaxWidth(400);
        return formBox;
    }

    private Label createTitleLabel() {
        Label titleLabel = new Label("Вход в систему");
        titleLabel.setFont(Font.font("System", FontWeight.BOLD, 24));
        titleLabel.setTextFill(Color.web("#2E7D32"));
        return titleLabel;
    }

    private VBox createFieldsBox() {
        VBox fieldsBox = new VBox(15);
        fieldsBox.setAlignment(Pos.CENTER);

        usernameField = createStyledTextField("Введите логин");
        passwordField = createStyledPasswordField("Введите пароль");

        fieldsBox.getChildren().addAll(
                createFieldLabel("Имя пользователя:"),
                usernameField,
                createFieldLabel("Пароль:"),
                passwordField
        );
        return fieldsBox;
    }

    private Label createMessageLabel() {
        messageLabel = new Label();
        messageLabel.setStyle("-fx-text-fill: #2E7D32;");
        return messageLabel;
    }

    private HBox createButtonsBox() {
        HBox buttonsBox = new HBox(15);
        buttonsBox.setAlignment(Pos.CENTER);

        Button loginButton = createPrimaryButton("Войти");
        Button registerButton = createSecondaryButton("Регистрация");

        setupButtonActions(loginButton, registerButton);

        buttonsBox.getChildren().addAll(loginButton, registerButton);
        return buttonsBox;
    }

    private void setupButtonActions(Button loginButton, Button registerButton) {
        loginButton.setOnAction(e -> handleLogin());
        registerButton.setOnAction(e -> handleRegistration());
    }

    private void handleLogin() {
        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();

        if (username.isEmpty() || password.isEmpty()) {
            messageLabel.setText("Заполните все поля.");
            return;
        }

        try {
            User user = controller.login(username, password);
            if (user != null) {
                messageLabel.setText("Успешный вход! Роль: " + user.getRole());
                new MainView(user).show(new Stage());
                stage.close();
            } else {
                messageLabel.setText("Неверный логин или пароль.");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            messageLabel.setText("Ошибка входа.");
        }
    }

    private void handleRegistration() {
        new RegisterView().show(new Stage());
    }

    private void setupScene(StackPane root) {
        Scene scene = new Scene(root, 500, 500);
        stage.setTitle("Авторизация");
        stage.setScene(scene);
        stage.show();
    }

    private Label createFieldLabel(String text) {
        Label label = new Label(text);
        label.setStyle("-fx-font-size: 14px; -fx-text-fill: #424242;");
        return label;
    }

    private TextField createStyledTextField(String prompt) {
        TextField textField = new TextField();
        textField.setPromptText(prompt);
        textField.setStyle("-fx-background-color: #f5f5f5; -fx-background-radius: 5; " +
                "-fx-border-radius: 5; -fx-padding: 8; -fx-font-size: 14px;");
        textField.setMaxWidth(250);
        textField.setMinHeight(35);
        return textField;
    }

    private PasswordField createStyledPasswordField(String prompt) {
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText(prompt);
        passwordField.setStyle("-fx-background-color: #f5f5f5; -fx-background-radius: 5; " +
                "-fx-border-radius: 5; -fx-padding: 8; -fx-font-size: 14px;");
        passwordField.setMaxWidth(250);
        passwordField.setMinHeight(35);
        return passwordField;
    }

    private Button createPrimaryButton(String text) {
        Button button = new Button(text);
        button.setStyle("-fx-background-color: #2E7D32; -fx-text-fill: white; " +
                "-fx-font-weight: bold; -fx-background-radius: 5; -fx-padding: 10 20;");
        button.setMinWidth(120);
        return button;
    }

    private Button createSecondaryButton(String text) {
        Button button = new Button(text);
        button.setStyle("-fx-background-color: transparent; -fx-text-fill: #2E7D32; " +
                "-fx-border-color: #2E7D32; -fx-border-radius: 5; -fx-border-width: 2; " +
                "-fx-padding: 8 20;");
        button.setMinWidth(120);
        return button;
    }
}