package ulb.infof307.g12.controller.javafx.connexion;

import javafx.stage.Stage;
import ulb.infof307.g12.controller.javafx.BaseController;
import ulb.infof307.g12.view.listeners.UserCredentialsListener;
import ulb.infof307.g12.controller.storage.GestionnairePaquet;
import ulb.infof307.g12.controller.storage.GestionnaireUtilisateur;
import ulb.infof307.g12.model.STATUS;
import ulb.infof307.g12.model.Utilisateur;
import ulb.infof307.g12.view.connexion.ConnexionVueController;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Objects;

public class ConnexionMenuController extends BaseController implements UserCredentialsListener {

    private final GestionnaireUtilisateur gestionnaireUtilisateur;

    /**
     * Controller du menu de connexion
     * @param stage stage
     * @param gestionnaireUtilisateur gestionnaire d'utilisateur
     * @throws IOException exception
     */
    public ConnexionMenuController(Stage stage,GestionnaireUtilisateur gestionnaireUtilisateur) throws IOException{
        super(stage, ConnexionVueController.class.getResource("connexion-menu-view.fxml"),"Recto? Verso!");
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
    public String onRegister(String username, String password,boolean isOnline) {
        MenuPrincipal.getINSTANCE().setOnline(isOnline);
        return isOnline ? onlineRegister(username,password) : offlineRegister(username,password);
    }

    /**
     * création d'un utilisateur en local
     * @param username pseudo
     * @param password mot de passe
     * @return le statut
     */
    private String offlineRegister(String username, String password){
        String result = "";

        try {
            if (gestionnaireUtilisateur.register(username, password)) { // Si l'enregistrement s'est bien passé
                result = username + " registered with success.";
            } else {
                result = gestionnaireUtilisateur.getStatusMsg();
            }
        } catch (IOException e) {
            MenuPrincipal.getINSTANCE().showErrorPopup("Impossible d'enregistrer le nouvel utilisateur !");
        }

        return result;
    }

    private String onlineRegister(String username, String password){
        String result = MenuPrincipal.getINSTANCE().getServer().createUser(username,password);
        offlineRegister(username, password);
        return result;
    }

    /**
     * Lorsqu'on se connecte
     * @param username username
     * @param password mot de passe
     * @param isOnline si la connexion se fait en ligne
     * @return le statut
     */
    @Override
    public String onLogin(String username, String password, boolean isOnline) {
        MenuPrincipal.getINSTANCE().setOnline(isOnline);
        return (isOnline) ? onlineLogin(username,password) : offlineLogin(username,password);
    }

    /**
     * Lorsqu'on se connecte de façon hors-ligne
     * @See onLogin
     * @param username nom d'utilisateur
     * @param password mot de passe
     * @return le statut
     */
    private String offlineLogin(String username, String password){
        String result = "";
        try {
            if (gestionnaireUtilisateur.connect(username, password)) { // Si la connexion s'est bien passée
                result = "Connecting: " + username;

                Utilisateur connectedUser = new Utilisateur(username,password);
                GestionnairePaquet gestionnairePaquet = MenuPrincipal.getINSTANCE().getGestionnairePaquet();
                connectedUser.setListPaquet(gestionnairePaquet.load(connectedUser));
                MenuPrincipal.getINSTANCE().showMenuPaquet(connectedUser,this);
            } else {
                result = gestionnaireUtilisateur.getStatusMsg();
            }
        } catch (FileNotFoundException e) {
            MenuPrincipal.getINSTANCE().showErrorPopup("Impossible de retrouver les informations de connexion !");
        }

        return result;
    }

    /**
     * Lorsque l'utilisateur se connecte au serveur
     * @See onLogin
     * @param username nom d'utilisateur
     * @param password mot de passe
     * @return le statut
     */
    private String onlineLogin(String username, String password){
        try{
            STATUS result = MenuPrincipal.getINSTANCE().getServer().getLogin(username,password);
            if (Objects.equals(result.getMsg(), "")) {
                offlineLogin(username, password);
            }
            return result.getMsg();
        }catch (Exception e){
            MenuPrincipal.getINSTANCE().showErrorPopup("Impossible de se connecter au serveur !");
            return "Server Not Found";
        }


    }

}