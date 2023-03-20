package ulb.infof307.g12.view.profiles;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import lombok.Setter;
import ulb.infof307.g12.controller.listeners.ProfilVueListener;
import ulb.infof307.g12.controller.storage.GestionnaireUtilisateur;
import ulb.infof307.g12.view.connection.ConnectionVueController;
import ulb.infof307.g12.view.paquets.MenuPaquetVueController;

import java.io.IOException;
import java.util.Optional;

public class ProfilVueController {

    @FXML
    private Label messageLabelProfil;

    @Setter
    private ProfilVueListener listener;


    public void retourMenuPaquet(ActionEvent event) throws Exception{

        FXMLLoader loader = new FXMLLoader(MenuPaquetVueController.class.getResource("menuPaquet.fxml"));
        Parent menuPaquet = loader.load();

        // Créer une nouvelle scène à partir de la racine de la nouvelle page
        Scene menuPaquetScene = new Scene(menuPaquet);

        // Obtenir une référence au stage actuel et fermer la fenêtre
        Stage stageActuel = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stageActuel.setScene(menuPaquetScene);
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

    @FXML
    private Button deconnexionButton;
    @FXML
    protected void onDeconnexionButtonClick() throws Exception{
        FXMLLoader loader = new FXMLLoader(ConnectionVueController.class.getResource("connexion-menu-view.fxml"));

        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) deconnexionButton.getScene().getWindow();
        stage.setScene(scene);
        stage.show();

    }

}