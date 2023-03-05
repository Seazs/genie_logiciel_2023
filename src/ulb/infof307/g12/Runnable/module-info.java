module com.example.javafxmodule {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    opens com.example.projet_infof307_g12 to javafx.fxml;
    exports com.example.projet_infof307_g12;
}