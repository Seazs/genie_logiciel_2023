package ulb.infof307.g12.view.paquets;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.web.WebView;
import lombok.Setter;
import ulb.infof307.g12.controller.javafx.connexion.MenuPrincipal;
import ulb.infof307.g12.view.listeners.CarteEtudeListener;

import org.scilab.forge.jlatexmath.TeXFormula;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import java.awt.image.BufferedImage;
import javafx.embed.swing.SwingFXUtils;
import java.util.concurrent.ThreadLocalRandom;

public class CarteEtudeVueController{
    @Setter
    private CarteEtudeListener listener;
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
            case "Spec" -> showSpec();
        }
        answer.setText("");
        reponseTt.clear();
    }

    /**
     * Change de côté de la carte entre recto et verso
     */
    public void changeSide(){
        String type = listener.getCartesEtude().get(indexCarte).getType();
        switch (type) {
            case "Simple" -> changeSideSimple();
            case "QCM", "TT" -> changeSideTTorQcm();
            case "Spec" -> changeSideSpec();
        }
    }

    /**
     * Affiche une carte spéciale
     */
    public void showSpec(){
        String[] infos = listener.getCartesEtude().get(indexCarte).getCarteInfo();
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
        String[] infos = listener.getCartesEtude().get(indexCarte).getCarteInfo();
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
            switch (listener.getCartesEtude().get(indexCarte).getType()) {
                case "QCM" -> showQCMBack();
                case "TT" -> showTTBack();
            }
            side = 1;
        }
        else{
            switch (listener.getCartesEtude().get(indexCarte).getType()) {
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
            qrText.setText(listener.getCartesEtude().get(indexCarte).getVerso());
            btnChange.setText("Recto");
            side = 1;
        }
        else{
            qrText.setText(listener.getCartesEtude().get(indexCarte).getRecto());
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
        qrText.setText(listener.getCartesEtude().get(indexCarte).getRecto());
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
        String[] infos = listener.getCartesEtude().get(indexCarte).getCarteInfo();
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
        String[] infos = listener.getCartesEtude().get(indexCarte).getCarteInfo();
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
        qrText.setText(listener.getCartesEtude().get(indexCarte).getVerso());
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
        qrText.setText(listener.getCartesEtude().get(indexCarte).getVerso());
    }


    /**
     * Fonction appelée lors du clic sur le bouton "BoutonValidAnswer"
     */
    @FXML
    void choiceSelected() {
        String type = listener.getCartesEtude().get(indexCarte).getType();
        switch (type) {
            case "QCM" -> verfyAnswerQcm();
            case "TT" -> verifyAnswerTt();
        }
    }

    /**
     * Fonction qui verifie si la réponse est bonne pour les cartes Qcm
     */
    public void verfyAnswerQcm() throws NullPointerException{
        String[] infos = listener.getCartesEtude().get(indexCarte).getCarteInfo();
        try{
            if(reponsesList.getSelectionModel().getSelectedItem().equals(infos[4])){
                answer.setText("T'es un bg en sah");
                veryGood();
            }
            else{
                answer.setText("Tu pues ta grand mère");
                veryBad();
            }
        }
        catch (NullPointerException e){
            MenuPrincipal.getINSTANCE().showErrorPopup("Il faut sélectionner une solution !");
        }

    }

    /**
     * Fonction qui verifie si la réponse est bonne pour les cartes Tt
     */
    public void verifyAnswerTt(){
        String[] infos = listener.getCartesEtude().get(indexCarte).getCarteInfo();
        if(reponseTt.getText().equals(infos[2])){
            answer.setText("T'es un bg en sah");
            listener.tresBon(indexCarte);
        }
        else{
            answer.setText("Tu pues ta grand mère !");
            listener.tresMauvais(indexCarte);
        }
    }

    /**
     * Passe à la carte suivante
     */
    public void nextCard(){
        indexCarte++;
        indexCarte = indexRandom();
        showGoodTypeCard(listener.getCartesEtude().get(indexCarte).getType());
        side=0;
    }

    /**
     * Retourne à la carte précédente
     */
    public void previousCard(){
        if (indexCarte >= 0){
            indexCarte=indexRandom();
            showGoodTypeCard(listener.getCartesEtude().get(indexCarte).getType());
            side=0;
        }

    }

    /**
     * Fonction qui appelle la fonction terminer de CarteEtudeListener
     */
    public void endStudy() {
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
        if(cardsReaded()){
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
    public boolean cardsReaded() {
        indexCarte=0;
        while (indexCarte < listener.getCartesEtude().size()-1) {
            indexCarte++;
            if (listener.getCartesEtude().get(indexCarte).getConnaissance() == 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * @param actionEvent  evenement
     */
    public void btnReadText(ActionEvent actionEvent) {
        String info[]=listener.getCartesEtude().get(indexCarte).getCarteInfo();
        if (side == 0){
            switch (listener.getCartesEtude().get(indexCarte).getType()) {
                case "Simple" -> listener.parlerTexte(info[1]);
                case "QCM" -> listener.parlerTexte(info[0]+info[1]+info[2]+info[3]);
                case "TT" -> listener.parlerTexte(info[0]+info[1]);
            }
        }
        else {
            switch (listener.getCartesEtude().get(indexCarte).getType()) {
                case "Simple", "TT" -> listener.parlerTexte(info[2]);
                case "QCM" -> listener.parlerTexte(info[4]);
            }
        }
    }
}
