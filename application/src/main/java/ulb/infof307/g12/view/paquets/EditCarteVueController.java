package ulb.infof307.g12.view.paquets;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class EditCarteVueController {

    @FXML
    private TextField questionTextField;
    @FXML
    private TextField reponseTextField;


    public String getQuestionTextField(){
        return questionTextField.getText();
    }

    public String getReponseTextField(){
        return reponseTextField.getText();
    }
}
