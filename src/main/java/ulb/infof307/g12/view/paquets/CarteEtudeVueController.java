package ulb.infof307.g12.view.paquets;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import lombok.Setter;
import ulb.infof307.g12.controller.listeners.CarteEtudeListener;
import ulb.infof307.g12.model.Carte;
import ulb.infof307.g12.model.Paquet;

import java.util.ArrayList;

public class CarteEtudeVueController{
    @Setter
    private CarteEtudeListener listener;
    @FXML
    private Button BoutonPrecedent;
    @FXML
    private Button BoutonSuivant;
    @FXML
    private Text affichageCarte;
    @FXML
    private Button boutonChange;
    private ArrayList<Carte> cartesEtude;



    public void chargerCarteEtudeVue(ArrayList<Carte> cartesEtude) {
        affichageCarte.setText(cartesEtude.get(0).getRecto());
    }

    public void changeCote(){
        cartesEtude = listener.getCartesEtude();
        affichageCarte.setText(cartesEtude.get(0).getVerso());
        boutonChange.setText("Recto");

    }
}
