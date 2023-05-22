package ulb.infof307.g12.controller.javafx.paquets;

import javafx.stage.Stage;
import lombok.Getter;
import ulb.infof307.g12.controller.javafx.BaseController;
import ulb.infof307.g12.controller.javafx.connexion.MenuPrincipal;
import ulb.infof307.g12.view.listeners.CardStudyListener;
import ulb.infof307.g12.controller.storage.PaquetManager;
import ulb.infof307.g12.controller.textToSpeech.textToSpeechController;
import ulb.infof307.g12.model.Card;
import ulb.infof307.g12.model.Paquet;
import ulb.infof307.g12.view.paquets.CardStudyViewController;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;

public class CardStudyController extends BaseController implements CardStudyListener {

    private final ArrayList<Card> cardsStudy;

    @Getter
    private final Paquet paquet;

    private final textToSpeechController textToSpeechController = new textToSpeechController();

    /**
     * Controller de l'étude de carte
     * @param stage stage
     * @param paquet paquet
     * @throws IOException  exception
     */
    public CardStudyController(Stage stage, Paquet paquet) throws IOException {
        super(stage, CardStudyViewController.class.getResource("cardStudy.fxml"), "");
        CardStudyViewController controller = (CardStudyViewController) super.controller;
        this.paquet=paquet;
        controller.setListener(this);
        cardsStudy = paquet.getCards();
        controller.loadViewStudyCard();
    }

    /**
     * @return CartesEtudes
     */
    @Override
    public ArrayList<Card> getCardsStudy(){
        return cardsStudy;
    }

    /**
     * @param index  index
     */
    @Override
    public void veryBad(int index){
        cardsStudy.get(index).setKnowledge(1);
    }

    /**
     * @param index index
     */
    @Override
    public void bad(int index){
        cardsStudy.get(index).setKnowledge(2);
    }

    /**
     * @param index index
     */
    @Override
    public void average(int index){
        cardsStudy.get(index).setKnowledge(3);
    }
    public void good(int index){
        cardsStudy.get(index).setKnowledge(4);
    }

    /**
     * @param index index
     */
    @Override
    public void veryGood(int index){
        cardsStudy.get(index).setKnowledge(5);
    }

    /**
     * Sauvegarde des cartes
     */
    @Override
    public void saveCards(){
        try {
            PaquetManager paquetManager = MenuPrincipal.getINSTANCE().getPaquetManager();
            paquetManager.save(MenuPrincipal.getINSTANCE().getPrincipalUser());
        } catch (IOException e) {
            MenuPrincipal.getINSTANCE().showErrorPopup("Impossible de sauvegarder le paquet !");
        }
    }

    /**
     * Fonction qui permet de lire un texte avec la synthèse vocale
     * @param text text
     */
    @Override
    public void speakText(String text){
        textToSpeechController.vocalSynthesis(text);
    }

    /**
     * @param msg message d'erreur à afficher
     */
    @Override
    public void error(String msg) {
        MenuPrincipal.getINSTANCE().showErrorPopup(msg);
    }

    /**
     * Retour au menu principal depuis l'étude de carte
     */
    @Override
    public void returnFromCardStudyToMenuPaquet() {MenuPrincipal.getINSTANCE().returnFromCardStudyToMenuPaquet();}

    /**
     * Trie les cartes par connaissance
     */
    @Override
    public void sortCardStudyList(){cardsStudy.sort(Comparator.comparingInt(Card::getConnaissance));}

}



