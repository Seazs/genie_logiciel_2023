package ulb.infof307.g12.view.connexion;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import lombok.Setter;
import ulb.infof307.g12.controller.listeners.UserCredentialsListener;

public class ConnexionVueController {
    @FXML
    private Label messageLabel;
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;

    @Setter
    private UserCredentialsListener listener;

    @FXML
    protected void onConnectButtonClick(ActionEvent event) {
        String username = usernameField.getText();
        String password = passwordField.getText();
        String result = listener.onLogin(username,password);
        messageLabel.setText(result);
    }

    @FXML
    public void onRegisterButtonClick(ActionEvent actionEvent) {
        String username = usernameField.getText();
        String password = passwordField.getText();
        String result = listener.onRegister(username,password);
        messageLabel.setText(result);
    }


}
