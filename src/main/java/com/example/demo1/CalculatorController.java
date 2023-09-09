package com.example.demo1;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class CalculatorController {

    ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor(); //This service will be used to
    //briefly highlight and unhighlight buttons after pressing/clicking them as it's hard to achieve in CSS
    //We then shut it down in the main class on app exit

    private final CalculatorModel model = new CalculatorModel();    //Accessing the model class

    //Setters and getters
    public void setModelFlag(String flag) {
        model.setFlag(flag);
    }

    //Defining global variables

    @FXML
    private Label display;

    //Defining button variables to use them in the highlighting on press method etc.
    @FXML
    private Button buttonNegative, buttonPoint, buttonC, buttonEquals;

    //Button onAction methods

    @FXML
    private void onDigitPress(ActionEvent event) {  //Grab the passed onAction event from the digit button

        Button button = (Button) event.getTarget(); //Get the Button object from the event and store it in the "button"
                                                    //variable

        highlightOnKeyPress(button);    //Pass the current button to highlightOnKeyPress so that it lights up when fired

        //Checks if the "0" without the f. point is being displayed or either an operator button or "=" being previously pressed

        if ((Double.parseDouble(display.getText()) == 0 && !display.getText().contains(".")) || model.getAfterTheOperator() || model.getEquals()) {

            display.setText(button.getText());  //Resets the displayed number to the button's digit
            model.setAfterTheOperator(false);   //Sets the operator flag to "false" after the 1st press to allow for
            // appending digits
            model.setEquals(false);     //Sets the "=" flag to "false" after the 1st press to allow for appending digits
        }

        else {

            String num = display.getText(); //Gets the number that's already on the display
            num = num + button.getText();   //Appends the button's text value to it
            display.setText(num);   //Displays the resulting number
        }
    }

    @FXML
    protected void onButtonCClick() {   //Clears the display, resets all flags, empties all variables

        highlightOnKeyPress(buttonC);
        display.setText("0");
        setModelFlag("");
        model.setAfterTheOperator(false);
        model.setEquals(false);
        model.setNumIterative("");
    }

    @FXML
    public void onOperatorPress(ActionEvent event) {    //Grab the passed onAction event from the operator button

        Button button = (Button) event.getTarget(); //Get the Button object from the event and store it in the "button"
        //variable

        highlightOnKeyPress(button);    //Pass the current button to highlightOnKeyPress so that it lights up when fired

        //Just in case the user has backspaced to "-0." after entering the 1st operand we perform the steps below

        String pointAndZeroCleared = model.getDeciFormat().format(Double.valueOf(display.getText())); //Converts double to String
        //and formats it, stripping any trailing zeros and the floating point from the number displayed


        if (pointAndZeroCleared.matches("-0")) {    //Checks if the resulting trimmed value turned out to be "-0"

            pointAndZeroCleared = "0";  //strips the leading "-" from the "0"
        }

        display.setText(pointAndZeroCleared);   //Outputs the corrected value to the display
        model.setNum1(display.getText());               //Stores the current displayed number in the global variable num1
        model.setFlag(button.getText());                //Sets the operator flag corresponding to the button's text
        model.setAfterTheOperator(true);          //This lets the digit buttons know that they have to overwrite the number
        //displayed
        model.setNumIterative("");                 //Empties numIterative so that the function, calculating the result, knows
                                            //that the next iteration will be the 1st and to use the num2 variable as the
                                            //2nd operand instead of numIterative
    }

    public void onEqualsPress() {   //The "=" button, calculates the resulting value

        highlightOnKeyPress(buttonEquals);
        String result = model.calculate(display.getText());    //Pass the current displayed value to calculate() the res.
        updateDisplay(result);  //Output the calculated value
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

            display.setText(model.getDeciFormat().format(Double.valueOf(displayPos))); //Trim the final value, convert to String, and
            //output to the display
        }

        if (displayed > 0) {    //If the number is positive

            double displayNeg = -displayed;     //Convert to negative and store in displayNeg

            display.setText(model.getDeciFormat().format(Double.valueOf(displayNeg)));   //Trim the final value, convert to String, and
            //output to the display
        }
    }

    public void onButtonPointClick() {      //Introducing the floating point

        highlightOnKeyPress(buttonPoint);
        String displayed = display.getText();   //Store currently displayed text into displayed

        if (!displayed.contains(".") && (!model.getAfterTheOperator() && !model.getEquals())) { //If there's no f.point already and none
            //of the flags is set to "true"

            String pointAdded = displayed + ".";    //Append the f.point to the currently displayed number
            display.setText(pointAdded);    //Output the resulting value
        }

        if (model.getAfterTheOperator() || model.getEquals()) {   //If either of the flags is set to "true"

            //Then I'd like the app to input "0." for the 1st operand or the 2nd operand
            //for us if we hit the f.point button after either seeing the result or pressing an operator button:

            display.setText("0.");
            model.setAfterTheOperator(false);   //Reset both flags
            model.setEquals(false);
        }
    }

    public void onButtonBackspaceClick() {  //Deleting the last digit on the display

        if (!model.getEquals()) { //Makes the backspace only work on entered operands and not the result

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

        //Create a new Runnable object
        Runnable highlight = () -> {

            button.setStyle("-fx-background-color: #1B6B93");   //Set the bg color to a different one immediately
            //after pressing
        };

        //Create a new Runnable object
        Runnable unHighlight = () -> {

            button.setStyle(":hover -fx-background-color: #526D82");    //Set the stylesheet back to change
            // color on hover

        };

        executor.execute(highlight);    //Highlight the button
        executor.schedule(unHighlight, 150, TimeUnit.MILLISECONDS);     //Unhighlight it almost immediately after
    }

    public void updateDisplay(String output) {  //Output the info to the display

        display.setText(output);
    }
}