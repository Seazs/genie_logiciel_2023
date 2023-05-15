package ulb.infof307.g12.view.listeners;

import ulb.infof307.g12.model.Card;

import java.util.ArrayList;

public interface CardStudyListener {
    public ArrayList<Card> getCardsStudy();

    /**
     * @param index index
     */
    public void veryBad(int index);

    /**
     * @param index index
     */
    public void bad(int index);

    /**
     * @param index index
     */
    public void average(int index);

    /**
     * @param index index
     */
    public void good(int index);

    /**
     * @param index index
     */
    public void veryGood(int index);

    /**
     * Sauvegarde des cartes
     */
    public void saveCards();

    /**
     * Fait parler le texte par la synthèse vocale
     * @param texte texte
     */
    public void speakText(String texte);

    /**
     * Affiche une erreur
     * @param msg message d'erreur
     */
    public void error(String msg);
}
