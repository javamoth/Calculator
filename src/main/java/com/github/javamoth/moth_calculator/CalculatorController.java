package com.github.javamoth.moth_calculator;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class CalculatorController {

    ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor(); //This service will be used to
    //briefly highlight and unhighlight buttons after pressing/clicking them as it's hard to achieve in CSS
    //and also to run a couple of other tasks
    //We then shut it down in the main class on app exit

    private final CalculatorModel model = new CalculatorModel();    //Accessing the model class

    //Defining global variables

    @FXML
    private Label display, displayMini, displayError;

    //Defining button variables to use them in the highlighting on press method etc.
    @FXML
    private Button buttonNegative, buttonPoint, buttonC, buttonEquals, buttonBackspace, buttonRepeatSwitch;

    //Button onAction methods

    @FXML
    private void onDigitPress(ActionEvent event) {  //Grab the passed onAction event from the digit button

        Button button = (Button) event.getTarget(); //Get the Button object from the event and store it in the "button"
                                                    //variable

        highlightOnKeyPress(button);    //Pass the current button to highlightOnKeyPress so that it lights up when fired

        try {   //Prevent from crashing the app via the infinity sign/overflow

            //Checks if the "0" without the f. point is being displayed or either an operator button or "=" being previously pressed
            if ((new BigDecimal(display.getText()).signum() == 0 && !display.getText().contains(".")) || model.getAfterTheOperator() || model.getEquals()) {

                display.setText(button.getText());  //Resets the displayed number to the button's digit
                model.setAfterTheOperator(false);   //Sets the operator flag to "false" after the 1st press to allow for
                // appending digits

                model.setEquals(false);     //Sets the "=" flag to "false" after the 1st press to allow for appending digits
                model.setNumIterative("");  //Resets numIterative to prevent from iterating on the old 2nd operand after
                //user has entered a new number

                model.setCalculated(false);     //Allows the operator button onAction calculation to continue after a new operand
                //has been entered
            } else if (display.getText().length() < 21) {     //Limit input to the width of the display

                String num = display.getText(); //Gets the number that's already on the display
                num = num + button.getText();   //Appends the button's text value to it
                display.setText(num);   //Displays the resulting number
            }

        } catch (NumberFormatException | ArithmeticException e) {

            resetAfterError();
        }
    }

    @FXML
    protected void onButtonCClick() {   //Clears the display, resets all flags, empties all variables

        highlightOnKeyPress(buttonC);
        display.setText("0");
        displayMini.setText("");
        displayError.setText("");
        model.setFlag("");
        model.setAfterTheOperator(false);
        model.setEquals(false);
        model.setNumIterative("");
        model.setResult(BigDecimal.ZERO);
        model.setNum1("");
        model.setNum2("");
        model.setCalculated(false);
    }

    @FXML
    public void onOperatorPress(ActionEvent event) {    //Grab the passed onAction event from the operator button

            Button button = (Button) event.getTarget(); //Get the Button object from the event and store it in the "button"
            //variable

            highlightOnKeyPress(button);    //Pass the current button to highlightOnKeyPress so that it lights up when fired

        try {   //Prevent crashing the app via the infinity sign/overflow

                //Just in case the user has backspaced to "-0." after entering the 1st operand we perform the steps below
                String pointAndZeroCleared = model.formatOutput(display.getText()); //Converts double to String
                //and formats it, stripping any trailing zeros and the floating point from the number displayed

            if (pointAndZeroCleared.matches("-0")) {    //Checks if the resulting trimmed value turned out to be "-0"

                pointAndZeroCleared = "0";  //strips the leading "-" from the "0"
            }

            if (!Objects.equals(model.getNum1(), "")) {   //If num1 has been set, on the next operator button press get
                //and set num2

                display.setText(pointAndZeroCleared);           //Outputs the corrected value to the display
                model.setNum2(display.getText());          //Stores the current displayed number in the global variable num2

                //Show the current operation on the mini display
                displayMini.setText(model.formatOutput(model.getNum1()) + " " + model.getFlag() + " " + model.getNumIterative() + " =");
            }

            if (!Objects.equals(model.getNum1(), "") && !Objects.equals(model.getNum2(), "") && !model.isCalculated()) {
                //Calculates an intermediate result if both num1 and num2 have been set and the result hasn't been already
                //calculated

                display.setText(pointAndZeroCleared);   //Outputs the corrected value to the display
                String result = model.calculate("Intermediate");    //Pass the "Intermediate" flag, calculate an
                //intermediate result

                updateDisplay(result);  //Output the calculated value
                model.setFlag(button.getText());    //Sets the operator flag corresponding to the button's text

                //Show the current operation on the mini display
                displayMini.setText(model.formatOutput(model.getNum1()) + " " + model.getFlag());
                model.setCalculated(true);    //Prevents from iterating on the result while waiting for the next operand

            } else {    //Does the regular thing if none of the operands are known yet

                display.setText(model.formatOutput(pointAndZeroCleared));   //Outputs the corrected value to the display
                model.setNum1(display.getText());   //Stores the current displayed number in the global variable num1
                model.setFlag(button.getText());    //Sets the operator flag corresponding to the button's text

                //Show the 1st operand and the operator on the mini display
                displayMini.setText(model.formatOutput(model.getNum1()) + " " + model.getFlag());
                model.setAfterTheOperator(true);    //This lets the digit buttons know that they have to overwrite the number
                //displayed

                model.setNumIterative("");  //Empties numIterative so that the function, calculating the result, knows
                //that the next iteration will be the 1st and to use the num2 variable as the
                //2nd operand instead of numIterative

                model.setCalculated(true);
            }
        } catch (NumberFormatException | ArithmeticException e){  //Clear and reset everything

            resetAfterError();
        }
    }

    @FXML
    public void onEqualsPress() {   //The "=" button, calculates the resulting value

        highlightOnKeyPress(buttonEquals);

        if (!Objects.equals(model.getFlag(), "")) { //Only execute the rest of the method if the operator flag is set

            if (Objects.equals(display.getText(), "0") && Objects.equals(model.getNum1(), "")) {    ////Do not pass empty strings to format
                //do nothing
            } else {

                try {    //Prevent crashing the app via the infinity sign/overflow

                    String result = model.calculate(display.getText());    //Pass the current displayed value to calculate() the res.

                    //Show the current operation on the mini display
                    if (Objects.equals(model.getNumIterative(), "") && Objects.equals(model.getNum1(), "")) {
                        //do nothing
                    }

                    if (Objects.equals(model.getNumIterative(), "") && !Objects.equals(model.getNum1(), "")) {

                        displayMini.setText(model.formatOutput(model.getNum1()) + " " + model.getFlag());

                    } else {

                        displayMini.setText(model.formatOutput(model.getNum1()) + " " + model.getFlag() + " " + model.formatOutput(model.getNumIterative()) + " =");
                    }

                    updateDisplay(result);  //Output the calculated value

                } catch (NumberFormatException | ArithmeticException e) {   //Clear and reset everything

                    resetAfterError();
                }
            }
        }
    }

    @FXML
    public void onButtonRepeatSwitchClick() {

        Runnable activate = () -> Platform.runLater(() -> { //Platform.runLater makes it not run on the FX thread

            model.setIterativeOn(false);
            buttonRepeatSwitch.setStyle("-fx-background-color: #E25E3E;");    //Make the bg color orange

            //This is the only way I've found to be working when I wanted the repeat switch to still have hover effects
            //setStyle with the :hover pseudo class just didn't work
            buttonRepeatSwitch.setOnMouseEntered(r -> buttonRepeatSwitch.setStyle("-fx-background-color:#af380f;"));
            buttonRepeatSwitch.setOnMouseExited(r -> buttonRepeatSwitch.setStyle("-fx-background-color:#E25E3E;"));
        });

        Runnable deactivate = () -> Platform.runLater(() -> {

            model.setIterativeOn(true);
            buttonRepeatSwitch.setStyle("-fx-background-color: #526D82;");    //Reset the bg color to default
            buttonRepeatSwitch.setOnMouseEntered(r -> buttonRepeatSwitch.setStyle("-fx-background-color:#164B60;"));
            buttonRepeatSwitch.setOnMouseExited(r -> buttonRepeatSwitch.setStyle("-fx-background-color:#526D82;"));
        });

        if (model.isIterativeOn()) {

            executor.execute(activate);    //Turn the switch on (Iterative calculation will be off)
        }

        if (!model.isIterativeOn()) {

            executor.execute(deactivate);    //Turn the switch off (Iterative calculation will be on)
        }
    }

    @FXML
    public void onButtonNegativeClick() {   //Switching to negative or positive number

        highlightOnKeyPress(buttonNegative);
        BigDecimal displayed = new BigDecimal(display.getText()); //Grab the displayed value, convert it to double
        //and store in displayed

        if (displayed.signum() == -1) {    //If the number is negative

            BigDecimal displayPos = displayed.abs();    //Convert to positive and store in displayedPos
            display.setText(model.formatOutput(displayPos.toString())); //Trim the final value, convert to String, and
            //output to the display
        }

        if (displayed.signum() == 1) {    //If the number is positive

            BigDecimal displayNeg = displayed.negate();    //Convert to negative and store in displayNeg
            display.setText(model.formatOutput(displayNeg.toString()));   //Trim the final value, convert to String, and
            //output to the display
        }
    }

    @FXML
    public void onButtonPointClick() {      //Introducing the floating point

        highlightOnKeyPress(buttonPoint);
        String displayed = display.getText();   //Store the currently displayed text into "displayed"

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

    @FXML
    public void onButtonBackspaceClick() {  //Deleting the last digit on the display

        highlightOnKeyPress(buttonBackspace);

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

    @FXML
    public void highlightOnKeyPress(Button button) {    //Highlight the passed button briefly after pressing it

        //Runnable tasks
        Runnable highlight = () -> {

            button.setStyle("-fx-background-color: #1B6B93");   //Set the bg color to a different one immediately
            //after pressing
        };

        Runnable unHighlight = () -> {

            button.setStyle(":hover -fx-background-color: #526D82");    //Set the stylesheet back to change
            // color on hover
        };

        executor.execute(highlight);    //Highlight the button
        executor.schedule(unHighlight, 150, TimeUnit.MILLISECONDS);     //Unhighlight it almost immediately after
    }

    @FXML
    public void updateDisplay(String output) {  //Output the info to the display

        display.setText(model.formatOutput(output));
    }

    @FXML
    public void resetAfterError() {     //Reset everything after the exception and display the error message

        display.setText("0");
        displayMini.setText("");
        model.setFlag("");
        model.setAfterTheOperator(false);
        model.setEquals(false);
        model.setNumIterative("");
        model.setResult(BigDecimal.ZERO);
        model.setNum1("");
        model.setNum2("");
        model.setCalculated(false);

        //Runnable tasks
        Runnable showErrorMessage = () -> Platform.runLater(() -> {
        displayError.setText("Error: number too big");   //Display the error message
        });

        Runnable clearErrorMessage = () -> Platform.runLater(() -> {
        displayError.setText("");   //Clear the error message
        });

        executor.execute(showErrorMessage);
        executor.schedule(clearErrorMessage, 3, TimeUnit.SECONDS);
    }
}