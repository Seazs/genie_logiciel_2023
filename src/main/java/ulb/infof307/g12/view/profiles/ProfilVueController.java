package ulb.infof307.g12.view.profiles;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import lombok.Setter;
import ulb.infof307.g12.controller.javafx.connexion.MenuPrincipal;
import ulb.infof307.g12.controller.listeners.ProfilVueListener;

import java.util.Optional;

public class ProfilVueController {

    @FXML
    private Label messageLabelProfil;

    @Setter
    private ProfilVueListener listener;


    /**
     * Ce qui s'exécute lorsqu'on clique sur le bouton de retour au menu principal
     * @param event l'événement qui a déclenché l'action
     */
    public void retourMenuPaquet(ActionEvent event){
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
        Optional<String> newPassword = dialog.showAndWait();

        String result = listener.changePassword(newPassword);

        messageLabelProfil.setText(result);
    }

    /**
     * Ce qui s'exécute lorsqu'on clique sur le bouton de déconnexion
     * @param e l'événement qui a déclenché l'action
     */
    @FXML
    public void onDeconnexionButtonclick(ActionEvent e){
        if (listener!=null){
            listener.deconnexion();
        }
    }

}