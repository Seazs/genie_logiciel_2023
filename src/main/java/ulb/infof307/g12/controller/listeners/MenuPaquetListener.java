package ulb.infof307.g12.controller.listeners;

import ulb.infof307.g12.model.Paquet;

import java.io.IOException;

public interface MenuPaquetListener {
    void openPaquet(Paquet paquet);
    Paquet creerPaquet() throws IOException;
    void editerPaquet(Paquet paquet) ;
    void CarteEtude(Paquet paquet);
}
