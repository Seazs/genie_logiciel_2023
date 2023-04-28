package ulb.infof307.g12.view.cartes;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import ulb.infof307.g12.controller.javafx.connexion.MenuPrincipal;
import ulb.infof307.g12.model.Carte;
import ulb.infof307.g12.model.CarteTt;

import java.util.ArrayList;

public class CarteTTVueController {

    @FXML
    Button confirmButton;
    @FXML
    TextFlow showedText;
    String answer;
    TextField userAnswerField;
    Carte card;


    /**
     * Affichage de la carte
     * @param card
     */
    public void showCarte(CarteTt card){
        this.card = card;
        ArrayList<String> list = card.getTTInfo();
        Text textBefore = new Text(list.get(0)),
                textAfter = new Text(list.get(1));
        userAnswerField = new TextField();
        answer = list.get(2);

        showedText.getChildren().addAll(textBefore,userAnswerField,textAfter);
        MenuPrincipal.getINSTANCE().showCarteTT(card);
    }

    /**
     * Montre la réponse lorsqu'une réponse est validée
     * @param e
     */
    public void checkAnswer(ActionEvent e){
        MenuPrincipal.getINSTANCE().showTTResponse(userAnswerField.getText(),answer);
    }
}
