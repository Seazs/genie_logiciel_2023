package ulb.infof307.g12.view.listeners;

import ulb.infof307.g12.model.Paquet;

import java.io.IOException;

public interface MenuPaquetListener {
    /**
     * @return Paquet
     * @throws IOException exception
     */
    Paquet creerPaquet() throws IOException;

    /**
     * Editer le paquet
     * @param paquet paquet
     */
    void editerPaquet(Paquet paquet) ;

    /**
     * Etude des cartes
     * @param paquet paquet
     */
    void CarteEtude(Paquet paquet);

    /**
     * @param paquet Paquet à supprimer
     */
    void supprimerPaquet(Paquet paquet);
}
