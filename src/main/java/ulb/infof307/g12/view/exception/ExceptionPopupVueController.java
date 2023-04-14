package ulb.infof307.g12.view.exception;

import javafx.fxml.FXML;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class ExceptionPopupVueController {

    @FXML
    TextFlow errorView;

    private Text errorMsg = new Text();

    public void setErrorMsg(String text){
        errorMsg.setText(text);
    }


}
