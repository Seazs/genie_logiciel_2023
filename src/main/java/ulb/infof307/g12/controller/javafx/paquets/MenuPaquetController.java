package ulb.infof307.g12.controller.javafx.paquets;

import javafx.stage.Stage;
import lombok.Getter;
import ulb.infof307.g12.controller.javafx.BaseController;
import ulb.infof307.g12.controller.javafx.connexion.MenuPrincipal;
import ulb.infof307.g12.controller.listeners.MenuPaquetListener;
import ulb.infof307.g12.model.Paquet;
import ulb.infof307.g12.model.Utilisateur;
import ulb.infof307.g12.view.paquets.MenuPaquetVueController;


import java.io.IOException;

public class MenuPaquetController extends BaseController implements MenuPaquetListener {

    @Getter
    private Utilisateur user;

    public MenuPaquetController(Utilisateur user,Stage stage) throws IOException {
        super(stage,MenuPaquetVueController.class.getResource("menuPaquet.fxml"),"");
        this.user = user;
        MenuPaquetVueController controller = (MenuPaquetVueController) super.controller;
        controller.setListener(this);
    }
    @Override
    public void openPaquet(Paquet paquet) {
        //TODO: faire le paquet
    }

    /**
     * Création d'un nouveau paquet vide
     * @return Paquet vide avec un nom et catégorie génériques
     */
    @Override
    public Paquet creerPaquet() {
        // Créer le paquet et l'ajouter à la liste de paquet de l'utilisateur
        Paquet nouveauPaquet = new Paquet("Nouveau Paquet") ;
        user.addPaquet(nouveauPaquet);
        return nouveauPaquet;
    }

    /**
     * Lancer le menu d'édition avec le paquet choisit par l'utilisateur
     * @param paquet Paquet choisit par l'utilisateur à être modifié
     */
    @Override
    public void editerPaquet(Paquet paquet) {
        MenuPrincipal.getINSTANCE().showMenuEdition(paquet);
    }

    @Override
    public void CarteEtude(Paquet paquet){
        MenuPrincipal instance = MenuPrincipal.getINSTANCE();
        instance.showCarteEtude(this,paquet);
    }


}
