package ulb.infof307.g12.view.exception;

import javafx.fxml.FXML;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class ExceptionPopupVueController {

    @FXML
    TextFlow errorView;

    private final Text errorMsg = new Text();

    /**
     * Ajout du texte sur une erreur
     * @param text
     */
    public void setErrorMsg(String text){
        errorMsg.setText(text);
        errorView.getChildren().add(errorMsg);
    }


}
