package ulb.infof307.g12.controller.javafx.connexion;

import javafx.stage.Stage;
import ulb.infof307.g12.controller.javafx.BaseController;
import ulb.infof307.g12.controller.storage.PaquetManager;
import ulb.infof307.g12.controller.storage.UserManager;
import ulb.infof307.g12.view.connexion.ConnexionViewController;
import ulb.infof307.g12.view.listeners.UserCredentialsListener;
import ulb.infof307.g12.model.STATUS;
import ulb.infof307.g12.model.User;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Objects;

public class ConnexionMenuController extends BaseController implements UserCredentialsListener {

    private final UserManager userManager;

    /**
     * Controller du menu de connexion
     * @param stage stage
     * @param userManager gestionnaire d'utilisateur
     * @throws IOException exception
     */
    public ConnexionMenuController(Stage stage, UserManager userManager) throws IOException{
        super(stage, ConnexionViewController.class.getResource("connexion-menu-view.fxml"),"Recto? Verso!");
        this.userManager = userManager;

        ConnexionViewController controller = (ConnexionViewController) super.controller;
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
     * Création d'un utilisateur en local
     * @param username pseudo
     * @param password mot de passe
     * @return le statut
     */
    private String offlineRegister(String username, String password){
        String result = "";

        try {
            if (userManager.register(username, password)) { // Si l'enregistrement s'est bien passé
                result = username + " registered with success.";
            } else {
                result = userManager.getStatusMsg();
            }
        } catch (IOException e) {
            MenuPrincipal.getINSTANCE().showErrorPopup("Impossible d'enregistrer le nouvel utilisateur !");
        }

        return result;
    }

    /**
     * Création d'un utilisateur en ligne
     * @param username username de l'utilisateur
     * @param password mot de passe de l'utilisateur
     * @return le statut de la création
     */
    private String onlineRegister(String username, String password){
        String result = MenuPrincipal.getINSTANCE().getServer().createUser(username,password);
        if (STATUS.valueOf(result).equals(STATUS.SERVER_CREATION_ERROR)){
            MenuPrincipal.getINSTANCE().showErrorPopup("Le pseudo existe déjà!");
        }else if (STATUS.valueOf(result).equals(STATUS.SERVER_CONNEXION_ERROR)){
            MenuPrincipal.getINSTANCE().showErrorPopup("Impossible de se connecter au serveur !");
        }
        offlineRegister(username, password);
        return STATUS.valueOf(result).getMsg();
    }

    /**
     * Lorsqu'on se connecte
     * @param username username de l'utilisateur
     * @param password mot de passe de l'utilisateur
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
     * @param username nom d'utilisateur
     * @param password mot de passe
     * @return le statut
     */
    private String offlineLogin(String username, String password){
        String result = "";
        try {
            if (userManager.connect(username, password)) { // Si la connexion s'est bien passée
                result = "Connecting: " + username;

                User connectedUser = new User(username,password);
                PaquetManager paquetManager = MenuPrincipal.getINSTANCE().getPaquetManager();
                connectedUser.setListPaquet(paquetManager.load(connectedUser));
                MenuPrincipal.getINSTANCE().showMenuPaquet(connectedUser,this);
            } else {
                result = userManager.getStatusMsg();
            }
        } catch (FileNotFoundException e) {
            MenuPrincipal.getINSTANCE().showErrorPopup("Impossible de retrouver les informations de connexion !");
        }
        return result;
    }

    /**
     * Lorsque l'utilisateur se connecte au serveur
     * @param username nom d'utilisateur
     * @param password mot de passe
     * @return le statut
     */
    private String onlineLogin(String username, String password){
        try{
            STATUS result = MenuPrincipal.getINSTANCE().getServer().getLogin(username,password);
            if (Objects.equals(result.getMsg(), "OK")) {
                offlineLogin(username, password);
            }
            if (result == STATUS.SERVER_CONNEXION_ERROR){
                return "Erreur lors de la connexion au serveur";
            }
            return result.getMsg();
        }catch (Exception e){
            MenuPrincipal.getINSTANCE().showErrorPopup("Impossible de se connecter au serveur !");
            return "Server Not Found";
        }
    }
}