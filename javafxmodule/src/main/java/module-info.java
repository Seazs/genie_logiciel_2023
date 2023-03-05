module com.example.javafxmodule {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    opens com.example.javafxmodule to javafx.fxml;
    exports com.example.javafxmodule;
}