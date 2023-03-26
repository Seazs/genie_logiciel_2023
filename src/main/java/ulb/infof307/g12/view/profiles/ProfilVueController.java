package ulb.infof307.g12.view.profiles;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;
import lombok.Setter;
import ulb.infof307.g12.controller.javafx.connection.MenuPrincipal;
import ulb.infof307.g12.controller.listeners.ProfilVueListener;
import ulb.infof307.g12.controller.storage.GestionnaireUtilisateur;
import ulb.infof307.g12.view.paquets.MenuPaquetVueController;

import java.io.IOException;
import java.util.Optional;

public class ProfilVueController {

    @FXML
    private Label messageLabelProfil;

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

        messageLabelProfil.setText(result);
    }

}