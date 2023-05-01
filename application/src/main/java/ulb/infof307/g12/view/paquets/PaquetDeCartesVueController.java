package ulb.infof307.g12.view.paquets;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import ulb.infof307.g12.model.Paquet;

import java.util.ArrayList;

public class PaquetDeCartesVueController {
    @FXML
    private Label nomLabel;
    @FXML
    private Label categorieLabel;



    /**
     * Applique le nom et les catégories aux labels associés
     * @param paquetDeCartes paquet de cartes
     */
    public void setPaquetDeCartes(Paquet paquetDeCartes) {
        nomLabel.setText(paquetDeCartes.getNom());
        StringBuilder textCategories = new StringBuilder();
        ArrayList<String> categories = paquetDeCartes.getCategories();
        for(int i = 0; i<categories.size(); i++){
            if (i != categories.size()-1) {
                textCategories.append(categories.get(i)).append(", ");
            }else{
                textCategories.append(categories.get(i));
            }
        }
        categorieLabel.setText(textCategories.toString());
    }
}
