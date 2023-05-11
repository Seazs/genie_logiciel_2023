package ulb.infof307.g12.view.listeners;

import ulb.infof307.g12.view.dto.PaquetDTO;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

public interface MenuPaquetListener {
    /**
     * @return Paquet
     * @throws IOException exception
     */
    PaquetDTO creerPaquet() throws IOException;

    /**
     * Editer le paquet
     * @param paquet paquet
     */
    void editerPaquet(PaquetDTO paquet) ;

    /**
     * Etude des cartes
     * @param paquet paquet
     */
    void CarteEtude(PaquetDTO paquet);

    /**
     * @param paquet Paquet à supprimer
     */
    void supprimerPaquet(PaquetDTO paquet);

    Collection<PaquetDTO> filterPaquet(String filter);

    Collection<PaquetDTO> getPaquetDTOList();

    /**
     * Importer un paquet avec une menu de sélection
     * @param file fichier
     *
     */
    void importPaquet(File file);
}
