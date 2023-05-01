package ulb.infof307.g12.controller.listeners;

import ulb.infof307.g12.model.Paquet;

import java.io.IOException;

public interface MenuPaquetListener {
    /**
     * @return
     * @throws IOException
     */
    Paquet creerPaquet() throws IOException;

    /**
     * Editer le paquet
     * @param paquet
     */
    void editerPaquet(Paquet paquet) ;

    /**
     * Etude des cartes
     * @param paquet
     */
    void CarteEtude(Paquet paquet);

    /**
     * @param paquet Paquet à supprimer
     */
    void supprimerPaquet(Paquet paquet);
}
