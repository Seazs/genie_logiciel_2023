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

    private final GestionnaireUtilisateur gestionnaireUtilisateur;

    /**
     * Controller du menu de connexion
     * @param stage stage
     * @param gestionnaireUtilisateur gestionnaire d'utilisateur
     * @throws IOException exception
     */
    public ConnexionMenuController(Stage stage,GestionnaireUtilisateur gestionnaireUtilisateur) throws IOException {
        super(stage, ConnexionVueController.class.getResource("connexion-menu-view.fxml"),"Application Title");
        this.gestionnaireUtilisateur = gestionnaireUtilisateur;

        ConnexionVueController controller = (ConnexionVueController) super.controller;
        controller.setListener(this);
    }

    /**
     * Lorsqu'on s'enregistre
     * @param username username
     * @param password mot de passe
     * @return le statut
     */
    @Override
    public String onRegister(String username, String password) {

        String result = "";

        try {
            if (gestionnaireUtilisateur.register(username, password)) { // Si l'enregistrement s'est bien passé
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

    /**
     * Lorsqu'on se connecte
     * @param username username
     * @param password mot de passe
     * @return le statut
     */
    @Override
    public String onLogin(String username, String password, boolean isOnline) {
        String result = "";

        try {
            //TODO : se connecter de deux différentes manières (en ligne et hors ligne)
            if (gestionnaireUtilisateur.connect(username, password)) { // Si la connexion s'est bien passée
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