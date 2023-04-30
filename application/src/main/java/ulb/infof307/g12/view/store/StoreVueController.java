package ulb.infof307.g12.view.store;

import lombok.Setter;
import ulb.infof307.g12.controller.javafx.connexion.MenuPrincipal;
import ulb.infof307.g12.controller.javafx.store.StoreController;
import ulb.infof307.g12.controller.listeners.StoreVueListener;


public class StoreVueController {


    @Setter
    private StoreVueListener listener;

    /**
     * Retourne au menu précédent
     */
    public void retourMenuPaquet() {
        MenuPrincipal.getINSTANCE().returnFromStoreToMenuPaquet();
    }
}
