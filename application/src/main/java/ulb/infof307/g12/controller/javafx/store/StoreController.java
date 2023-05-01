package ulb.infof307.g12.controller.javafx.store;

import javafx.stage.Stage;
import ulb.infof307.g12.controller.javafx.BaseController;

import ulb.infof307.g12.controller.javafx.connexion.MenuPrincipal;
import ulb.infof307.g12.controller.listeners.StoreVueListener;
import ulb.infof307.g12.model.JsonParser;
import ulb.infof307.g12.model.Paquet;
import ulb.infof307.g12.view.paquets.EditionVueController;
import ulb.infof307.g12.view.store.StoreVueController;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Controller for the store view
 *
 */
public class StoreController extends BaseController implements StoreVueListener {


    public StoreController(Stage stage) throws IOException {
        super(stage, StoreVueController.class.getResource("menuStore.fxml"), "");
        StoreVueController controller = (StoreVueController) super.controller;
        controller.setListener(this);

    }


    /**
     * @param paquet télécharger le paquet et l'ajoute a sa collection
     */
    @Override
    public void downloadPaquet(Paquet paquet) {

    }

    /**
     * @return la liste des paquets du store pour l'affichage
     */
    @Override
    public List<Paquet> getStorePaquets() {

        return null;
    }

    /**
     * @param paquet ajoute un paquet dans la liste des paquets du store
     */
    public void uploadPaquet(Paquet paquet) throws IOException {
        MenuPrincipal.getINSTANCE().getServer().postPaquet(paquet);
    }

    /**
     * Rafraichit la liste des paquets du store
     */
    @Override
    public void refresh() {
        String paquetsJson = MenuPrincipal.getINSTANCE().getServer().getPaquets();

    }
}
