package ulb.infof307.g12.view.profiles;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;
import ulb.infof307.g12.controller.storage.GestionnaireUtilisateur;
import ulb.infof307.g12.view.paquets.MenuPaquetController;

import java.io.IOException;
import java.util.Optional;

public class ProfilController {

    @FXML
    private Label messageLabelProfil;

    public void retourMenuPaquet(ActionEvent event) throws Exception{

        FXMLLoader loader = new FXMLLoader(MenuPaquetController.class.getResource("menuPaquet.fxml"));
        Parent menuPaquet = loader.load();

        // Créer une nouvelle scène à partir de la racine de la nouvelle page
        Scene menuPaquetScene = new Scene(menuPaquet);

        // Obtenir une référence au stage actuel et fermer la fenêtre
        Stage stageActuel = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stageActuel.setScene(menuPaquetScene);
    }


    @FXML
    protected void onChangePasswordButtonClick() throws IOException {
        String username = GestionnaireUtilisateur.utilisateurConnected.getPseudo() ;
        String oldPassword = GestionnaireUtilisateur.utilisateurConnected.getMdp() ;
        TextInputDialog dialog = new TextInputDialog("");
        dialog.setTitle("Change password");
        dialog.setHeaderText("Enter your new password:");
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            String newPassword = result.get();
            GestionnaireUtilisateur gestionnaire = new GestionnaireUtilisateur();
            if (gestionnaire.modifierMotDePasse(username, newPassword, oldPassword)) {
                messageLabelProfil.setText("Password changed successfully.");
            } else {
                switch (gestionnaire.getStatus()) {
                    case USERNAME_DOES_NOT_EXIST ->
                            messageLabelProfil.setText("Username does not exist. Please try again.");
                    case WRONG_PASSWORD ->
                            messageLabelProfil.setText("Wrong password. Please try again.");
                }
            }
        }
    }
}