package com.github.javamoth.moth_calculator;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("hello-view.fxml"));

        Scene scene = new Scene(fxmlLoader.load(), 340, 420);

        //Accessing the buttons in the .fxml file and assigning them to variables
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
        Button buttonPoint = (Button) scene.lookup("#buttonPoint");
        Button buttonNegative = (Button) scene.lookup("#buttonNegative");
        Button buttonBackspace = (Button) scene.lookup("#buttonBackspace");

        //Detecting the keys that are being pressed
        scene.addEventFilter(KeyEvent.KEY_PRESSED, key -> {  //Registering a key press event

                switch (key.getCode()) {                    //Testing the value of the key code

                //If either the zero key on the number row or the numpad zero is pressed
                //Press the "0" button programmatically
                case DIGIT0, NUMPAD0 -> {button0.fire(); key.consume();}
                case DIGIT1, NUMPAD1 -> {button1.fire(); key.consume();}
                case DIGIT2, NUMPAD2 -> {button2.fire(); key.consume();}
                case DIGIT3, NUMPAD3 -> {button3.fire(); key.consume();}
                case DIGIT4, NUMPAD4 -> {button4.fire(); key.consume();}
                case DIGIT5, NUMPAD5 -> {button5.fire(); key.consume();}
                case DIGIT6, NUMPAD6 -> {button6.fire(); key.consume();}
                case DIGIT7, NUMPAD7 -> {button7.fire(); key.consume();}
                case DIGIT9, NUMPAD9 -> {button9.fire(); key.consume();}
                case ADD -> {buttonAdd.fire(); key.consume();}
                case SUBTRACT, MINUS -> {buttonSubtract.fire(); key.consume();}
                case MULTIPLY -> {buttonMultiply.fire(); key.consume();}
                case DIVIDE, BACK_SLASH -> {buttonDivide.fire(); key.consume();}
                case DELETE -> {buttonC.fire(); key.consume();}
                case DECIMAL, PERIOD, F11 -> {buttonPoint.fire(); key.consume();}
                case F10 -> {buttonNegative.fire(); key.consume();}
                case BACK_SPACE, F12 -> {buttonBackspace.fire(); key.consume();}
//              default -> System.out.println(key.getCode()); //Use this for debugging
            }

            //For this number row key we need to trigger an event with both shift pressed
            //and shift released, so I excluded it from the switch statement and wrote two "if" conditions instead
            if (key.isShiftDown() && key.getCode() == KeyCode.EQUALS) {
                buttonAdd.fire();
                key.consume();
            }

            if (!key.isShiftDown() && ((key.getCode() == KeyCode.EQUALS) || (key.getCode() == KeyCode.ENTER))) {
                buttonEquals.fire();
            }

            //Same as above
            if (key.isShiftDown() && key.getCode() == KeyCode.DIGIT8) {
                buttonMultiply.fire();
                key.consume();
            }
            if (!key.isShiftDown() && (key.getCode() == KeyCode.DIGIT8) || (key.getCode() == KeyCode.NUMPAD8)){
                button8.fire();
                key.consume();
            }
        });

        stage.setTitle("Moth Calculator v1.23");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();

        CalculatorController helloController = fxmlLoader.getController();   //Access stuff in CalculatorController.java
        stage.setOnCloseRequest(event -> helloController.executor.shutdown());  //Shut down the executor so the app
        //closes properly
    }

    public static void main(String[] args) {

        launch();
    }
}