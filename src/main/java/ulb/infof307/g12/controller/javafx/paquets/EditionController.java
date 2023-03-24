package ulb.infof307.g12.controller.javafx.paquets;

import javafx.stage.Stage;
import ulb.infof307.g12.controller.javafx.BaseController;
import ulb.infof307.g12.controller.listeners.EditionVueListener;
import ulb.infof307.g12.model.Paquet;
import ulb.infof307.g12.view.paquets.EditionVueController;

import java.io.IOException;
import java.net.URL;

public class EditionController extends BaseController implements EditionVueListener {

    private Paquet paquet;

    public EditionController(Stage stage, Paquet paquet) throws IOException {
        super(stage, EditionVueController.class.getResource("editionPaquet.fxml"), "");
        this.paquet = paquet;

        EditionVueController controller = (EditionVueController) super.controller;
        controller.setListener(this);
    }

    @Override
    public void enregistrerPaquet() {

    }
}
