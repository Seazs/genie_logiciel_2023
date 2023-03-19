package ulb.infof307.g12.controller.listeners;

import ulb.infof307.g12.model.Paquet;
import ulb.infof307.g12.model.Utilisateur;

public interface MenuPaquetListener {
    void openProfile(Utilisateur user);
    void openPaquet(Paquet paquet);
}
