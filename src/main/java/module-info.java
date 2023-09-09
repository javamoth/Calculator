module com.github.javamoth.moth_calculator {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.github.javamoth.moth_calculator to javafx.fxml;
    exports com.github.javamoth.moth_calculator;
}