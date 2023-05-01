module connexion {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires static lombok;
    requires spring.web;
    requires freetts;
    requires com.fasterxml.jackson.databind;

    opens ulb.infof307.g12.controller.javafx.connexion to javafx.fxml;
    exports ulb.infof307.g12.controller.javafx.connexion;

    opens ulb.infof307.g12.view.exception to javafx.fxml;
    exports ulb.infof307.g12.view.exception;

    opens ulb.infof307.g12.controller.javafx.exception to javafx.fxml;
    exports ulb.infof307.g12.controller.javafx.exception;

    opens ulb.infof307.g12.view.profiles to javafx.fxml;
    exports ulb.infof307.g12.view.profiles;

    opens ulb.infof307.g12.view.paquets to javafx.fxml;
    exports ulb.infof307.g12.view.paquets;

    opens ulb.infof307.g12.view.connexion to javafx.fxml;
    exports ulb.infof307.g12.view.connexion;

    exports ulb.infof307.g12.controller.javafx.paquets;
    opens ulb.infof307.g12.controller.javafx.paquets to javafx.fxml;

    opens ulb.infof307.g12.view.cartes;
    exports ulb.infof307.g12.view.cartes to javafx.fxml;

    opens ulb.infof307.g12.model to javafx.base, com.fasterxml.jackson.databind;

    opens ulb.infof307.g12.controller.javafx.store to javafx.fxml;
    exports ulb.infof307.g12.controller.javafx.store;

    opens ulb.infof307.g12.view.store to javafx.fxml;
    exports ulb.infof307.g12.view.store;

}