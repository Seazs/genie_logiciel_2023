package ulb.infof307.g12.view.paquets;


import java.util.concurrent.ThreadLocalRandom;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import lombok.Setter;
import ulb.infof307.g12.controller.javafx.connexion.MenuPrincipal;
import ulb.infof307.g12.controller.listeners.CarteEtudeListener;

import java.util.ArrayList;


public class CarteEtudeVueController{
    @Setter
    private CarteEtudeListener listener;
    @FXML
    private Label qrText, questionQcmLabel;
    @FXML
    private Button boutonSuivant;
    @FXML
    private Button boutonChange, btnValidAnswer, boutonTerminer;
    @FXML
    private ListView<String> reponsesList;

    private ArrayList<Integer> cartesEtudeScore;
    @FXML

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
            case "TT" -> showTT();
        }
    }

    private void showTT() {
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
        String[] infos = listener.getCartesEtude().get(indexCarte).getCarteInfo();
        questionQcmLabel.setText(infos[0]);
        reponsesList.getItems().clear();
        reponsesList.getItems().addAll(infos[1], infos[2], infos[3]);
    }

    /**
     * Fonction qui affiche les éléments d'étude de carte de type QCM
     */
    private void showQCMBack() {
        boutonChange.setVisible(true);
        btnValidAnswer.setVisible(true);
        qrText.setVisible(true);
        questionQcmLabel.setVisible(true);
        boutonChange.setText("Question");
        reponsesList.setVisible(false);
        qrText.setText(listener.getCartesEtude().get(indexCarte).getVerso());
    }

    /**
     * Fonction appelée lors du clic sur le bouton "BoutonValidAnswer"
     */
    @FXML
    void choiceSelected() {
        btnValidAnswer.setDisable(false);
        // TO DO
    }
    /**
     * Passe à la carte suivante
     */
    public void carteSuivante(){
        indexCarte = indexRandom();
        showGoodTypeCard(listener.getCartesEtude().get(indexCarte).getType());
    }

    /**
     * Retourne à la carte précédente
     */
    public void cartePrecedente(){
        if (indexCarte > 0){
            indexCarte--;
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
}
