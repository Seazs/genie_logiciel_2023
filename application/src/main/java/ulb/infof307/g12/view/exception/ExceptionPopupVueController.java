package ulb.infof307.g12.view.exception;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class ExceptionPopupVueController {
    @FXML
    public AnchorPane errorPane;
    @FXML
    TextFlow errorView;

    private final Text errorMsg = new Text();

    /**
     * Ajout du texte sur une erreur
     * @param text texte
     */
    public void setErrorMsg(String text){
        errorMsg.setText(text);
        errorView.getChildren().add(errorMsg);
    }


}
