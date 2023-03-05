package ulb.infof307.g12.connexion;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;

public class ConnexionMenuController {
    @FXML
    private Label messageLabel;
    @FXML
    private TextField usernameField;
    @FXML

    private PasswordField passwordField;

    @FXML
    protected void onConnectButtonClick() throws IOException {
        String username = usernameField.getText();
        String password = usernameField.getText();
        GestionnaireUtilisateur gestionnaire = new GestionnaireUtilisateur();
        if (gestionnaire.connect(username, password)) {
            messageLabel.setText("Connecting: " + usernameField.getText() + " = " + passwordField.getText());
        }
        else
        {
            switch (gestionnaire.getStatus()) {
                case USERNAME_DOES_NOT_EXIST ->
                        messageLabel.setText("Username does not exist. Please try again or create your account!");
                case WRONG_PASSWORD -> messageLabel.setText("Wrong password. Please try again.");
                case USERNAME_DOES_ALREADY_EXIST ->
                        messageLabel.setText("Username does already exist. Please try another username!");

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
                case USERNAME_DOES_ALREADY_EXIST:
                    messageLabel.setText("Username does not exist. Please try again or create your account!");
                    break;
            }
        }
    }
}