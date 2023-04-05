package ulb.infof307.g12.controller.javafx.paquets;

import javafx.stage.Stage;
import lombok.Getter;
import ulb.infof307.g12.controller.javafx.BaseController;
import ulb.infof307.g12.controller.listeners.EditionVueListener;
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
        controller.chargerEditionVue();
    }

    @Override
    public void enregistrerPaquet() {

    }

    public ArrayList<Carte> loadCartes() {
        return paquet.getCartes() ;
    }
}
