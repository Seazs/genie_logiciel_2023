package ulb.infof307.g12.view.paquets;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import ulb.infof307.g12.view.dto.PaquetDTO;

import java.util.List;

public class PaquetOfCardsViewController {
    @FXML
    private Label nomLabel;
    @FXML
    private Label categorieLabel;



    /**
     * Applique le nom et les catégories aux labels associés
     * @param paquet le paquet de cartes
     */
    public void setPaquetOfCards(PaquetDTO paquet) {
        nomLabel.setText(paquet.nom());
        List<String> categories = paquet.categories();

        StringBuilder textCategories = new StringBuilder();
        for(int i = 0; i<categories.size(); i++){

            textCategories.append(categories.get(i));

            if (i != categories.size()-1)
                textCategories.append(", ");
        }

        categorieLabel.setText(textCategories.toString());
    }
}
