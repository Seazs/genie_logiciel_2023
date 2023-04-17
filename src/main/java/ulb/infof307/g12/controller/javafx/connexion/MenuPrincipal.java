package ulb.infof307.g12.controller.javafx.connexion;

import javafx.application.Application;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;
import ulb.infof307.g12.controller.javafx.paquets.CarteEtudeController;
import ulb.infof307.g12.controller.javafx.BaseController;
import ulb.infof307.g12.controller.javafx.cartes.CarteQCMController;
import ulb.infof307.g12.controller.javafx.cartes.CarteReponseController;
import ulb.infof307.g12.controller.javafx.cartes.CarteTTController;
import ulb.infof307.g12.controller.javafx.exception.ExceptionPopupController;
import ulb.infof307.g12.controller.javafx.paquets.EditionController;
import ulb.infof307.g12.controller.javafx.paquets.MenuPaquetController;
import ulb.infof307.g12.controller.javafx.profiles.ProfilController;
import ulb.infof307.g12.controller.storage.GestionnairePaquet;
import ulb.infof307.g12.controller.storage.GestionnaireUtilisateur;
import ulb.infof307.g12.model.Paquet;
import ulb.infof307.g12.model.Carte;
import ulb.infof307.g12.model.Utilisateur;
import ulb.infof307.g12.view.exception.ExceptionPopupVueController;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Getter
public class MenuPrincipal extends Application {
    @Getter(lazy = true)
    private static final MenuPrincipal INSTANCE = new MenuPrincipal();
    private GestionnaireUtilisateur gestionnaireUtilisateur = new GestionnaireUtilisateur();
    private GestionnairePaquet gestionnairePaquet = new GestionnairePaquet();
    private ConnexionMenuController connexionController;
    private MenuPaquetController menuPaquetController;
    private ProfilController profilController;
    private EditionController editionController;
    private CarteEtudeController carteEtudeController;
    @Setter
    private Utilisateur userPrincipale;
    @Getter
    private List<Paquet> userPaquets;
    private CarteQCMController carteQCMController;
    private CarteTTController carteTTController;
    private ExceptionPopupController exceptionPopupController;
    private CarteReponseController carteReponseController;

    /**
     * Démarrage de l'application
     * @param stage
     * @throws IOException
     */
    @Override
    public void start(Stage stage) throws IOException {
        exceptionPopupController = new ExceptionPopupController(new Stage());
        connexionController = new ConnexionMenuController(stage, gestionnaireUtilisateur);
        connexionController.show();
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        File stockage = new File("./stockage");
        if (!stockage.exists()){
            stockage.mkdir();
        }
        launch();
    }

    /**
     * Affichage du menu du Paquet
     * @param user
     * @param parent
     */
    public void showMenuPaquet(Utilisateur user, ConnexionMenuController parent) {
        try {
            this.userPrincipale = user;
            userPaquets = user.getListPaquet();
            menuPaquetController = new MenuPaquetController(user,new Stage());
            parent.hide();
            menuPaquetController.show();
        } catch (IOException e) {
            e.printStackTrace();
            showErrorPopup("Impossible de charger les paquets !");
        }
    }

    /**
     * Affichage du menu de connexion
     * @param parent
     */
    public void showConnexionMenu(ProfilController parent){
        try {
            parent.hide();
            Stage stage = new Stage();
            connexionController = new ConnexionMenuController(stage, gestionnaireUtilisateur);
            connexionController.show();
        } catch (IOException e) {
            e.printStackTrace();
            showErrorPopup("Un problème est survenu lors du chargement du menu de connexion.");
        }

    }

    /**
     * Affichage de l'étude de carte
     * @param parent
     * @param paquet
     */
    public void showCarteEtude(MenuPaquetController parent,Paquet paquet){
        try{
            parent.hide();
            Stage stage = new Stage();
            carteEtudeController = new CarteEtudeController(stage,paquet);
            carteEtudeController.show();
        }catch (IOException e){
            e.printStackTrace();
            showErrorPopup("Impossible de lancer une session d'étude !");
        }
    }

    /**
     * Ouverture du profil
     */
    public void openProfile(){
        profilController = null;
        try {
            profilController = new ProfilController(new Stage(),userPrincipale);
            menuPaquetController.hide();
            profilController.show();
        } catch (IOException e) {
            e.printStackTrace();
            showErrorPopup("Impossible de charger le profil !");
        }
    }

    /**
     * Retour au menu Paquet
     */
    public void returnToMenuPaquet() {
        profilController.hide();
        menuPaquetController.show();
    }

    /**
     * Retour au menu Paquet depuis l'édition de ce dernier
     */
    public void returnFromEditionToMenuPaquet() {
        menuPaquetController.show();
        editionController.hide();
    }

    /**
     * Retour à l'étude de cartes depuis le menu du Paquet
     */
    public void returnFromCarteEtudeToMenuPaquet() {
        menuPaquetController.show();
        carteEtudeController.hide();
    }

    /**
     * Retour au menu de connexion
     */
    public void returnToConnexionMenu() {
        profilController.hide();
        connexionController.show();
    }

    /**
     * Affichage des cartes de type QCM
     * @param card
     */
    public void showCarteQCM(Carte card) {
        try {
            carteQCMController = new CarteQCMController(new Stage(),"Title",card);
            menuPaquetController.hide();
            carteQCMController.show();
        } catch (IOException e) {
            e.printStackTrace();
            showErrorPopup("Impossible de charger la carte QCM !");
        }

    }

    /**
     * Affichage des cartes de type texte à trou
     * @param card
     */
    public void showCarteTT(Carte card) {
        try{
        carteTTController = new CarteTTController(new Stage(),"",card);
        menuPaquetController.hide();
        carteTTController.show();
        } catch (IOException e) {
            e.printStackTrace();
            showErrorPopup("Impossible de charger la carte Texte à Trou !");
        }
    }


    /**
     * Affichage de la réponse
     * @param userReponse
     * @param rightAnswer
     * @param controller
     */
    private void showResponse(String userReponse, String rightAnswer, BaseController controller) {
        try {
            controller.hide();
            carteReponseController = new CarteReponseController(new Stage(),"title",userReponse,rightAnswer);
            carteReponseController.show();
        } catch (IOException e) {
            e.printStackTrace();
            showErrorPopup("Impossible d'afficher la réponse !");
        }

    }

    /**
     * Affichage de la réponse des cartes QCM
     * @param userReponse
     * @param rightAnswer
     */
    public void showQCMResponse(String userReponse, String rightAnswer){
        showResponse(userReponse,rightAnswer,carteQCMController);
    }

    /**
     * Affichage de la réponse des cartes textes à trous
     * @param userReponse
     * @param rightAnswer
     */
    public void showTTResponse(String userReponse, String rightAnswer){
        showResponse(userReponse,rightAnswer,carteTTController);
    }


    /**
     * Affichage du menu d'édition
     * @param paquet
     */
    public void showMenuEdition(Paquet paquet) {
        try{
            editionController = new EditionController(new Stage(),paquet);
            menuPaquetController.hide();
            editionController.show();
        } catch (IOException e) {
            e.printStackTrace();
            showErrorPopup("Impossible de charger le menu d'édition !");
        }

    }

    /**
     * @param error
     */
    public void showErrorPopup(String error){
        exceptionPopupController.createError(error);
    }


}