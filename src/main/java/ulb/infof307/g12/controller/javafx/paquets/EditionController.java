package ulb.infof307.g12.controller.javafx.paquets;

import javafx.stage.Stage;
import lombok.Getter;
import ulb.infof307.g12.controller.javafx.BaseController;
import ulb.infof307.g12.controller.javafx.connexion.MenuPrincipal;
import ulb.infof307.g12.controller.listeners.EditionVueListener;
import ulb.infof307.g12.controller.storage.GestionnairePaquet;
import ulb.infof307.g12.model.Carte;
import ulb.infof307.g12.model.Paquet;
import ulb.infof307.g12.model.Utilisateur;
import ulb.infof307.g12.view.paquets.EditionVueController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EditionController extends BaseController implements EditionVueListener {

    @Getter
    private Paquet paquet;

    public EditionController(Stage stage, Paquet paquet) throws IOException {
        super(stage, EditionVueController.class.getResource("editionPaquet.fxml"), "");
        this.paquet = paquet;
        EditionVueController controller = (EditionVueController) super.controller;
        controller.setListener(this);
        controller.chargerEditionVue(paquet);
    }

    @Override
    public void enregistrerPaquet(String nom, String categorie) throws IOException {
        paquet.setNom(nom);
        paquet.setCategorie(categorie);
        GestionnairePaquet gestionnairePaquet = MenuPrincipal.getINSTANCE().getGestionnairePaquet() ;
        Utilisateur user = MenuPrincipal.getINSTANCE().getUserPrincipale();
        List<Paquet> listePaquetUser = user.getListPaquet();
        GestionnairePaquet.save(MenuPrincipal.getINSTANCE().getUserPrincipale());
    }

    public ArrayList<Carte> loadCartes() {
        return paquet.getCartes() ;
    }

    public void ajouterCarte(String recto, String verso){
        int id = paquet.getCartes().size() + 1 ;
        Carte carte = new Carte(id, recto, verso) ;
        paquet.ajouterCarte(carte);
    }

}
