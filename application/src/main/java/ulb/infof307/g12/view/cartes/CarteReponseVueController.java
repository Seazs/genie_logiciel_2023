package ulb.infof307.g12.view.cartes;

import javafx.fxml.FXML;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;


public class CarteReponseVueController {
    @FXML
    TextFlow resultText, rightAnswerText;

    /**
     * Affichage de la réponse avec le résultat et la bonne réponse
     * @param result
     * @param rightAnswer
     */
    public void showReponse(String result, String rightAnswer){
        if(result == null)
            result = "";
        Text resulttxt = new Text("Votre réponse est " + result);
        Text rightAnswertxt = new Text("Le bon résultat est " + rightAnswer);
        resultText.getChildren().addAll(resulttxt);
        rightAnswerText.getChildren().addAll(rightAnswertxt);
    }
}
