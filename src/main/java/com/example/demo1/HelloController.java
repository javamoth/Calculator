package com.example.demo1;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.text.DecimalFormat;
import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class HelloController {

    ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor(); //This service will be used to
    //briefly highlight and unhighlight buttons after pressing/clicking them as it's hard to achieve in CSS
    //We then shut it down in the main class on app exit

    //Defining global variables

//    BooChangeListener mOnChange = null;
    @FXML
    private Label display;

    //Defining button variables to use them in the highlighting on press method etc.
    @FXML
    Button buttonNegative, buttonPoint, buttonBackspace, button7, button8, button9, buttonDivide,
            button4, button5, button6, buttonMultiply, button1, button2, button3, buttonSubtract,
            buttonC, buttonEquals, button0, buttonAdd;

    String flag; //Stores the operator: +, -, *, /

    String num1; //Stores the 1st operand

    String num2; //Stores the 2nd operand

    String numIterative = ""; //Stores the latest operand to be used as the 2nd operand during iterative calculation
                                //Used in a couple of unique checks

    Boolean afterTheOperator = false; //This flag lets the Onclick functions of the number buttons know that
                                //an operator button has been pressed, and they need to replace the displayed number
                                //entirely instead of appending digits to it.

    Boolean equals = false;         //This flag lets the Onclick functions of the number buttons know that the "=" button
                            //has been pressed, and they need to replace the displayed number
                            //entirely instead of appending digits to it.

    Boolean willHighlight = true;   //Lets the switchBackspaceButton method know if it should highlight or unhighlight
                                    //the BP button when it's clicked



    //onClick methods

    @FXML
    private void onDigitPress(ActionEvent event) {  //Grab the passed onAction event from a digit button

        Button button = (Button) event.getTarget(); //Get the Button object from the event and store it in the "button"
                                                    //variable

        highlightOnKeyPress(button);    //Pass the current button to highlightOnKeyPress so that it lights up when fired

        //Checks if the "0" without the f. point is being displayed or either an operator button or "=" being previously pressed
        if ((Double.parseDouble(display.getText()) == 0 && !display.getText().contains(".")) || afterTheOperator || equals) {

            display.setText(button.getText());  //Resets the displayed number to the button's digit
            afterTheOperator = false;   //Sets the operator flag to "false" after the 1st press to allow for
            // appending digits
            equals = false;     //Sets the "=" flag to "false" after the 1st press to allow for appending digits
//            setOnBooChangeListener(mOnChange);
            switchBackspaceButton();    //This method call should be everywhere after the equals flag changes, this
            //lets us either make the BP button appear active or inactive at appropriate times
        }

        else {

            String num = display.getText(); //Gets the number that's already on the display
            num = num + button.getText();   //Appends its value to it
            display.setText(num);   //Displays the resulting number
        }
    }

    @FXML
    protected void onButtonCClick() {   //Clears the display, resets all flags, empties all variables

        highlightOnKeyPress(buttonC);

        display.setText("0");
        flag = "";
        afterTheOperator = false;
        equals = false;
        numIterative = "";

        switchBackspaceButton();
//        setOnBooChangeListener(mOnChange);
//        System.out.println(mOnChange);
    }

    public void onButtonAddClick() {

        highlightOnKeyPress(buttonAdd);

        //Just in case the user has backspaced to "-0." after entering the 1st operand we perform the steps below

        DecimalFormat format = new DecimalFormat("0.###########"); //Presets the format

        String pointAndZeroCleared = format.format(Double.valueOf(display.getText())); //Converts double to String
        //and formats it, stripping any trailing zeros and the floating point from the number displayed

        if (pointAndZeroCleared.matches("-0")) { //Checks if the resulting trimmed value turned out to be "-0"

            pointAndZeroCleared = "0"; //strips the leading "-" from the "0"
        }

        display.setText(pointAndZeroCleared); //Outputs the corrected value to the display

        num1 = display.getText();       //Stores the current displayed number in the global variable num1
        flag = "+";                     //Sets the operator flag to the corresponding operator sign
        afterTheOperator = true;        //This lets the digit buttons know that they have to overwrite the number
                                        //displayed
        numIterative = "";              //Empties numIterative so that the function, calculating the result, knows
                                        //that the next iteration will be the 1st and to use the num2 variable as the
                                        //2nd operand instead of numIterative
    }

    public void onButtonSubtractClick() {

        highlightOnKeyPress(buttonSubtract);

        DecimalFormat format = new DecimalFormat("0.###########");
        String pointAndZeroCleared = format.format(Double.valueOf(display.getText()));

        if (pointAndZeroCleared.matches("-0")) {

            pointAndZeroCleared = "0";
        }

        display.setText(pointAndZeroCleared);
        num1 = display.getText();
        flag = "-";
        afterTheOperator = true;
        numIterative = "";
    }

    public void onButtonMultiplyClick() {

        highlightOnKeyPress(buttonMultiply);

        DecimalFormat format = new DecimalFormat("0.###########");
        String pointAndZeroCleared = format.format(Double.valueOf(display.getText()));

        if (pointAndZeroCleared.matches("-0")) {

            pointAndZeroCleared = "0";
        }

        display.setText(pointAndZeroCleared);
        num1 = display.getText();
        flag = "*";
        afterTheOperator = true;
        numIterative = "";
    }

    public void onButtonDivideClick() {

        highlightOnKeyPress(buttonDivide);

        DecimalFormat format = new DecimalFormat("0.###########");
        String pointAndZeroCleared = format.format(Double.valueOf(display.getText()));

        if (pointAndZeroCleared.matches("-0")) {

            pointAndZeroCleared = "0";
        }

        display.setText(pointAndZeroCleared);
        num1 = display.getText();
        flag = "/";
        afterTheOperator = true;
        numIterative = "";
    }

    public void onButtonEqualsClick() {         //The "=" button, calculates the resulting value

        highlightOnKeyPress(buttonEquals);

        if (Objects.equals(flag, "+")) { //Checks what the operator flag is set to (+)

            if (!Objects.equals(numIterative, "")) { //If numIterative is not empty, that means the 2nd operand
                //from the previous operation has been stored in numIterative, and will be used again as the 2nd operand here

                num1 = display.getText(); //Stores the outputted result of the previous operation in num1
                double result = Double.parseDouble(num1) + Double.parseDouble(numIterative); //Converts the values
                //of both variables to double and stores the result

                String stringRes = Double.toString(result); //Converts the result to String
                DecimalFormat format = new DecimalFormat("0.###########"); //Presets the format
                display.setText(format.format(Double.valueOf(stringRes))); //Converts the String result to double,
                //formats it and outputs it to the display

                equals = true; //Sets the equals flag to true, this lets the digit buttons know to overwrite the number
//                            //displayed on the first press
            }

            if (Objects.equals(numIterative, "")) { //If numIterative is empty, that means that this is the first
                //iteration of the operation

                num2 = display.getText(); //Stores the value of the 2nd operand, currently on the display, into num2
                double result = Double.parseDouble(num1) + Double.parseDouble(num2); //Converts the values
                //of both variables to double and stores the result

                String stringRes = Double.toString(result); //Converts the result to String
                DecimalFormat format = new DecimalFormat("0.###########"); //Presets the format
                display.setText(format.format(Double.valueOf(stringRes))); //Converts the String result to double,
                //formats it and outputs it to the display

                equals = true; //Sets the equals flag to true, this lets the digit buttons know to overwrite the number
//                            //displayed on the first press

                numIterative = num2; //Stores the 2nd operand into numIterative to be used as the 2nd operand during
                //the 2nd and all consecutive iterations

                num2 = ""; //Empties num2
            }
        }

        if (Objects.equals(flag, "-")) {

            if (!Objects.equals(numIterative, "")){

                num1 = display.getText();
                double result = Double.parseDouble(num1) - Double.parseDouble(numIterative);
                String stringRes = Double.toString(result);
                DecimalFormat format = new DecimalFormat("0.###########");
                display.setText(format.format(Double.valueOf(stringRes)));
                equals = true;
            }

            if (Objects.equals(numIterative, "")) {

                num2 = display.getText();
                double result = Double.parseDouble(num1) - Double.parseDouble(num2);
                String stringRes = Double.toString(result);
                DecimalFormat format = new DecimalFormat("0.###########");
                display.setText(format.format(Double.valueOf(stringRes)));
                equals = true;
                numIterative = num2;
                num2 = "";
            }
        }

        if (Objects.equals(flag, "*")) {

            if (!Objects.equals(numIterative, "")) {

                num1 = display.getText();
                double result = Double.parseDouble(num1) * Double.parseDouble(numIterative);
                String stringRes = Double.toString(result);
                DecimalFormat format = new DecimalFormat("0.###########");
                display.setText(format.format(Double.valueOf(stringRes)));
                equals = true;
            }

            if (Objects.equals(numIterative, "")) {

                num2 = display.getText();
                double result = Double.parseDouble(num1) * Double.parseDouble(num2);
                String stringRes = Double.toString(result);
                DecimalFormat format = new DecimalFormat("0.###########");
                display.setText(format.format(Double.valueOf(stringRes)));
                equals = true;
                numIterative = num2;
                num2 = "";
            }
        }

        if (Objects.equals(flag, "/")) {

            if (!Objects.equals(numIterative, "")) {

                num1 = display.getText();
                double result = Double.parseDouble(num1) / Double.parseDouble(numIterative);
                String stringRes = Double.toString(result);
                DecimalFormat format = new DecimalFormat("0.###########");
                display.setText(format.format(Double.valueOf(stringRes)));
                equals = true;
            }

            if (Objects.equals(numIterative, "")) {

                num2 = display.getText();
                double result = Double.parseDouble(num1) / Double.parseDouble(num2);
                String stringRes = Double.toString(result);
                DecimalFormat format = new DecimalFormat("0.###########");
                display.setText(format.format(Double.valueOf(stringRes)));
                equals = true;
                numIterative = num2;
                num2 = "";
            }
        }
        switchBackspaceButton();
//        setOnBooChangeListener(mOnChange);
    }

    public void onButtonRepeatSwitchClick() {

        //Implement method
    }

    public void onButtonNegativeClick() {   //Switching to negative or positive number

        highlightOnKeyPress(buttonNegative);

        double displayed = Double.parseDouble(display.getText()); //Grab the displayed value, convert it to double
        //and store in displayed

        if (displayed < 0) {    //If the number is negative

            double displayPos = Math.abs(displayed);    //Convert to positive and store in displayedPos
            DecimalFormat format = new DecimalFormat("0.###########");  //Preset the output format
            display.setText(format.format(Double.valueOf(displayPos))); //Trim the final value, convert to String, and
            //output to the display
        }

        if (displayed > 0) {    //If the number is positive

            double displayNeg = -displayed;     //Convert to negative and store in displayNeg
            DecimalFormat format = new DecimalFormat("0.###########");  //Preset the output format
            display.setText(format.format(Double.valueOf(displayNeg)));   //Trim the final value, convert to String, and
            //output to the display
        }
    }

    public void onButtonPointClick() {      //Introducing the floating point

        highlightOnKeyPress(buttonPoint);

        String displayed = display.getText();   //Store currently displayed text into displayed

        if (!displayed.contains(".") && (!afterTheOperator && !equals)) { //If there's no f.point already and none
            //of the flags is set to "true"

            String pointAdded = displayed + ".";    //Append the f.point to the currently displayed number
            display.setText(pointAdded);    //Output the resulting value
        }

        if (afterTheOperator || equals) {   //If either of the flags is set to "true"

            //Then I'd like the app to input "0." for the 1st operand or the 2nd operand
            //for us if we hit the f.point button after either seeing the result or pressing an operator button:

            display.setText("0.");
            afterTheOperator = false;   //Reset both flags
            equals = false;
            switchBackspaceButton();
        }
    }

    public void onButtonBackspaceClick() {  //Deleting the last digit on the display

        if (willHighlight) {

            highlightOnKeyPress(buttonBackspace);
        }

        if (!equals) { //Makes the backspace only work on entered operands and not the result

            String displayed = display.getText();   //Grab the displayed value, store in displayed

            if (displayed.length() == 1) {   //If there's just a character on the screen

                display.setText("0"); //Set the displayed string to zero
            }

            if (displayed.length() > 1) {   //If there's more than one character

                String trimmed = displayed.substring(0, displayed.length() - 1);    //Reduce the length by 1
                display.setText(trimmed);   //Output the resulting value
            }

            if ((displayed.matches("-.")) || (displayed.matches("-0."))) {  //If there's either a single
                //character with the leading minus or a zero with the leading minus and the decimal point

                display.setText("0");   //Set the displayed string to zero
            }
        }
    }

    public void highlightOnKeyPress(Button button) {    //Highlight the passed button briefly after pressing it

        Runnable highlight = new Runnable() {   //Create a new Runnable object

            @Override
            public void run() {

                button.setStyle("-fx-background-color: #1B6B93");   //Set the bg color to a different one immediately
                //after pressing
            }
        };

        Runnable unHighlight = new Runnable() {     //Create a new Runnable object

            @Override
            public void run() {

                button.setStyle(":hover -fx-background-color: #526D82");    //Set the stylesheet back to change
                // color on hover

            }
        };

        executor.execute(highlight);    //Highlight the button
        executor.schedule(unHighlight, 150, TimeUnit.MILLISECONDS);     //Unhighlight it almost immediately after
    }

    void switchBackspaceButton() {  //Switch the appearance of the BP button <- THIS IS  BUGGED

//        if (equals) {   //If equals is true, make the button appear inactive
//
//            buttonBackspace.setStyle("-fx-background-color: #3C486B; -fx-text-fill: #61677A");
//            willHighlight = false;  //Prohibits onclick highlighting
//        }
//
//        if (!equals){   //If equals is false, reset the appearance
//
//            buttonBackspace.setStyle(":hover -fx-background-color: #526D82");
//            willHighlight = true;   //Allows onclick highlighting
//        }
    }



}