package ulb.infof307.g12.view.paquets;

import javafx.event.ActionEvent;


import java.util.concurrent.ThreadLocalRandom;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
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
    private Text affichageCarte;
    @FXML
    private Button boutonSuivant;
    @FXML
    private Button boutonChange;
    private ArrayList<Carte> cartesEtude;
    private ArrayList<Integer> cartesEtudeScore;

    private int indexCarte = 0;
    private int cote = 0; // 0 = recto, 1 = verso


    /**
     * Chargement de la vue des cartes d'études
     * @param cartesEtude
     */
    public void chargerCarteEtudeVue(ArrayList<Carte> cartesEtude) {
        indexCarte=indexRandom();
        affichageCarte.setText(cartesEtude.get(indexCarte).getRecto());
    }

    /**
     * Change de coté de la carte entre recto et verso
     * @return
     */
    public void changeCote(){
        cartesEtude = listener.getCartesEtude();
        if (cote == 0){
            affichageCarte.setText(cartesEtude.get(indexCarte).getVerso());
            boutonChange.setText("Recto");
            cote = 1;
        }
        else{
            affichageCarte.setText(cartesEtude.get(indexCarte).getRecto());
            boutonChange.setText("Verso");
            cote = 0;
        }
    }

    /**
     * Passe à la carte suivante
     */
    public void carteSuivante(){
        cartesEtude = listener.getCartesEtude();
        indexCarte=indexRandom();
        affichageCarte.setText(cartesEtude.get(indexCarte).getRecto());
        if (cote == 1){
            changeCote();
            }
    }

    /**
     * Retourne à la carte précédente
     */
    public void cartePrecedente(){
        cartesEtude = listener.getCartesEtude();
        if (indexCarte > 0){
            indexCarte--;
            affichageCarte.setText(cartesEtude.get(indexCarte).getRecto());
            if (cote == 1){
                changeCote();
            }
        }
    }
    public void terminer() {
        MenuPrincipal.getINSTANCE().returnFromCarteEtudeToMenuPaquet();
        listener.saveCartes();
    }
    @FXML
    public void veryBad() {
        listener.tresMauvais(indexCarte);

    }

    public void bad() {
        listener.mauvais(indexCarte);
    }

    public void middle() {
        listener.moyen(indexCarte);
    }

    public void good() {
        listener.bon(indexCarte);
    }

    public void veryGood() {
        listener.tresBon(indexCarte);
    }
    public int indexRandom(){
        cartesEtude = listener.getCartesEtude();
        if(cartesLues()){
            indexCarte = ThreadLocalRandom.current().nextInt(0, cartesEtude.size());
            int apparition = ThreadLocalRandom.current().nextInt(0,(cartesEtude.get(indexCarte).getConnaissance()*10)+1);
            while(apparition>20){
                indexCarte = ThreadLocalRandom.current().nextInt(0, cartesEtude.size());
                apparition = ThreadLocalRandom.current().nextInt(0,(cartesEtude.get(indexCarte).getConnaissance()*10)+1);
            }
        }
        return indexCarte;
    }
    public boolean cartesLues() {
        cartesEtude = listener.getCartesEtude();
        indexCarte=0;
        while (indexCarte < cartesEtude.size()-1) {
            indexCarte++;
            if (cartesEtude.get(indexCarte).getConnaissance() == 0) {
                return false;
            }
        }
        return true;
    }
}
