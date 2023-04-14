package ulb.infof307.g12.view.cartes;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import ulb.infof307.g12.controller.javafx.connexion.MenuPrincipal;
import ulb.infof307.g12.model.Carte;

import java.io.IOException;
import java.util.ArrayList;

public class CarteQCMVueController {
    @FXML
    TextFlow questionText;
    @FXML
    ListView<String> reponsesList;
    private String response;
    public void showCarte(Carte carte){
        ArrayList<String> list = carte.getQCMOrTTInfo();

        Text text = new Text(list.get(0));
        questionText.getChildren().addAll(text);
        reponsesList.getItems().addAll(list.stream().toList().subList(1, list.size()-1));
        response = list.get(list.size()-1);
    }

    public void onClick(ActionEvent e) throws IOException {
        String userReponse = reponsesList.getSelectionModel().getSelectedItem();
        MenuPrincipal.getINSTANCE().showQCMResponse(userReponse,response);
    }

}
