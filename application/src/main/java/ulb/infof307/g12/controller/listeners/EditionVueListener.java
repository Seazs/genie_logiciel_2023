package ulb.infof307.g12.controller.listeners;

import ulb.infof307.g12.model.Carte;

import java.util.ArrayList;

public interface EditionVueListener {
    /**
     * Enregistrement du paquet
     * @param nom nom du paquet
     * @param categorie categorie du paquet
     */
    void enregistrerPaquet(String nom, String categorie);
    ArrayList<Carte> loadCartes();

    /**
     * Ajout de cartes de type Qr
     * @param recto recto de la carte
     * @param verso verso de la carte
     */
    void ajouterCarte(String recto, String verso);

    /**
     * Ajout de cartes
     * @param recto recto de la carte
     * @param verso verso de la carte
     */
    void ajouterCarteQCM(String recto, String verso);

    /**
     * Ajout de cartes
     * @param recto recto de la carte
     * @param verso verso de la carte
     */
    void ajouterCarteTT(String recto, String verso);

}
