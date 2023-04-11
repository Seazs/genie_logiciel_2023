module connection {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires static lombok;

    opens ulb.infof307.g12.controller.javafx.connexion to javafx.fxml;
    exports ulb.infof307.g12.controller.javafx.connexion;

    opens ulb.infof307.g12.view.profiles to javafx.fxml;
    exports ulb.infof307.g12.view.profiles;

    opens ulb.infof307.g12.view.paquets to javafx.fxml;
    exports ulb.infof307.g12.view.paquets;

    opens ulb.infof307.g12.view.connexion to javafx.fxml;
    exports ulb.infof307.g12.view.connexion;
    exports ulb.infof307.g12.controller.javafx.paquets;
    opens ulb.infof307.g12.controller.javafx.paquets to javafx.fxml;

    opens ulb.infof307.g12.model to javafx.base;
}