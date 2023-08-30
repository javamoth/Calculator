package com.example.demo1;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
//    public static boolean equals;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));

        Scene scene = new Scene(fxmlLoader.load(), 347, 351);
//        scene.getStylesheets().add(getClass().getResource("calc.css").toExternalForm());

//        BooleanProperty equals = new SimpleBooleanProperty(false);
//
//        // Add change listener
//        equals.addListener((observable, oldValue, newValue) -> myFunc());

        stage.setTitle("Calculator");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();


    }

    public static void main(String[] args) {
        launch();
    }
}