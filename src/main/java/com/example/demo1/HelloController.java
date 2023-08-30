package com.example.demo1;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class HelloController {
    @FXML
    private Label display;
    @FXML
    private Button button0, button1, button2, button3, button4, button5,
            button6, button7, button8, button9, buttonAdd, buttonSubtract,
            buttonDivide, buttonMultiply, buttonEquals, buttonC;

    @FXML
    protected void onHelloButtonClick() {
        display.setText("Welcome to JavaFX Application!");
        button0.setText("!!!");
    }
}