module week {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.prefs;
    requires jakarta.xml.bind;
    requires jdk.compiler;
    opens week08 to javafx.fxml, jakarta.xml.bind, jakarta.activation;
    exports week08;
}