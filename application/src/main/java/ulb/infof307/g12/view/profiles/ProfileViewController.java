package ulb.infof307.g12.view.profiles;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextInputDialog;
import lombok.Setter;
import ulb.infof307.g12.controller.javafx.connexion.MenuPrincipal;
import ulb.infof307.g12.view.listeners.ProfileViewListener;

import java.util.Optional;

public class ProfileViewController {

    @FXML
    private Label messageLabelProfil, pseudoLabel, mdpLabel;

    @Setter
    private ProfileViewListener listener;


    /**
     * Ce qui s'exécute lorsqu'on clique sur le bouton de retour au menu principal
     * @param event l'événement qui a déclenché l'action
     */
    public void returnMenuPaquet(ActionEvent event){
        MenuPrincipal.getINSTANCE().returnToMenuPaquet();
    }


    /**
     * Ce qui s'exécute lorsqu'on clique sur le bouton de changement de mot de passe
     */
    @FXML
    protected void onChangePasswordButtonClick() {

        TextInputDialog dialog = new TextInputDialog("");
        dialog.setTitle("Change password");
        dialog.setHeaderText("Enter your new password:");
        PasswordField pwdField = new PasswordField();
        dialog.getDialogPane().setContent(pwdField);
        Optional<String> newPassword = dialog.showAndWait();
        try {
            String result = listener.changePassword(newPassword);
            mdpLabel.setText("Mot de passe: " + newPassword.get());
            messageLabelProfil.setText(result);
        } catch (RuntimeException e) {
            MenuPrincipal.getINSTANCE().showErrorPopup("veuillez entrer un mot de passe");
        }


    }

    /**
     * Ce qui s'exécute lorsqu'on clique sur le bouton de déconnexion
     * @param e l'événement qui a déclenché l'action
     */
    @FXML
    public void onDeconnexionButtonclick(ActionEvent e){
        if (listener!=null){
            listener.disconnect();
        }
    }

    public void setPseudoLabel(String pseudo){
        this.pseudoLabel.setText("Pseudo: " + pseudo);
    }
    public void setMdpLabel(String mdp){
        this.mdpLabel.setText("Mot de passe: " + mdp);
    }

}