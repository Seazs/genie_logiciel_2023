package ulb.infof307.g12.controller.listeners;

import ulb.infof307.g12.model.Carte;

import java.util.ArrayList;

public interface EditionVueListener {
    void enregistrerPaquet(String nom, String categorie);
    ArrayList<Carte> loadCartes();
    void ajouterCarte(String recto, String verso);
}
