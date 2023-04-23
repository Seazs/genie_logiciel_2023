package ulb.infof307.g12.controller.listeners;

import ulb.infof307.g12.model.Carte;

import java.util.ArrayList;

public interface EditionVueListener {
    /**
     * Enregistrement du paquet
     * @param nom
     * @param categorie
     */
    void enregistrerPaquet(String nom, String categorie);
    ArrayList<Carte> loadCartes();

    /**
     * Ajout de cartes
     * @param recto
     * @param verso
     */
    void ajouterCarte(String recto, String verso);
}
