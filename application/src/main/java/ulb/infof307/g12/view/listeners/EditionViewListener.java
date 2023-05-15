package ulb.infof307.g12.view.listeners;

import ulb.infof307.g12.model.Card;
import ulb.infof307.g12.view.dto.CardDTO;

import java.util.ArrayList;
import java.util.List;

public interface EditionViewListener {
    /**
     * Enregistrement du paquet
     * @param nom nom du paquet
     * @param categorie categorie du paquet
     */
    void savePaquet(String nom, String categorie);
    ArrayList<Card> loadCards();

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
     * Ajout de cartes
     * @param recto recto de la carte
     * @param verso verso de la carte
     * @param lang langue de la carte
     */
    void addCardSpecial(String recto, String verso,String lang);


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


    /**
     * @param message message d'erreur
     */
    void error(String message);

    /**
     * @return Liste de question/reponses
     */
    List<CardDTO> getData();

    /**
     * Change la question d'une carte dans le paquet
     * @param newQuestion nouvelle question
     */
    void editQuestion(String newQuestion);

    /**
     * Change la réponse d'une carte dans le paquet
     * @param newReponse nouvelle réponse
     */
    void editResponse(String newReponse);
}
