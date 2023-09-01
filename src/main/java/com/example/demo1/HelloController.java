package com.example.demo1;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.text.DecimalFormat;
import java.util.Objects;

public class HelloController {

    //Defining the variables
    @FXML
    private Label display;
//    @FXML
//    private Button button0, button1, button2, button3, button4, button5,
//            button6, button7, button8, button9, buttonAdd, buttonSubtract,
//            buttonDivide, buttonMultiply, buttonEquals, buttonC;

    String flag;
    String num1;
    Boolean afterTheOperator;
    Boolean equals = false;


    //Event handler methods
    @FXML
    protected void onButton1Click() {


        if (Double.parseDouble(display.getText()) == 0 || afterTheOperator || equals) {
            display.setText("1");
            afterTheOperator = false;

        }
        else {
            String num = display.getText();
            num = num + "1";
            display.setText(num);
        }
    }

    @FXML
    protected void onButton2Click() {
        if (Double.parseDouble(display.getText()) == 0 || afterTheOperator || equals) {
            display.setText("2");
            afterTheOperator = false;
            equals = false;
        }
        else {
            String num = display.getText();
            num = num + "2";
            display.setText(num);
        }
    }

    @FXML
    protected void onButton3Click() {
        if (Double.parseDouble(display.getText()) == 0 || afterTheOperator || equals) {
            display.setText("3");
            afterTheOperator = false;
            equals = false;
        }
        else {
            String num = display.getText();
            num = num + "3";
            display.setText(num);
        }
    }

    @FXML
    protected void onButton4Click() {
        if (Double.parseDouble(display.getText()) == 0 || afterTheOperator || equals) {
            display.setText("4");
            afterTheOperator = false;
            equals = false;
        }
        else {
            String num = display.getText();
            num = num + "4";
            display.setText(num);
        }
    }

    @FXML
    protected void onButton5Click() {
        if (Double.parseDouble(display.getText()) == 0 || afterTheOperator || equals) {
            display.setText("5");
            afterTheOperator = false;
            equals = false;
        }
        else {
            String num = display.getText();
            num = num + "5";
            display.setText(num);
        }
    }

    @FXML
    protected void onButton6Click() {
        if (Double.parseDouble(display.getText()) == 0 || afterTheOperator || equals) {
            display.setText("6");
            afterTheOperator = false;
            equals = false;
        }
        else {
            String num = display.getText();
            num = num + "6";
            display.setText(num);
        }
    }

    @FXML
    protected void onButton7Click() {
        if (Double.parseDouble(display.getText()) == 0 || afterTheOperator || equals) {
            display.setText("7");
            afterTheOperator = false;
            equals = false;
        }
        else {
            String num = display.getText();
            num = num + "7";
            display.setText(num);
        }
    }

    @FXML
    protected void onButton8Click() {
        if (Double.parseDouble(display.getText()) == 0 || afterTheOperator || equals) {
            display.setText("8");
            afterTheOperator = false;
            equals = false;
        }
        else {
            String num = display.getText();
            num = num + "8";
            display.setText(num);
        }
    }

    @FXML
    protected void onButton9Click() {
        if (Double.parseDouble(display.getText()) == 0 || afterTheOperator || equals) {
            display.setText("9");
            afterTheOperator = false;
            equals = false;
        }
        else {
            String num = display.getText();
            num = num + "9";
            display.setText(num);
        }
    }

    @FXML
    protected void onButton0Click() {
        if (Double.parseDouble(display.getText()) == 0 || afterTheOperator || equals) {
            display.setText("0");
            afterTheOperator = false;
            equals = false;
        }
        else {
            String num = display.getText();
            num = num + "0";
            display.setText(num);
        }
    }

    //This clears the display area
    @FXML
    protected void onButtonCClick() {
        display.setText("0");
        flag = "";
        afterTheOperator = false;
        equals = false;
    }

    public void onButtonAddClick(){
        num1 = display.getText();
//        display.setText("0");
        flag = "+";
        afterTheOperator = true;


    }

    public void onButtonSubtractClick(){
        num1 = display.getText();
//        display.setText("0");
        flag = "-";
        afterTheOperator = true;

    }

    public void onButtonMultiplyClick(){
        num1 = display.getText();
//        display.setText("0");
        flag = "*";
        afterTheOperator = true;

    }

    public void onButtonDivideClick(){
        num1 = display.getText();
//        display.setText("0");
        flag = "/";
        afterTheOperator = true;

    }

    public void onButtonEqualsClick() {
        if (Objects.equals(flag, "+")) {
            String num2 = display.getText();
            double result = Double.parseDouble(num1) + Double.parseDouble(num2);
            String stringRes = Double.toString(result);
            DecimalFormat format = new DecimalFormat("0.###########");
            display.setText(format.format(Double.valueOf(stringRes)));
            flag = "";
            equals = true;
        }

        if (Objects.equals(flag, "-")) {
            String num2 = display.getText();
            double result = Double.parseDouble(num1) - Double.parseDouble(num2);
            String stringRes = Double.toString(result);
            DecimalFormat format = new DecimalFormat("0.###########");
            display.setText(format.format(Double.valueOf(stringRes)));
            flag = "";
            equals = true;
        }

        if (Objects.equals(flag, "*")) {
            String num2 = display.getText();
            double result = Double.parseDouble(num1) * Double.parseDouble(num2);
            String stringRes = Double.toString(result);
            DecimalFormat format = new DecimalFormat("0.###########");
            display.setText(format.format(Double.valueOf(stringRes)));
            flag = "";
            equals = true;
        }

        if (Objects.equals(flag, "/")) {
            String num2 = display.getText();
            double result = Double.parseDouble(num1) / Double.parseDouble(num2);
            String stringRes = Double.toString(result);
            DecimalFormat format = new DecimalFormat("0.###########");
            display.setText(format.format(Double.valueOf(stringRes)));
            flag = "";
            equals = true;
        }
    }




}