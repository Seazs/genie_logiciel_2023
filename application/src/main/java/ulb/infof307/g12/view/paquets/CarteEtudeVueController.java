package ulb.infof307.g12.view.paquets;

import javafx.event.ActionEvent;


import java.lang.annotation.Documented;
import java.util.concurrent.ThreadLocalRandom;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import lombok.Setter;
import ulb.infof307.g12.controller.javafx.connexion.MenuPrincipal;
import ulb.infof307.g12.controller.listeners.CarteEtudeListener;
import ulb.infof307.g12.model.Carte;
import ulb.infof307.g12.model.Paquet;

import java.util.ArrayList;



public class CarteEtudeVueController{
    @Setter
    private CarteEtudeListener listener;
    @FXML
    private Label qrText, questionQcmLabel,answer;
    @FXML
    private Button boutonChange, btnValidAnswer;
    @FXML
    private ListView<String> reponsesList;
    @FXML
    private TextField reponseTt;
    @FXML
    private Button boutonEcouter;
    private int indexCarte = 0;
    private int cote = 0; // 0 = recto, 1 = verso


    /**
     * Chargement de la vue des cartes d'études
     */
    public void chargerCarteEtudeVue() {
        indexCarte=indexRandom();
        String type = listener.getCartesEtude().get(indexCarte).getType();
        showGoodTypeCard(type);
    }

    /**
     * Affiche le bon type de carte
     * @param type type de la carte
     */
    private void showGoodTypeCard(String type){
        switch (type) {
            case "Simple" -> showQR();
            case "QCM" -> showQCMFront();
            case "TT" -> showTTFront();
        }
        answer.setText("");
        reponseTt.clear();
    }

    /**
     * Change de côté de la carte entre recto et verso
     */
    public void changeCote(){
        String type = listener.getCartesEtude().get(indexCarte).getType();
        switch (type) {
            case "Simple" -> changeCoteQr();
            case "QCM" -> changeCoteQcm();
            case "TT" -> changeCoteTt();
        }
    }

    private void changeCoteTt() {
        String[] infos = listener.getCartesEtude().get(indexCarte).getCarteInfo();
        if (cote == 0){
            showTTFront();
            qrText.setText(infos[0]+"___"+infos[1]);
            boutonChange.setText("Réponse");
            reponseTt.clear();
            cote = 1;
        }
        else{
            showTTBack();
            qrText.setText(infos[2]); // affiche la bonne réponse
            boutonChange.setText("Question");
            cote = 0;
        }
    }

    private void changeCoteQcm() {
        String[] infos = listener.getCartesEtude().get(indexCarte).getCarteInfo();
        if (cote == 0){
            showQCMFront();
            questionQcmLabel.setText(infos[0]);
            reponsesList.getItems().clear();
            reponsesList.getItems().addAll(infos[1], infos[2], infos[3]);
            boutonChange.setText("Réponse");
            cote = 1;
        }
        else{
            showQCMBack();
            qrText.setText(infos[4]); // affiche la bonne réponse
            boutonChange.setText("Question");
            cote = 0;
        }


    }

    private void changeCoteQr() {
        if (cote == 0){
            qrText.setText(listener.getCartesEtude().get(indexCarte).getVerso());
            boutonChange.setText("Recto");
            cote = 1;
        }
        else{
            qrText.setText(listener.getCartesEtude().get(indexCarte).getRecto());
            boutonChange.setText("Verso");
            cote = 0;
        }
    }


    /**
     * Fonction qui affiche les éléments d'étude de carte de type QR
     */
    private void showQR() {
        boutonChange.setVisible(true);
        btnValidAnswer.setVisible(false);
        qrText.setVisible(true);
        questionQcmLabel.setVisible(false);
        boutonChange.setText("Verso");
        reponseTt.setVisible(false);
        reponsesList.setVisible(false);
        qrText.setText(listener.getCartesEtude().get(indexCarte).getRecto());
    }


    /**
     * Fonction qui affiche les éléments d'étude de carte de type QCM
     */
     private void showQCMFront() {
        boutonChange.setVisible(true);
        btnValidAnswer.setVisible(true);
        qrText.setVisible(false);
        questionQcmLabel.setVisible(true);
        boutonChange.setText("Réponse");
        reponsesList.setVisible(true);
        reponseTt.setVisible(false);
        String[] infos = listener.getCartesEtude().get(indexCarte).getCarteInfo();
        questionQcmLabel.setText(infos[0]);
        reponsesList.getItems().clear();
        reponsesList.getItems().addAll(infos[1], infos[2], infos[3]);
    }

    /**
     * Fonction qui affiche les éléments d'étude de carte de type TT
     */
    private void showTTFront() {
        boutonChange.setVisible(true);
        btnValidAnswer.setVisible(true);
        qrText.setVisible(true);
        questionQcmLabel.setVisible(false);
        boutonChange.setText("Verso");
        reponsesList.setVisible(false);
        reponseTt.setVisible(true);
        String[] infos = listener.getCartesEtude().get(indexCarte).getCarteInfo();
        qrText.setText(infos[0]+ "___" + infos[1]);
    }
    /**
     * Fonction qui affiche les éléments d'étude de carte de type QCM
     */
    private void showQCMBack() {
        boutonChange.setVisible(true);
        btnValidAnswer.setVisible(false);
        qrText.setVisible(true);
        questionQcmLabel.setVisible(true);
        reponseTt.setVisible(false);
        boutonChange.setText("Question");
        reponsesList.setVisible(false);
        qrText.setText(listener.getCartesEtude().get(indexCarte).getVerso());
    }
    /**
     * Fonction qui affiche les éléments d'étude de carte de type TT
     */
    private void showTTBack() {
        boutonChange.setVisible(true);
        btnValidAnswer.setVisible(false);
        qrText.setVisible(true);
        reponseTt.setVisible(false);
        questionQcmLabel.setVisible(false);
        boutonChange.setText("Question");
        reponsesList.setVisible(false);
        qrText.setText(listener.getCartesEtude().get(indexCarte).getVerso());
    }


