package ulb.infof307.g12.view.listeners;

import ulb.infof307.g12.view.dto.PaquetDTO;

import java.io.IOException;
import java.util.Collection;

public interface StoreViewListener {

    /**
     * @param paquet paquet
     */
    void uploadPaquet(PaquetDTO paquet) throws IOException;

    /**
     * @param paquet paquet
     */
    void downloadPaquet(PaquetDTO paquet);

    /**
     * @return liste des paquets du store
     */
    Collection<PaquetDTO> getStorePaquets();

    Collection<PaquetDTO> filterPaquet(String recherche);

    Collection<PaquetDTO> getUserPaquets();

    void deletePaquetStore(PaquetDTO paquet);

}
