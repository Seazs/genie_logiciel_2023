module connection {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    opens ulb.infof307.g12.view.connection to javafx.fxml;
    exports ulb.infof307.g12.view.connection;

    opens ulb.infof307.g12.view.profiles to javafx.fxml;
    exports ulb.infof307.g12.view.profiles;

    opens ulb.infof307.g12.view.paquets to javafx.fxml;
    exports ulb.infof307.g12.view.paquets;
}