package ulb.infof307.g12.controller.javafx.connexion;

import javafx.application.Application;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;
import ulb.infof307.g12.controller.javafx.BaseController;
import ulb.infof307.g12.controller.javafx.cartes.CarteQCMController;
import ulb.infof307.g12.controller.javafx.cartes.CarteReponseController;
import ulb.infof307.g12.controller.javafx.cartes.CarteTTController;
import ulb.infof307.g12.controller.javafx.paquets.EditionController;
import ulb.infof307.g12.controller.javafx.paquets.MenuPaquetController;
import ulb.infof307.g12.controller.javafx.profiles.ProfilController;
import ulb.infof307.g12.controller.storage.GestionnairePaquet;
import ulb.infof307.g12.controller.storage.GestionnaireUtilisateur;
import ulb.infof307.g12.model.Paquet;
import ulb.infof307.g12.model.Carte;
import ulb.infof307.g12.model.Utilisateur;

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
    @Setter
    private Utilisateur userPrincipale;
    @Getter
    private List<Paquet> userPaquets;
    private CarteQCMController carteQCMController;
    private CarteTTController carteTTController;
    @Override
    public void start(Stage stage) throws IOException {
        connexionController = new ConnexionMenuController(stage, gestionnaireUtilisateur);
        connexionController.show();
    }

    public static void main(String[] args) {
        File stockage = new File("./stockage");
        if (!stockage.exists()){
            stockage.mkdir();
        }
        launch();
    }

    public void showMenuPaquet(Utilisateur user, ConnexionMenuController parent) {
        try {
            this.userPrincipale = user;
            userPaquets = user.getListPaquet();
            menuPaquetController = new MenuPaquetController(user,new Stage());
            parent.hide();
            menuPaquetController.show();
        } catch (IOException e) {
            System.out.print(e);
            //TODO: Avertir l'utilisateur
        }
    }
    public void showConnexionMenu(ProfilController parent){
        try {
            parent.hide();
            Stage stage = new Stage();
            connexionController = new ConnexionMenuController(stage, gestionnaireUtilisateur);
            connexionController.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void openProfile(){
        profilController = null;
        try {
            profilController = new ProfilController(new Stage(),userPrincipale);
            menuPaquetController.hide();
            profilController.show();
        } catch (IOException e) {
            //TODO: Renvoyer l'erreur à l'utilisateur
        }
    }

    public void returnToMenuPaquet() {
        profilController.hide();
        menuPaquetController.show();
    }

    public void returnFromEditionToMenuPaquet() {
        menuPaquetController.show();
        editionController.hide();
    }

    public void returnToConnexionMenu() {
        profilController.hide();
        connexionController.show();
    }
    public void showCarteQCM(Carte card) throws IOException {
        carteQCMController = new CarteQCMController(new Stage(),"Title",card);
        menuPaquetController.hide();
        carteQCMController.show();
    }

    public void showCarteTT(Carte card) throws IOException {
        carteTTController = new CarteTTController(new Stage(),"",card);
        menuPaquetController.hide();
        carteTTController.show();
    }


    private void showResponse(String userReponse, String rightAnswer, BaseController controller) {
        try {
            controller.hide();
            CarteReponseController carteReponseController = new CarteReponseController(new Stage(),"title",userReponse,rightAnswer);
            carteReponseController.show();
        } catch (IOException e) {
            //TODO:erreur show Response
            e.printStackTrace();
        }

    }

    public void showQCMResponse(String userReponse, String rightAnswer){
        showResponse(userReponse,rightAnswer,carteQCMController);
    }

    public void showTTResponse(String userReponse, String rightAnswer){
        showResponse(userReponse,rightAnswer,carteTTController);
    }


    public void showMenuEdition(Paquet paquet) {
        try{
            editionController = new EditionController(new Stage(),paquet);
            menuPaquetController.hide();
            editionController.show();
        } catch (IOException e) {
            //TODO: Renvoyer l'erreur à l'utilisateur
            System.out.println(e);
        }

    }

}