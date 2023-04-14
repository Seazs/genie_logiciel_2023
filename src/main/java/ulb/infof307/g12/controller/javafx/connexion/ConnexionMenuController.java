package ulb.infof307.g12.controller.javafx.connexion;

import javafx.stage.Stage;
import ulb.infof307.g12.controller.javafx.BaseController;
import ulb.infof307.g12.controller.listeners.UserCredentialsListener;
import ulb.infof307.g12.controller.storage.GestionnairePaquet;
import ulb.infof307.g12.controller.storage.GestionnaireUtilisateur;
import ulb.infof307.g12.model.Utilisateur;
import ulb.infof307.g12.view.connexion.ConnexionVueController;

import java.io.FileNotFoundException;
import java.io.IOException;

public class ConnexionMenuController extends BaseController implements UserCredentialsListener {

    private GestionnaireUtilisateur gestionnaireUtilisateur;

    public ConnexionMenuController(Stage stage,GestionnaireUtilisateur gestionnaireUtilisateur) throws IOException {
        super(stage, ConnexionVueController.class.getResource("connexion-menu-view.fxml"),"Application Title");
        this.gestionnaireUtilisateur = gestionnaireUtilisateur;

        ConnexionVueController controller = (ConnexionVueController) super.controller;
        controller.setListener(this);
    }

    @Override
    public String onRegister(String username, String password) {

        String result = "";

        try {
            if (gestionnaireUtilisateur.register(username, password)) {
                result = "Register: " + username + " = " + password;
            } else {
                result = gestionnaireUtilisateur.getStatusMsg();
            }
        } catch (IOException e) {
            e.printStackTrace();
            MenuPrincipal.getINSTANCE().showErrorPopup("Impossible d'enregistrer le nouvel utilisateur !");
        }

        return result;

    }

    @Override
    public String onLogin(String username, String password) {
        String result = "";

        try {
            if (gestionnaireUtilisateur.connect(username, password)) {
                result = "Connecting: " + username + " = " + password;

                Utilisateur connectedUser = new Utilisateur(username,password);
                GestionnairePaquet gestionnairePaquet = MenuPrincipal.getINSTANCE().getGestionnairePaquet();
                connectedUser.setListPaquet(gestionnairePaquet.load(connectedUser));
                MenuPrincipal.getINSTANCE().showMenuPaquet(connectedUser,this);
            } else {
                result = gestionnaireUtilisateur.getStatusMsg();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            MenuPrincipal.getINSTANCE().showErrorPopup("Impossible de retrouver les informations de connexion !");
        }

        return result;
    }


}