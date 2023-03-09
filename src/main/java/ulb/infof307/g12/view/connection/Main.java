package ulb.infof307.g12.view.connection;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ulb.infof307.g12.controller.storage.GestionnairePaquet;
import ulb.infof307.g12.model.Carte;
import ulb.infof307.g12.model.Paquet;
import ulb.infof307.g12.view.paquets.MenuPaquetController;

import java.io.File;
import java.io.IOException;

public class Main extends Application {
    private static String[] args;

    /*
        public static void main(String[] args) throws IOException {
            GestionnaireUtilisateur gestionnaire = new GestionnaireUtilisateur();
            Utilisateur user1 = new Utilisateur("Brenno", "brebre");
            Utilisateur user2 = new Utilisateur("Ismail", "isis");
            gestionnaire.add(user1);
            gestionnaire.add(user2);
            gestionnaire.save();
        }*/


    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("connexion-menu-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Application Title");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}
