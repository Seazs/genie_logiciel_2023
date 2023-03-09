package ulb.infof307.g12.view.connection;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ulb.infof307.g12.controller.storage.GestionnaireUtilisateur;
import ulb.infof307.g12.view.paquets.MenuPaquetController;

import java.io.IOException;
import java.util.EventObject;

public class ConnexionMenuController {
    @FXML
    private Label messageLabel;
    @FXML
    private TextField usernameField;
    @FXML

    private PasswordField passwordField;

    @FXML
    protected void onConnectButtonClick(ActionEvent event) throws IOException {
        String username = usernameField.getText();
        String password = passwordField.getText();
        GestionnaireUtilisateur gestionnaire = new GestionnaireUtilisateur();
        if (gestionnaire.connect(username, password)) {
            messageLabel.setText("Connecting: " + usernameField.getText() + " = " + passwordField.getText());
            FXMLLoader loader = new FXMLLoader(MenuPaquetController.class.getResource("menuPaquet.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            MenuPaquetController controller = loader.getController();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();

        }
        else
        {
            switch (gestionnaire.getStatus()) {
                case USERNAME_DOES_NOT_EXIST ->
                        messageLabel.setText("Username does not exist. Please try again or create your account!");
                case WRONG_PASSWORD -> messageLabel.setText("Wrong password. Please try again.");

            }
        }
    }
    @FXML
    protected void onRegisterButtonClick() throws IOException {
        String username = usernameField.getText();
        String password = passwordField.getText();
        GestionnaireUtilisateur gestionnaire = new GestionnaireUtilisateur();
        if (gestionnaire.register(username, password)) {
            messageLabel.setText("Register: " + usernameField.getText() + " = " + passwordField.getText());
        }
        else
        {
            switch (gestionnaire.getStatus())
            {
                case USERNAME_DOES_ALREADY_EXIST ->
                        messageLabel.setText("Username does already exist. Please try another username!");

            }
        }
    }
}