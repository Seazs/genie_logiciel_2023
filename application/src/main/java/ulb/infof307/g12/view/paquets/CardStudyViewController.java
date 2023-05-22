package ulb.infof307.g12.view.paquets;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.web.WebView;
import lombok.Setter;
import ulb.infof307.g12.view.listeners.CardStudyListener;

import org.scilab.forge.jlatexmath.TeXFormula;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import java.awt.image.BufferedImage;
import javafx.embed.swing.SwingFXUtils;

public class CardStudyViewController {
    @Setter
    private CardStudyListener listener;
    @FXML
    private Label qrText, questionQcmLabel,answer;
    @FXML
    private Button btnChange, btnValidAnswer;
    @FXML
    private ListView<String> reponsesList;
    @FXML
    private TextField reponseTt;
    @FXML
    private WebView htmlView;
    @FXML
    private ImageView LatexView;
    private int indexCarte = 0;
    private int side = 0; // 0 = recto, 1 = verso


    /**
     * Chargement de la vue des cartes d'études
     */
    public void loadViewStudyCard() {
        listener.sortCardStudyList();
        String type = listener.getCardsStudy().get(indexCarte).getType();
        showGoodTypeCard(type);
    }

    /**
     * Affiche le bon type de carte
     * @param type type de la carte
     */
    private void showGoodTypeCard(String type){
        switch (type) {
            case "Carte" -> showQR();
            case "QCM" -> showQCMFront();
            case "TT" -> showTTFront();
            case "Spec" -> showSpec();
        }
        answer.setText("");
        reponseTt.clear();
    }

    /**
     * Change de côté de la carte entre recto et verso
     */
    public void changeSide(){
        String type = listener.getCardsStudy().get(indexCarte).getType();
        switch (type) {
            case "Carte" -> changeSideSimple();
            case "QCM", "TT" -> changeSideTTorQcm();
            case "Spec" -> changeSideSpec();
        }
    }

    /**
     * Affiche une carte spéciale
     */
    public void showSpec(){
        String[] infos = listener.getCardsStudy().get(indexCarte).getCardInfo();
        String lang = infos[3];
        if (lang.equals("html")) showHTML(infos[1]);
        else if (lang.equals("latex")) showLatex(infos[1]);
        btnChange.setText("Recto");
        side = 0;
    }


    /**
     * Change le côté de la carte Spéciale
     */
    private void changeSideSpec(){
        String[] infos = listener.getCardsStudy().get(indexCarte).getCardInfo();
        String lang = infos[3];
        if (side == 0){
            if (lang.equals("html")) showHTML(infos[2]);
            else if (lang.equals("latex")) showLatex(infos[2]);
            btnChange.setText("Recto");
            side = 1;
        }
        else{
            if (lang.equals("html")) showHTML(infos[1]);
            else if (lang.equals("latex")) showLatex(infos[1]);
            btnChange.setText("Verso");
            side = 0;
        }
    }
    /**
     * Change le côté de la carte Texte à trou et QCM
     */
    private void changeSideTTorQcm() {

        if (side == 0){
            switch (listener.getCardsStudy().get(indexCarte).getType()) {
                case "QCM" -> showQCMBack();
                case "TT" -> showTTBack();
            }
            side = 1;
        }
        else{
            switch (listener.getCardsStudy().get(indexCarte).getType()) {
                case "QCM" -> showQCMFront();
                case "TT" -> showTTFront();
            }
            side = 0;
        }
    }

    /**
     * Change le côté de la carte QR
     */
    private void changeSideSimple() {
        if (side == 0){
            qrText.setText(listener.getCardsStudy().get(indexCarte).getVerso());
            btnChange.setText("Recto");
            side = 1;
        }
        else{
            qrText.setText(listener.getCardsStudy().get(indexCarte).getRecto());
            btnChange.setText("Verso");
            side = 0;
        }
    }


