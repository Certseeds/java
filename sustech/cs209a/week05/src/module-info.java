module week {
    requires javafx.controls;
    requires javafx.fxml;

    opens week05 to javafx.fxml;
    exports week05;
}