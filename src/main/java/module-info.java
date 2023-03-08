module connection {
    requires javafx.controls;
    requires javafx.fxml;


    opens ulb.infof307.g12.view.connection to javafx.fxml;
    exports ulb.infof307.g12.view.connection;
}