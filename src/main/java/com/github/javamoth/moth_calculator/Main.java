package com.github.javamoth.moth_calculator;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    //Maximum width of the text
    private final double MAX_TEXT_WIDTH = 300;

    //Default (non-scaled) font size of the text
    private final double displayFontSize = 27;
    private final Font displayFont = Font.font(displayFontSize);

    private final double miniDisplayFontSize = 15;
    private final Font miniDisplayFont = Font.font(miniDisplayFontSize);

    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("hello-view.fxml"));

        Scene scene = new Scene(fxmlLoader.load(), 340, 425);

        //Accessing both displays to later reduce font size in them
        Label display = (Label) scene.lookup("#display");
        Label miniDisplay = (Label) scene.lookup("#displayMini");

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
        Button buttonRepeatSwitch = (Button) scene.lookup("#buttonRepeatSwitch");

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
                case R -> {buttonRepeatSwitch.fire(); key.consume();}
                case DECIMAL, PERIOD, F11, SLASH -> {buttonPoint.fire(); key.consume();}    //SLASH is for RU period
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

        //Dynamically reduce the font size when the numbers get longer, main display
        display.setFont(displayFont);
        display.textProperty().addListener((observable, oldValue, newValue) -> {
            //create temp Text object with the same text as the label
            //and measure its width using default label font size
            Text tmpText = new Text(newValue);
            tmpText.setFont(displayFont);

            double textWidth = tmpText.getLayoutBounds().getWidth();

            //check if text width is smaller than maximum width allowed
            if (textWidth <= MAX_TEXT_WIDTH) {
                display.setFont(displayFont);
            } else {
                //and if it isn't, calculate new font size,
                // so that label text width matches MAX_TEXT_WIDTH
                double newFontSize = displayFontSize * MAX_TEXT_WIDTH / textWidth;
                display.setFont(Font.font(displayFont.getFamily(), newFontSize));
            }

        });

        //Dynamically reduce the font size when the numbers get longer, mini display
        miniDisplay.setFont(miniDisplayFont);
        miniDisplay.textProperty().addListener((observable, oldValue, newValue) -> {
            //create temp Text object with the same text as the label
            //and measure its width using default label font size
            Text tmpText = new Text(newValue);
            tmpText.setFont(miniDisplayFont);

            double textWidth = tmpText.getLayoutBounds().getWidth();

            //check if text width is smaller than maximum width allowed
            if (textWidth <= MAX_TEXT_WIDTH) {
                miniDisplay.setFont(miniDisplayFont);
            } else {
                //and if it isn't, calculate new font size,
                // so that label text width matches MAX_TEXT_WIDTH
                double newFontSize = miniDisplayFontSize * MAX_TEXT_WIDTH / textWidth;
                miniDisplay.setFont(Font.font(miniDisplayFont.getFamily(), newFontSize));
            }

        });

        stage.setTitle("Moth Calculator v1.30");
        stage.setScene(scene);
        stage.setResizable(false);  //Prohibit resizing the window as the sizes of all elements are hard-coded
        stage.show();

        CalculatorController calculatorController = fxmlLoader.getController();   //Access stuff in CalculatorController.java
        stage.setOnCloseRequest(event -> calculatorController.executor.shutdown());  //Shut down the executor so the app
        //closes properly
    }

    public static void main(String[] args) {

        launch();
    }
}