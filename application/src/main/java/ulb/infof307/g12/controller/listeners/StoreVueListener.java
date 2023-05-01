package ulb.infof307.g12.controller.listeners;

import ulb.infof307.g12.model.Paquet;

import java.io.IOException;
import java.util.List;

public interface StoreVueListener {

    /**
     * @param paquet
     */
    void uploadPaquet(Paquet paquet) throws IOException;

    /**
     * @param paquet
     */
    void downloadPaquet(Paquet paquet);

    /**
     * @return liste des paquets du store
     */
    List<Paquet> getStorePaquets();

    /**
     * Rafraichit la liste des paquets du store
     */
    void refresh();
}
