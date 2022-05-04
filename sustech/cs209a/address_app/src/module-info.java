module week {
    requires javafx.controls;
    requires javafx.fxml;
    requires jakarta.xml.bind;
    requires java.prefs;

    opens address_app to javafx.fxml;
    opens address_app.view to javafx.fxml;
    opens address_app.model to jakarta.xml.bind, org.glassfish.jaxb.core;
    opens address_app.util to jakarta.xml.bind, org.glassfish.jaxb.core, org.glassfish.jaxb.runtime;
    exports address_app;
    exports address_app.util;
}