package org.example.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import org.example.model.User;

public class MainView {
    private final User user;

    public MainView(User user) {
        this.user = user;
    }

    public void show(Stage stage) {
        Label welcomeLabel = new Label("Добро пожаловать, " + user.getUsername() + " (" + user.getRole() + ")");
        welcomeLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        Button predmetBtn = createStyledButton("Предметы");
        predmetBtn.setOnAction(new PredmetHandler());

        Button gruppaBtn = createStyledButton("Группы");
        gruppaBtn.setOnAction(new GruppaHandler());

        Button prepodBtn = createStyledButton("Преподаватели");
        prepodBtn.setOnAction(new PrepodHandler());

        Button raspredBtn = createStyledButton("Распределённая нагрузка");
        raspredBtn.setOnAction(new RaspredHandler());

        Button planBtn = createStyledButton("Учебный план");
        planBtn.setOnAction(new PlanHandler());

        Button profileBtn = createStyledButton("Профиль");
        profileBtn.setOnAction(new ProfileHandler());

        VBox buttonBox = new VBox(10, predmetBtn, gruppaBtn, prepodBtn, raspredBtn, planBtn, profileBtn);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setPadding(new Insets(10));

        VBox mainBox = new VBox(20, welcomeLabel, buttonBox);
        mainBox.setAlignment(Pos.CENTER);
        mainBox.setPadding(new Insets(30));
        mainBox.setStyle("-fx-background-color: #f0f4f8; -fx-border-color: #ccc; -fx-border-radius: 10; -fx-background-radius: 10;");

        Scene scene = new Scene(new StackPane(mainBox), 400, 400);
        stage.setTitle("Главное меню");
        stage.setScene(scene);
        stage.show();
    }

    private Button createStyledButton(String text) {
        Button button = new Button(text);
        button.setPrefWidth(220);
        button.setStyle(
                "-fx-background-color: #4CAF50;" +
                        "-fx-text-fill: white;" +
                        "-fx-font-size: 14px;" +
                        "-fx-background-radius: 6;"
        );
        return button;
    }

    // Обработчики действий в ООП-стиле

    private class PredmetHandler implements javafx.event.EventHandler<javafx.event.ActionEvent> {
        @Override
        public void handle(javafx.event.ActionEvent event) {
            PredmetView view = new PredmetView(user);
            view.show(new Stage());
        }
    }

    private class GruppaHandler implements javafx.event.EventHandler<javafx.event.ActionEvent> {
        @Override
        public void handle(javafx.event.ActionEvent event) {
            GruppaView view = new GruppaView(user);
            view.show(new Stage());
        }
    }

    private class PrepodHandler implements javafx.event.EventHandler<javafx.event.ActionEvent> {
        @Override
        public void handle(javafx.event.ActionEvent event) {
            PrepodView view = new PrepodView(user);
            view.show(new Stage());
        }
    }

    private class RaspredHandler implements javafx.event.EventHandler<javafx.event.ActionEvent> {
        @Override
        public void handle(javafx.event.ActionEvent event) {
            RaspredNagruzkaView view = new RaspredNagruzkaView(user);
            view.show(new Stage());
        }
    }

    private class PlanHandler implements javafx.event.EventHandler<javafx.event.ActionEvent> {
        @Override
        public void handle(javafx.event.ActionEvent event) {
            UchebniyPlanView view = new UchebniyPlanView(user);
            view.show(new Stage());
        }
    }

    private class ProfileHandler implements javafx.event.EventHandler<javafx.event.ActionEvent> {
        @Override
        public void handle(javafx.event.ActionEvent event) {
            ProfileView view = new ProfileView(user);
            view.show(new Stage());
        }
    }
}
