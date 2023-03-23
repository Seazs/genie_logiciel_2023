package ulb.infof307.g12.controller.javafx.connection;

import javafx.stage.Stage;
import ulb.infof307.g12.controller.javafx.BaseController;
import ulb.infof307.g12.controller.listeners.UserCredentialsListener;
import ulb.infof307.g12.controller.storage.GestionnaireUtilisateur;
import ulb.infof307.g12.model.Utilisateur;
import ulb.infof307.g12.view.connection.ConnectionVueController;

import java.io.FileNotFoundException;
import java.io.IOException;

public class ConnexionMenuController extends BaseController implements UserCredentialsListener {

    private GestionnaireUtilisateur gestionnaire;

    public ConnexionMenuController(Stage stage,GestionnaireUtilisateur gestionnaireUtilisateur) throws IOException {
        super(stage,ConnectionVueController.class.getResource("connexion-menu-view.fxml"),"Application Title");
        gestionnaire = gestionnaireUtilisateur;

        ConnectionVueController controller = (ConnectionVueController) super.controller;
        controller.setListener(this);
    }

    @Override
    public String onRegister(String username, String password) {

        String result = "";

        try {
            if (gestionnaire.register(username, password)) {
                result = "Register: " + username + " = " + password;
            } else {
                result = gestionnaire.getStatusMsg();
            }
        } catch (IOException e) {
            //
        }

        return result;

    }

    @Override
    public String onLogin(String username, String password) {
        String result = "";

        try {
            if (gestionnaire.connect(username, password)) {
                result = "Connecting: " + username + " = " + password;

                Utilisateur connectedUser = new Utilisateur(username,password);
                MenuPrincipal.getINSTANCE().showMenuPaquet(connectedUser,this);
            } else {
                result = gestionnaire.getStatusMsg();
            }
        } catch (FileNotFoundException e) {
            //
        }

        return result;
    }


}