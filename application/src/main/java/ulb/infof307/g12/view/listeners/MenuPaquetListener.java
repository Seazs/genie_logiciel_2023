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
    PaquetDTO createPaquet() throws IOException;

    /**
     * Éditer le paquet
     * @param paquet paquet
     */
    void editPaquet(PaquetDTO paquet) ;

    /**
     * Etude des cartes
     * @param paquet paquet
     */
    void cardStudy(PaquetDTO paquet);

    /**
     * @param paquet Paquet à supprimer
     */
    void deletePaquet(PaquetDTO paquet);

    /**
     * Filtre les paquets selon le filtre
     * @param filter filtre
     */
    void filterPaquet(String filter);

    Collection<PaquetDTO> getPaquetDTOList();

    /**
     * Importer un paquet avec un menu de sélection
     * @param file fichier
     *
     */
    void importPaquet(File file);


    /**
     * Exporter un paquet avec un menu de sélection
     * @param paquet Paquet à exporter
     * @param path chemin où le dossier sera exporté
     */
    void exportPaquet(PaquetDTO paquet, String path);

    /**
     * Synchroniser les paquets
     */
    void sync();
}
