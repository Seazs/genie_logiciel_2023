package ulb.infof307.g12.view.listeners;

import ulb.infof307.g12.model.Paquet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public interface StoreVueListener {

    /**
     * @param paquet paquet
     */
    void uploadPaquet(Paquet paquet) throws IOException;

    /**
     * @param paquet paquet
     */
    void downloadPaquet(Paquet paquet);

    /**
     * @return liste des paquets du store
     */
    List<Paquet> getStorePaquets();

    /**
     * Rafraichit la liste des paquets du store
     */
    ArrayList<Paquet> refresh();
}
