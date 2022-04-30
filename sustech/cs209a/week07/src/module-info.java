module week {
    requires javafx.controls;
    requires javafx.fxml;

    opens week07 to javafx.fxml;
    exports week07;
}