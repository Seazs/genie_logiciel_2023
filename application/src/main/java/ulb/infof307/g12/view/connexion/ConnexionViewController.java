package ulb.infof307.g12.view.connexion;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import lombok.Setter;
import org.apache.commons.lang3.function.TriFunction;
import ulb.infof307.g12.view.listeners.UserCredentialsListener;

import java.util.function.Function;

public class ConnexionViewController {
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
     * @param event évènement
     */
    @FXML
    protected void onConnectButtonClick(ActionEvent event) {
        getCredentialsAndOperate(listener::onLogin);
    }

    /**
     * Ce qui s'exécute lorsqu'on clique sur le bouton de registration
     * @param actionEvent evenement
     */
    @FXML
    public void onRegisterButtonClick(ActionEvent actionEvent) {
        getCredentialsAndOperate(listener::onRegister);
    }

    /**
     * Switch l'application en mode en ligne ou hors ligne
     * @param actionEvent evenement
     */
    @FXML
    public void switchOnlineMode(ActionEvent actionEvent) {
        String text = (onlineToggle.isSelected()) ? "hors ligne" : "en ligne" ;
        onlineToggle.setText("Passer en mode " + text);
    }

    /**
     * Récupère les informations rentrées par l'utilisateur et exécute une méthode donnée en tenant compte de ces informations.
     * Affiche ensuite le résultat de l'opération.
     * @param function la méthode à exécuter
     */
    private void getCredentialsAndOperate(TriFunction<String,String,Boolean,String> function){
        String username = usernameField.getText();
        String password = passwordField.getText();
        boolean isOnline = onlineToggle.isSelected();
        String result = function.apply(username,password,isOnline);
        messageLabel.setText(result);
    }
}
