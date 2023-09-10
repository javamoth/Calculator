package com.github.javamoth.moth_calculator;

import java.text.DecimalFormat;
import java.util.Objects;

public class CalculatorModel {  //Main logic

    private String flag; //Stores the operator: +, -, *, /

    private String num1; //Stores the 1st operand

    private String num2; //Stores the 2nd operand

    private String numIterative = ""; //Stores the latest operand to be used as the 2nd operand during iterative calculation
    //Used in a couple of unique checks

    private Boolean afterTheOperator = false; //This flag lets the Onclick functions of the number buttons know that
    //an operator button has been pressed, and they need to replace the displayed number
    //entirely instead of appending digits to it.

    private Boolean equals = false;         //This flag lets the Onclick functions of the number buttons know that the "=" button
    //has been pressed, and they need to replace the displayed number
    //entirely instead of appending digits to it.

    private final DecimalFormat DECI_FORMAT = new DecimalFormat("0.###########");  //Presets the decimal format to be used

    private double result;  //Where result is stored

    //Delete this comment

    //Setters and getters
    public void setFlag(String flag) {
        this.flag = flag;
    }

    public void setNum1(String num1) {
        this.num1 = num1;
    }

    public String getNum1() {
        return num1;
    }

    public String getNum2() {
        return num2;
    }

    public void setNum2(String num2) {
        this.num2 = num2;
    }

    public double getResult() {
        return result;
    }

    public void setNumIterative(String numIterative) {
        this.numIterative = numIterative;
    }

    public Boolean getAfterTheOperator() {
        return afterTheOperator;
    }

    public void setAfterTheOperator(Boolean afterTheOperator) {
        this.afterTheOperator = afterTheOperator;
    }

    public Boolean getEquals() {
        return equals;
    }

    public void setEquals(Boolean equals) {
        this.equals = equals;
    }

    public DecimalFormat getDeciFormat() {
        return DECI_FORMAT;
    }

    public void setResult(double result) {
        this.result = result;
    }

    public String calculate(String currentValue) {   //Calculate the result

        if (!Objects.equals(numIterative, "")) {    //If numIterative is not empty, that means the 2nd operand
            //from the previous operation has been stored in numIterative, and will be used again as the 2nd operand here

            num1 = currentValue;   //Stores the outputted result of the previous operation in num1
            num2 = numIterative;   //
        }

        if (Objects.equals(numIterative, "")) { //If numIterative is empty, that means that this is the first
            //iteration of the operation

            num2 = currentValue;   //Stores the value of the 2nd operand, currently on the display, into num2

            numIterative = num2;    //Stores the 2nd operand into numIterative to be used as the 2nd operand during
            //the 2nd and all consecutive iterations
        }

        if (Objects.equals(flag, "+")) {    //If "flag" equals to "+"

            result = Double.parseDouble(num1) + Double.parseDouble(num2);   //Perform the calculation and store the res.
        }

        if (Objects.equals(flag, "-")) {

            result = Double.parseDouble(num1) - Double.parseDouble(num2);
        }

        if (Objects.equals(flag, "*")) {

            result = Double.parseDouble(num1) * Double.parseDouble(num2);
        }

        if (Objects.equals(flag, "/")) {

            result = Double.parseDouble(num1) / Double.parseDouble(num2);
        }

        equals = true;  //Sets the equals flag to true, this lets the digit buttons know to overwrite the number
        //displayed on the first press

        num2 = "";  //Empties num2
        return (DECI_FORMAT.format(result)); //Return the results
    }




    public String calcInter() {   //Calculate the result

//        if (!Objects.equals(numIterative, "")) {    //If numIterative is not empty, that means the 2nd operand
//            //from the previous operation has been stored in numIterative, and will be used again as the 2nd operand here
//
//            num1 = currentValue;   //Stores the outputted result of the previous operation in num1
//            num2 = numIterative;   //
//        }

        if (Objects.equals(numIterative, "")) { //If numIterative is empty, that means that this is the first
            //iteration of the operation

            numIterative = num2;    //Stores the 2nd operand into numIterative to be used as the 2nd operand during
            //the 2nd and all consecutive iterations
        }

        if (Objects.equals(flag, "+")) {    //If "flag" equals to "+"

            result = Double.parseDouble(num1) + Double.parseDouble(num2);   //Perform the calculation and store the res.
        }

        if (Objects.equals(flag, "-")) {

            result = Double.parseDouble(num1) - Double.parseDouble(num2);
        }

        if (Objects.equals(flag, "*")) {

            result = Double.parseDouble(num1) * Double.parseDouble(num2);
        }

        if (Objects.equals(flag, "/")) {

            result = Double.parseDouble(num1) / Double.parseDouble(num2);
        }

        equals = true;  //Sets the equals flag to true, this lets the digit buttons know to overwrite the number
        //displayed on the first press

        num2 = "";  //Empties num2
        return (DECI_FORMAT.format(result)); //Return the results
    }
}
