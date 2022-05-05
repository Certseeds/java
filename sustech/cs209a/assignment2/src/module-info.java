module assignment {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.prefs;
    requires jakarta.xml.bind;
    requires jdk.compiler;
    requires org.yaml.snakeyaml;
    opens assignment2 to javafx.fxml, jakarta.xml.bind, jakarta.activation;
    exports assignment2;
}