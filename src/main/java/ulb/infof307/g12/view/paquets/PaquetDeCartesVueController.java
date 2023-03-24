package ulb.infof307.g12.view.paquets;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import lombok.Setter;
import ulb.infof307.g12.controller.javafx.connection.MenuPrincipal;
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

    public void modifierPaquet(ActionEvent actionEvent) {
        //TODO: Afficher interface d'édition + connecter au controller
        //TODO: Identifier à quel paquet ce bouton est associé
        Paquet paquet = new Paquet("Hello", "World");
        MenuPrincipal.getINSTANCE().afficherMenuEdition(paquet);
    }
}
