package ulb.infof307.g12.view.cartes;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import ulb.infof307.g12.controller.javafx.connexion.MenuPrincipal;

public class CarteTTVueController {

    @FXML
    Button confirmButton;
    @FXML
    TextFlow showedText;
    String answer;
    TextField userAnswerField;



    /**
     * @param debut debut de la phrase
     * @param fin fin de la phrase
     * @param reponse réponse
     * Affichage des cartes de type Tt
     */
    public void showCarte(String debut,String fin,String reponse){
        Text textBefore = new Text(debut),
                textAfter = new Text(fin);
        userAnswerField = new TextField();
        answer = reponse;
        showedText.getChildren().addAll(textBefore,userAnswerField,textAfter);
    }

    /**
     * Montre la réponse lorsqu'une réponse est validée
     * @param e
     */
    public void checkAnswer(ActionEvent e){
        MenuPrincipal.getINSTANCE().showTTResponse(userAnswerField.getText(),answer);
    }
}
