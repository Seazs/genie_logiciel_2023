package ulb.infof307.g12.controller.listeners;

import ulb.infof307.g12.model.Carte;

import java.util.ArrayList;

public interface EditionVueListener {
    /**
     * Enregistrement du paquet
     * @param nom nom du paquet
     * @param categorie categorie du paquet
     */
    void savePaquet(String nom, String categorie);
    ArrayList<Carte> loadCartes();

    /**
     * Ajout de cartes de type Qr
     * @param recto recto de la carte
     * @param verso verso de la carte
     */
    void addCard(String recto, String verso);

    /**
     * Ajout de cartes
     * @param recto recto de la carte
     * @param verso verso de la carte
     */
    void addCardQCM(String recto, String verso);

    /**
     * Ajout de cartes
     * @param recto recto de la carte
     * @param verso verso de la carte
     */
    void addCardTT(String recto, String verso);


    /**
     * @param begin begin
     * @param end end
     * @param gap gap
     * @return true si les champs sont valides
     */
    boolean checkTt(String begin, String end, String gap);


    /**
     * @param question question
     * @param answer1 answer1
     * @param answer2 answer2
     * @param answer3 answer3
     * @param correctAnswer correctAnswer
     * @return true si les champs sont valides
     */
    boolean checkQcm(String question, String answer1, String answer2, String answer3, String correctAnswer);
}
