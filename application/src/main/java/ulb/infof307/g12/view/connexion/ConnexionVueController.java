package ulb.infof307.g12.view.connexion;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import lombok.Setter;
import ulb.infof307.g12.controller.listeners.UserCredentialsListener;

public class ConnexionVueController {
    @FXML
    private Label messageLabel;
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private ToggleButton onlineToggle;
    @Setter
    private UserCredentialsListener listener;

    /**
     * Ce qui s'exécute lorsqu'on clique sur le bouton de connexion
     * @param event
     */
    @FXML
    protected void onConnectButtonClick(ActionEvent event) {
        String username = usernameField.getText();
        String password = passwordField.getText();
        boolean isOnline = onlineToggle.isSelected();
        String result = listener.onLogin(username,password,isOnline);
        messageLabel.setText(result);
    }

    /**
     * Ce qui s'exécute lorsqu'on clique sur le bouton de registration
     * @param actionEvent
     */
    @FXML
    public void onRegisterButtonClick(ActionEvent actionEvent) {
        String username = usernameField.getText();
        String password = passwordField.getText();
        boolean isOnline = onlineToggle.isSelected();
        String result = listener.onRegister(username,password,isOnline);
        messageLabel.setText(result);
    }

    @FXML
    public void switchOnlineMode(ActionEvent actionEvent) {
        String text = (onlineToggle.isSelected()) ?"hors ligne" : "en ligne" ;
        onlineToggle.setText("Passer en mode " + text);
    }
}
