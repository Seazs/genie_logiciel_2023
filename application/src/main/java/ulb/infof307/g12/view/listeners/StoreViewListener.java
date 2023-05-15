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

    /**
     * Filtre les paquets du store selon la recherche
     * @param recherche recherche
     * @return liste des paquets du store filtrés
     */
    Collection<PaquetDTO> filterPaquet(String recherche);

    Collection<PaquetDTO> getUserPaquets();

    /**
     * Supprime un paquet du store
     * @param paquet paquet à supprimer du store
     */
    void deletePaquetStore(PaquetDTO paquet);

}
