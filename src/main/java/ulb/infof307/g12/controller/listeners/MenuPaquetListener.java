package ulb.infof307.g12.controller.listeners;

import ulb.infof307.g12.model.Paquet;
import ulb.infof307.g12.model.Utilisateur;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public interface MenuPaquetListener {
    void openPaquet(Paquet paquet);
    Paquet creerPaquet() throws IOException;
    void editerPaquet(Paquet paquet) ;
    void CarteEtude();


}
