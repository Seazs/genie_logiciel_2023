open module application {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires static lombok;
    requires spring.web;
    requires freetts;
    requires com.fasterxml.jackson.databind;
    requires json;
    requires javafx.web;
    requires org.apache.commons.lang3;
    requires org.apache.commons.io;
    requires jlatexmath;
    requires java.desktop;
    requires javafx.swing;

    exports ulb.infof307.g12.controller.javafx.connexion;

    exports ulb.infof307.g12.view.exception;

    exports ulb.infof307.g12.controller.javafx.exception;

    exports ulb.infof307.g12.view.profiles;

    exports ulb.infof307.g12.view.paquets;

    exports ulb.infof307.g12.view.connexion;

    exports ulb.infof307.g12.controller.javafx.paquets;

    exports ulb.infof307.g12.controller.javafx.store;

    exports ulb.infof307.g12.view.store;

    exports ulb.infof307.g12.view.dto;

    exports ulb.infof307.g12.controller.javafx.synchronisation;

    exports ulb.infof307.g12.view.synchronisation;


}