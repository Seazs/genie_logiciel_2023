package ulb.infof307.g12.view.paquets;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import ulb.infof307.g12.model.Paquet;

public class PaquetDeCartesVueController {
    public Button jouerBouton;
    public Button modifierBouton;
    @FXML
    private Label nomLabel;
    @FXML
    private Label categorieLabel;


    public void setPaquetDeCartes(Paquet paquetDeCartes) {
        nomLabel.setText(paquetDeCartes.getNom());
        categorieLabel.setText(paquetDeCartes.getCategorie());
    }

}
