package ulb.infof307.g12.controller.javafx.connexion;

import javafx.application.Application;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;
import ulb.infof307.g12.controller.javafx.BaseController;
import ulb.infof307.g12.controller.javafx.paquets.CardStudyController;
import ulb.infof307.g12.controller.javafx.profiles.ProfileController;
import ulb.infof307.g12.controller.javafx.synchronisation.SyncController;
import ulb.infof307.g12.controller.javafx.exception.ExceptionPopupController;
import ulb.infof307.g12.controller.javafx.paquets.EditionController;
import ulb.infof307.g12.controller.javafx.paquets.MenuPaquetController;
import ulb.infof307.g12.controller.javafx.store.StoreController;
import ulb.infof307.g12.controller.storage.PaquetManager;
import ulb.infof307.g12.controller.storage.UserManager;
import ulb.infof307.g12.model.*;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Getter
public class MenuPrincipal extends Application {
    @Getter(lazy = true)
    private static final MenuPrincipal INSTANCE = new MenuPrincipal();
    private final UserManager userManager = new UserManager();
    private final PaquetManager paquetManager = new PaquetManager();
    private ConnexionMenuController connexionController;
    private MenuPaquetController menuPaquetController;
    private ProfileController profilController;
    private EditionController editionController;
    private CardStudyController carteEtudeController;
    private StoreController storeController;
    @Setter
    private User principalUser;
    private ExceptionPopupController exceptionPopupController;

    private SyncController syncController;
    @Getter
    private final Server server = new Server();
    @Setter
    private boolean isOnline;


    /**
     * Démarrage de l'application
     * @param stage stage
     * @throws IOException exception
     */
    @Override
    public void start(Stage stage) throws IOException {
        this.connexionController = new ConnexionMenuController(stage, userManager);
        exceptionPopupController = new ExceptionPopupController(new Stage());
        connexionController.show();
    }

    /**
     * @param args arguments
     */
    public static void main(String[] args) {
        File stockage = new File("./src/main/resources/stockage");
        if (!stockage.exists()){
            stockage.mkdir();
        }
        launch();
    }

    /**
     * Affichage du menu du Paquet
     * @param user utilisateur
     * @param parent parent
     */
    public void showMenuPaquet(User user, ConnexionMenuController parent) {
        try {
            this.principalUser = user;
            menuPaquetController = new MenuPaquetController(user,new Stage());
            parent.hide();
            menuPaquetController.show();
        } catch (IOException e) {
            showErrorPopup("Impossible de charger les paquets !");
        }
    }

    /**
     * Affichage du menu de connexion
     * @param parent parent
     */
    public void showConnexionMenu(ProfileController parent){
        try {
            parent.hide();
            Stage stage = new Stage();
            connexionController = new ConnexionMenuController(stage, userManager);
            connexionController.show();
        } catch (IOException e) {
            showErrorPopup("Un problème est survenu lors du chargement du menu de connexion.");
        }

    }

    /**
     * Affichage de l'étude de carte
     * @param parent parent
     * @param paquet paquet
     */
    public void showCardStudy(MenuPaquetController parent, Paquet paquet){
        try{
            parent.hide();
            Stage stage = new Stage();
            carteEtudeController = new CardStudyController(stage,paquet);
            carteEtudeController.show();
        }catch (IOException e){
            showErrorPopup("Impossible de lancer une session d'étude !");
        }
    }

    /**
     * Ouverture du profil
     */
    public void openProfile(){
        profilController = null;
        try {
            profilController = new ProfileController(new Stage(), principalUser);
            menuPaquetController.hide();
            profilController.show();
        } catch (IOException e) {
            showErrorPopup("Impossible de charger le profil !");
        }
    }

    /**
     * Affichage du store
     */
    public void openStore(){
        if (isOnline) {
            try {
                storeController = new StoreController(new Stage());
                menuPaquetController.hide();
                storeController.show();
            } catch (IOException e) {
                showErrorPopup("Impossible de charger le store !");
            }
        }
        else showErrorPopup("Vous n’êtes pas connecté au serveur !");

    }


    /**
     * Passe d'une ancienne vue à une nouvelle vue
     * @param oldView ancienne vue
     * @param newView nouvelle vue
     */
    public void changeView(BaseController oldView, BaseController newView){
        oldView.hide();
        newView.show();
    }
    /**
     * Retour au menu Paquet
     */
    public void returnToMenuPaquet() {
        changeView(profilController,menuPaquetController);
    }

    /**
     * Retour au menu Paquet depuis l'édition de ce dernier
     */
    public void returnFromEditionToMenuPaquet() {

        changeView(editionController,menuPaquetController);
        menuPaquetController.updatePaquets();
    }

    /**
     * Retour à l'étude de cartes depuis le menu du Paquet
     */
    public void returnFromCardStudyToMenuPaquet() {
        changeView(carteEtudeController,menuPaquetController);
    }

    /**
     * Affichage de l'édition de carte
     */
    public void returnFromStoreToMenuPaquet() {
        changeView(storeController,menuPaquetController);
    }



    /**
     * Affichage du menu d'édition
     * @param paquet paquet
     */
    public void showMenuEdition(Paquet paquet) {
        try{
            editionController = new EditionController(new Stage(),paquet);
            changeView(menuPaquetController,editionController);
        } catch (IOException e) {
            showErrorPopup("Impossible de charger le menu d'édition !");
        } catch (IllegalArgumentException e){
            showErrorPopup("Veuillez sélectionner un paquet !");
        }

    }

    /**
     * Fonction qui ouvre une fenêtre avec un pop up d'erreur
     * @param error erreur String
     */
    public void showErrorPopup(String error){
        try {
            exceptionPopupController = new ExceptionPopupController(new Stage());
            exceptionPopupController.createError(error);
        } catch (IOException e) {
        }
    }

    public List<Paquet> getUserPaquets() {
        return Collections.unmodifiableList(principalUser.getListPaquet());
    }

    public void showSyncMenu() {
        if (isOnline) {
            try {
                syncController = new SyncController(new Stage());
                syncController.show();
            } catch (IOException e) {
                showErrorPopup("Impossible de charger le menu de synchronisation !");
            }
        }
        else showErrorPopup("Vous n’êtes pas connecté au serveur !");
    }
    public void setUserPaquets(List<Paquet> paquets) {
        principalUser.setListPaquet(paquets);
    }
    public void reloadPaquets() {
        try {
            menuPaquetController.updatePaquets();
            paquetManager.save(principalUser);
        } catch (IOException e) {
            showErrorPopup("Impossible de sauvegarder les paquets !");
        }
    }
}