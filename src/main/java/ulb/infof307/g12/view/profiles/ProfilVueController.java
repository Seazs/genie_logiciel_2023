package ulb.infof307.g12.view.profiles;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import lombok.Setter;
import ulb.infof307.g12.controller.javafx.connexion.MenuPrincipal;
import ulb.infof307.g12.controller.listeners.ProfilVueListener;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class ProfilVueController  {

    @FXML
    private Label messageLabelProfil, pseudoLabel, mdpLabel;

    @Setter
    private ProfilVueListener listener;



    public void retourMenuPaquet(ActionEvent event){
        MenuPrincipal.getINSTANCE().returnToMenuPaquet();
    }


    @FXML
    protected void onChangePasswordButtonClick() {

        TextInputDialog dialog = new TextInputDialog("");

        dialog.setTitle("Change password");
        dialog.setHeaderText("Enter your new password:");
        Optional<String> newPassword = dialog.showAndWait();

        String result = listener.changePassword(newPassword);
        mdpLabel.setText("Mot de passe: " + newPassword.get());

        messageLabelProfil.setText(result);

    }

    @FXML
    public void onDeconnexionButtonclick(ActionEvent e){
        if (listener!=null){
            listener.deconnexion();
        }
    }

    public void setPseudoLabel(String pseudo){
        this.pseudoLabel.setText("Pseudo: " + pseudo);
    }
    public void setMdpLabel(String mdp){
        this.mdpLabel.setText("Mot de passe: " + mdp);
    }

}