    /**
     * Fonction qui affiche les éléments d'étude de carte de type QR
     */
    private void showQR() {
        btnChange.setVisible(true);
        btnValidAnswer.setVisible(false);
        qrText.setVisible(true);
        questionQcmLabel.setVisible(false);
        htmlView.setVisible(false);
        LatexView.setVisible(false);
        btnChange.setText("Verso");
        reponseTt.setVisible(false);
        reponsesList.setVisible(false);
        qrText.setText(listener.getCardsStudy().get(indexCarte).getRecto());
    }


    /**
     * Show the HTML face of the card
     * @param content verso of the card
     */
    private void showHTML(String content) {
        btnChange.setVisible(true);
        btnValidAnswer.setVisible(false);
        qrText.setVisible(false);
        htmlView.setVisible(true);
        LatexView.setVisible(false);
        htmlView.getEngine().loadContent(content);
        questionQcmLabel.setVisible(false);
        btnChange.setText("Verso");
        reponseTt.setVisible(false);
        reponsesList.setVisible(false);
    }

    /**
     * Show the Latex face of the card
     * @param content verso of the card
     */
    private void showLatex(String content){
        btnChange.setVisible(true);
        btnValidAnswer.setVisible(false);
        qrText.setVisible(false);
        htmlView.setVisible(false);
        TeXFormula formula = new TeXFormula(content);
        BufferedImage bufferedImage = (BufferedImage) formula.createBufferedImage(TeXFormula.SERIF, 30, java.awt.Color.BLACK, null);
        Image image = SwingFXUtils.toFXImage(bufferedImage, null);
        LatexView.setImage(image);
        questionQcmLabel.setVisible(false);
        btnChange.setText("Verso");
        reponseTt.setVisible(false);
        reponsesList.setVisible(false);
        LatexView.setVisible(true);
    }

    /**
     * Fonction qui affiche les éléments d'étude de carte de type QCM
     */
     private void showQCMFront() {
        btnChange.setVisible(true);
        btnValidAnswer.setVisible(true);
        qrText.setVisible(false);
        questionQcmLabel.setVisible(true);
        htmlView.setVisible(false);
        LatexView.setVisible(false);
        btnChange.setText("Réponse");
        reponsesList.setVisible(true);
        reponseTt.setVisible(false);
        String[] infos = listener.getCardsStudy().get(indexCarte).getCardInfo();
        questionQcmLabel.setText(infos[0]);
        reponsesList.getItems().clear();
        reponsesList.getItems().addAll(infos[1], infos[2], infos[3]);
    }

    /**
     * Fonction qui affiche les éléments d'étude de carte de type TT
     */
    private void showTTFront() {
        btnChange.setVisible(true);
        btnValidAnswer.setVisible(true);
        qrText.setVisible(true);
        questionQcmLabel.setVisible(false);
        btnChange.setText("Verso");
        htmlView.setVisible(false);
        LatexView.setVisible(false);
        reponsesList.setVisible(false);
        reponseTt.setVisible(true);
        String[] infos = listener.getCardsStudy().get(indexCarte).getCardInfo();
        qrText.setText(infos[0]+ "___" + infos[1]);
    }
    /**
     * Fonction qui affiche les éléments d'étude de carte de type QCM
     */
    private void showQCMBack() {
        btnChange.setVisible(true);
        btnValidAnswer.setVisible(false);
        qrText.setVisible(true);
        questionQcmLabel.setVisible(true);
        htmlView.setVisible(false);
        LatexView.setVisible(false);
        reponseTt.setVisible(false);
        btnChange.setText("Question");
        reponsesList.setVisible(false);
        qrText.setText(listener.getCardsStudy().get(indexCarte).getVerso());
    }
    /**
     * Fonction qui affiche les éléments d'étude de carte de type TT
     */
    private void showTTBack() {
        btnChange.setVisible(true);
        btnValidAnswer.setVisible(false);
        qrText.setVisible(true);
        htmlView.setVisible(false);
        LatexView.setVisible(false);
        reponseTt.setVisible(false);
        questionQcmLabel.setVisible(false);
        btnChange.setText("Question");
        reponsesList.setVisible(false);
        qrText.setText(listener.getCardsStudy().get(indexCarte).getVerso());
    }


