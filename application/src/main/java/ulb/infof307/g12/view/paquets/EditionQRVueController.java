package ulb.infof307.g12.view.paquets;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import lombok.Setter;
import ulb.infof307.g12.controller.listeners.EditionVueListener;

public class EditionQRVueController {

    @FXML
    private TextField questionTextField, reponseTextField;

    public EditionQRVueController() {
        questionTextField = new TextField();
        reponseTextField = new TextField();
    }

    public String getQuestion() {
        System.out.println("Question: "+questionTextField.getText());
        return questionTextField.getText();
    }

    public String getReponse() {
        System.out.println("Reponse: "+reponseTextField.getText());
        return reponseTextField.getText();
    }

    public void clear(){
        questionTextField.clear();
        reponseTextField.clear();
    }
}
