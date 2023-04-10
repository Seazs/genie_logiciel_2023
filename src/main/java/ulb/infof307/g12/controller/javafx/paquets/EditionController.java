package ulb.infof307.g12.controller.javafx.paquets;

import javafx.scene.control.Menu;
import javafx.stage.Stage;
import lombok.Getter;
import ulb.infof307.g12.controller.javafx.BaseController;
import ulb.infof307.g12.controller.javafx.connection.MenuPrincipal;
import ulb.infof307.g12.controller.listeners.EditionVueListener;
import ulb.infof307.g12.controller.storage.GestionnairePaquet;
import ulb.infof307.g12.model.Carte;
import ulb.infof307.g12.model.Paquet;
import ulb.infof307.g12.view.paquets.EditionVueController;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class EditionController extends BaseController implements EditionVueListener {

    @Getter
    private Paquet paquet;

    public EditionController(Stage stage, Paquet paquet) throws IOException {
        super(stage, EditionVueController.class.getResource("editionPaquet.fxml"), "");
        System.out.println("Controller oui");
        this.paquet = paquet;
        EditionVueController controller = (EditionVueController) super.controller;
        controller.setListener(this);
        controller.chargerEditionVue(paquet);
    }

    @Override
    public void enregistrerPaquet(String nom, String categorie) throws IOException {
        paquet.setNom(nom);
        paquet.setCategorie(categorie);
        GestionnairePaquet gestionnairepaquet = MenuPrincipal.getINSTANCE().getGestionnairepaquet() ;
        gestionnairepaquet.save(MenuPrincipal.getINSTANCE().getUserPrincipale());

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
