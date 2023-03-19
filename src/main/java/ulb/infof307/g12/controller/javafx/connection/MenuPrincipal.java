package ulb.infof307.g12.controller.javafx.connection;

import javafx.application.Application;
import javafx.stage.Stage;
import lombok.Getter;
import ulb.infof307.g12.controller.javafx.paquets.MenuPaquetController;
import ulb.infof307.g12.controller.javafx.profiles.ProfilController;
import ulb.infof307.g12.controller.storage.GestionnaireUtilisateur;
import ulb.infof307.g12.model.Utilisateur;

import java.io.File;
import java.io.IOException;

public class MenuPrincipal extends Application implements ConnexionMenuController.ConnexionSuccessListener {

    @Getter(lazy = true)
    private static final MenuPrincipal INSTANCE = new MenuPrincipal();
    @Getter
    private GestionnaireUtilisateur gestionnaireUtilisateur = new GestionnaireUtilisateur();



    @Override
    public void start(Stage stage) throws IOException {
        ConnexionMenuController connexionController = new ConnexionMenuController(stage, gestionnaireUtilisateur, this);

        connexionController.show();

    }

    public static void main(String[] args) {
        File stockage = new File("./stockage");
        if (!stockage.exists()){
            stockage.mkdir();
        }
        launch();
    }

    @Override
    public void connect(Utilisateur user,ConnexionMenuController parent) {
        try {
            MenuPaquetController menuPaquetController = new MenuPaquetController(user,new Stage());
            parent.hide();
            menuPaquetController.show();
        } catch (IOException e) {
            //TODO: Avertir l'utilisateur
        }
    }
}
