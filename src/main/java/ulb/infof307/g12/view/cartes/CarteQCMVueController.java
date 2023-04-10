package ulb.infof307.g12.view.cartes;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import lombok.Setter;
import ulb.infof307.g12.controller.javafx.cartes.CarteQCMController;
import ulb.infof307.g12.controller.listeners.QCMListener;
import ulb.infof307.g12.model.Carte;

public class CarteQCMVueController {
    @FXML
    TextFlow questionText;
    @FXML
    ListView reponsesList;
    private Carte carte;
    @Setter
    private QCMListener listener;
    public void showCarte(Carte carte){
        this.carte = carte;
        Text text = new Text(carte.getRecto());
        questionText.getChildren().addAll(text);
    }

}
