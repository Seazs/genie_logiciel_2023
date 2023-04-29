package ulb.infof307.g12.controller.javafx.store;

import javafx.stage.Stage;
import ulb.infof307.g12.controller.javafx.BaseController;

import ulb.infof307.g12.controller.listeners.StoreVueListener;
import ulb.infof307.g12.model.Paquet;
import ulb.infof307.g12.view.paquets.EditionVueController;
import ulb.infof307.g12.view.store.StoreVueController;


import java.io.IOException;
import java.util.List;

public class StoreController extends BaseController implements StoreVueListener {


    public StoreController(Stage stage) throws IOException {
        super(stage, EditionVueController.class.getResource("editionPaquet.fxml"), "");
        StoreVueController controller = (StoreVueController) super.controller;
        controller.setListener(this);

    }


    @Override
    public void downloadPaquet(Paquet paquet) {

    }

    @Override
    public List<Paquet> getStorePaquets() {

        return null;
    }

    public void uploadPaquet(Paquet paquet){

    }

}
