package com.example.demo1;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {


    class WindowButtons extends HBox {

        public WindowButtons() {
            Button closeBtn = new Button("X");

            closeBtn.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent actionEvent) {
                    Platform.exit();
                }
            });

            this.getChildren().add(closeBtn);
        }
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));



        Scene scene = new Scene(fxmlLoader.load(), 347, 351);


        Button button0 = (Button) scene.lookup("#button0");
        Button button1 = (Button) scene.lookup("#button1");
        Button button2 = (Button) scene.lookup("#button2");
        Button button3 = (Button) scene.lookup("#button3");
        Button button4 = (Button) scene.lookup("#button4");
        Button button5 = (Button) scene.lookup("#button5");
        Button button6 = (Button) scene.lookup("#button6");
        Button button7 = (Button) scene.lookup("#button7");
        Button button8 = (Button) scene.lookup("#button8");
        Button button9 = (Button) scene.lookup("#button9");
        Button buttonAdd = (Button) scene.lookup("#buttonAdd");
        Button buttonSubtract = (Button) scene.lookup("#buttonSubtract");
        Button buttonMultiply = (Button) scene.lookup("#buttonMultiply");
        Button buttonDivide = (Button) scene.lookup("#buttonDivide");
        Button buttonEquals = (Button) scene.lookup("#buttonEquals");
        Button buttonC = (Button) scene.lookup("#buttonC");
        scene.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            public void handle(KeyEvent key) {
                switch (key.getCode()) {
                    case DIGIT0, NUMPAD0 -> {
                        button0.fire();
                        key.consume();
                    }
                    case DIGIT1, NUMPAD1 -> {
                        button1.fire();
                        key.consume();
                    }
                    case DIGIT2, NUMPAD2 -> {
                        button2.fire();
                        key.consume();
                    }
                    case DIGIT3, NUMPAD3 -> {
                        button3.fire();
                        key.consume();
                    }
                    case DIGIT4, NUMPAD4 -> {
                        button4.fire();
                        key.consume();
                    }
                    case DIGIT5, NUMPAD5 -> {
                        button5.fire();
                        key.consume();
                    }
                    case DIGIT6, NUMPAD6 -> {
                        button6.fire();
                        key.consume();
                    }
                    case DIGIT7, NUMPAD7 -> {
                        button7.fire();
                        key.consume();
                    }
//                    case DIGIT8, NUMPAD8 -> {
//                        button8.fire();
//                        key.consume();
//                    }
                    case DIGIT9, NUMPAD9 -> {
                        button9.fire();
                        key.consume();
                    }
                    case ADD -> {
                        buttonAdd.fire();
                        key.consume();
                    }
                    case SUBTRACT, MINUS -> {
                        buttonSubtract.fire();
                        key.consume();
                    }
                    case MULTIPLY -> {
                        buttonMultiply.fire();
                        key.consume();
                    }
                    case DIVIDE, BACK_SLASH -> {
                        buttonDivide.fire();
                        key.consume();
                    }
                    case EQUALS, ENTER -> {
                        buttonEquals.fire();
                        key.consume();
                    }
                    case DELETE -> {
                        buttonC.fire();
                        key.consume();
                    }
//                    default -> System.out.println(key.getCode());
                }


                if (key.isShiftDown() && key.getCode() == KeyCode.EQUALS) {
                    buttonAdd.fire();
                    key.consume();
                }
                if (key.isShiftDown() && key.getCode() == KeyCode.DIGIT8) {
                    buttonMultiply.fire();
                    key.consume();
                }
                if (!key.isShiftDown() && (key.getCode() == KeyCode.DIGIT8) || (key.getCode() == KeyCode.NUMPAD8)){
                    button8.fire();
                    key.consume();
                }



            }
        });




        stage.setTitle("Calculator v1.12");
        stage.setScene(scene);

        stage.setResizable(false);
        stage.show();






    }

    public static void main(String[] args) {

        launch();
    }
}