    /**
     * Fonction appelée lors du clic sur le bouton "BoutonValidAnswer"
     */
    @FXML
    void choiceSelected() {
        String type = listener.getCardsStudy().get(indexCarte).getType();
        switch (type) {
            case "QCM" -> verifyAnswerQcm();
            case "TT" -> verifyAnswerTt();
        }
    }

    /**
     * Fonction qui verifie si la réponse est bonne pour les cartes Qcm
     */
    public void verifyAnswerQcm(){
        String[] infos = listener.getCardsStudy().get(indexCarte).getCardInfo();
        if (reponsesList.getSelectionModel().getSelectedItem() == null) {
            listener.error("Il faut sélectionner une solution !");
            return;
        }
        if(reponsesList.getSelectionModel().getSelectedItem().equals(infos[4])){
            answer.setText("Bien joué !");
            veryGood();
        }else{
            answer.setText("Dommage, réessaie !");
            veryBad();
        }
    }

    /**
     * Fonction qui verifie si la réponse est bonne pour les cartes Tt
     */
    public void verifyAnswerTt(){
        String[] infos = listener.getCardsStudy().get(indexCarte).getCardInfo();
        if(reponseTt.getText().equals(infos[2])){
            answer.setText("Bien joué !");
            listener.veryGood(indexCarte);
        }else{
            answer.setText("Dommage, réessaie !");
            listener.veryBad(indexCarte);
        }
    }

    /**
     * Passe à la carte suivante
     */
    public void nextCard(){
        indexCarte=++indexCarte%listener.getCardsStudy().size();
        if(indexCarte==0){
            listener.sortCardStudyList();
        }
        showGoodTypeCard(listener.getCardsStudy().get(indexCarte).getType());
        side=0;
    }

    /**
     * Retourne à la carte précédente
     */
    public void previousCard(){
        if (indexCarte > 0){
            indexCarte=--indexCarte%listener.getCardsStudy().size();
            showGoodTypeCard(listener.getCardsStudy().get(indexCarte).getType());
            side=0;
        }
    }

    /**
     * Fonction qui appelle la fonction saveCards de CarteEtudeListener
     */
    public void endStudy() {
        listener.returnFromCardStudyToMenuPaquet();
        listener.saveCards();
    }

    /**
     * Fonction qui appelle la fonction tresMauvais de CarteEtudeListener
     */
    @FXML
    public void veryBad() { listener.veryBad(indexCarte); }

    /**
     * Fonction qui appelle la fonction mauvais de CarteEtudeListener
     */
    public void bad() {
        listener.bad(indexCarte);
    }

    /**
     * Fonction qui appelle la fonction moyen de CarteEtudeListener
     */
    public void middle() {
        listener.average(indexCarte);
    }

    /**
     * Fonction qui appelle la fonction bon de CarteEtudeListener
     */
    public void good() {
        listener.good(indexCarte);
    }

    /**
     * Fonction qui appelle la fonction tresBon de CarteEtudeListener
     */
    public void veryGood() {
        listener.veryGood(indexCarte);
    }

    /**
     * @param actionEvent  evenement
     */
    public void btnReadText(ActionEvent actionEvent) {
        String[] info =listener.getCardsStudy().get(indexCarte).getCardInfo();
        if (side == 0){
            switch (listener.getCardsStudy().get(indexCarte).getType()) {
                case "Carte" -> listener.speakText(info[1]);
                case "QCM" -> listener.speakText(info[0]+info[1]+info[2]+info[3]);
                case "TT" -> listener.speakText(info[0]+info[1]);
            }
        }
        else {
            switch (listener.getCardsStudy().get(indexCarte).getType()) {
                case "Carte", "TT" -> listener.speakText(info[2]);
                case "QCM" -> listener.speakText(info[4]);
            }
        }
    }
}