    /**
     * Fonction appelée lors du clic sur le bouton "BoutonValidAnswer"
     */
    @FXML
    void choiceSelected() {
        String type = listener.getCartesEtude().get(indexCarte).getType();
        switch (type) {
            case "QCM" -> verfiedAnswerQcm();
            case "TT" -> verifiedAnswerTt();
        }
    }

    /**
     * Fonction qui verifie si la réponse est bonne pour les cartes Qcm
     */
    public void verfiedAnswerQcm(){
        String[] infos = listener.getCartesEtude().get(indexCarte).getCarteInfo();
        if(reponsesList.getSelectionModel().getSelectedItem().equals(infos[4])){
            answer.setText("T'es un bg en sah");
            veryGood();
        }
        else{
            answer.setText("Tu pues ta grand mère");
            veryBad();
        }
    }

    /**
     * Fonction qui verifie si la réponse est bonne pour les cartes Tt
     */
    public void verifiedAnswerTt(){
        String[] infos = listener.getCartesEtude().get(indexCarte).getCarteInfo();
        if(reponseTt.getText().equals(infos[2])){
            answer.setText("T'es un bg en sah");
            listener.tresBon(indexCarte);
        }
        else{
            answer.setText("Tu pues ta grand mère");
            listener.tresMauvais(indexCarte);
        }
    }

    /**
     * Passe à la carte suivante
     */
    public void carteSuivante(){
        indexCarte++;
        indexCarte = indexRandom();
        showGoodTypeCard(listener.getCartesEtude().get(indexCarte).getType());
    }

    /**
     * Retourne à la carte précédente
     */
    public void cartePrecedente(){
        if (indexCarte >= 0){
            indexCarte=indexRandom();
            showGoodTypeCard(listener.getCartesEtude().get(indexCarte).getType());
        }

    }

    /**
     * Fonction qui appelle la fonction terminer de CarteEtudeListener
     */
    public void terminer() {
        MenuPrincipal.getINSTANCE().returnFromCarteEtudeToMenuPaquet();
        listener.saveCartes();
    }

    /**
     * Fonction qui appelle la fonction tresMauvais de CarteEtudeListener
     */
    @FXML
    public void veryBad() { listener.tresMauvais(indexCarte); }

    /**
     * Fonction qui appelle la fonction mauvais de CarteEtudeListener
     */
    public void bad() {
        listener.mauvais(indexCarte);
    }

    /**
     * Fonction qui appelle la fonction moyen de CarteEtudeListener
     */
    public void middle() {
        listener.moyen(indexCarte);
    }

    /**
     * Fonction qui appelle la fonction bon de CarteEtudeListener
     */
    public void good() {
        listener.bon(indexCarte);
    }

    /**
     * Fonction qui appelle la fonction tresBon de CarteEtudeListener
     */
    public void veryGood() {
        listener.tresBon(indexCarte);
    }

    /**
     * Vérifie si toutes les cartes ont été vues au moins une fois. Si c’est le cas:
     * On tire une carte du paquet au hasard. On tire un nombre "apparition" au hasard entre 0 et scoreConnaissance*10 + 1.
     * tant que le nombre "apparition" est plus grand que 20, on continue à tirer une carte au hasard et une "apparition".
     * Cela signifie que les cartes avec un score de connaissance élevé sont tirées moins souvent car leur "apparition" à plus de chance d’être supérieur à 20.
     */
    public int indexRandom(){
        if(cartesLues()){
            indexCarte = ThreadLocalRandom.current().nextInt(0, listener.getCartesEtude().size());
            int apparition = ThreadLocalRandom.current().nextInt(0,(listener.getCartesEtude().get(indexCarte).getConnaissance()*10)+1);
            while(apparition>20){
                indexCarte = ThreadLocalRandom.current().nextInt(0, listener.getCartesEtude().size());
                apparition = ThreadLocalRandom.current().nextInt(0,(listener.getCartesEtude().get(indexCarte).getConnaissance()*10)+1);
            }
        }
        return indexCarte;
    }

    /**
     * Fonction qui vérifie si toutes les cartes ont été lues
     * @return true si toutes les cartes ont été lues
     */
    public boolean cartesLues() {
        indexCarte=0;
        while (indexCarte < listener.getCartesEtude().size()-1) {
            indexCarte++;
            if (listener.getCartesEtude().get(indexCarte).getConnaissance() == 0) {
                return false;
            }
        }
        return true;
    }
    public void boutonParlerClick(ActionEvent actionEvent) {
        if (cote== 0){
            this.listener.parlerTexte(listener.getCartesEtude().get(indexCarte).getRecto());
        }
        else {
            this.listener.parlerTexte(listener.getCartesEtude().get(indexCarte).getVerso());
        }
    }
}
