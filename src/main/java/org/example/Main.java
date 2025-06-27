package org.example;

import javafx.application.Application;
import javafx.stage.Stage;
import org.example.view.LoginView;

import java.sql.SQLException;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws SQLException {
        LoginView loginView = new LoginView();
        loginView.show(primaryStage);
    }
}
