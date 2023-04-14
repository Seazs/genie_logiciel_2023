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



    public void chargerCarteEtudeVue(ArrayList<Carte> cartesEtude) {
        affichageCarte.setText(cartesEtude.get(0).getRecto());
    }

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
    public void carteSuivante(){
        cartesEtude = listener.getCartesEtude();
        if (indexCarte < cartesEtude.size()-1){
            indexCarte++;
            int i=indexRandom();
            affichageCarte.setText(cartesEtude.get(indexCarte).getRecto());
            if (cote == 1){
                changeCote();
            }
            if (indexCarte == cartesEtude.size()-1){
                boutonSuivant.setText("Terminer");
            }
        }
    }
    public void cartePrecedente(){
        cartesEtude = listener.getCartesEtude();
        if (indexCarte > 0){
            indexCarte--;
            affichageCarte.setText(cartesEtude.get(indexCarte).getRecto());
            if (cote == 1){
                changeCote();
            }
        }
        listener.saveCartes();
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
        int randomNum = ThreadLocalRandom.current().nextInt(0, cartesEtude.size());
        return randomNum;
    }
}
