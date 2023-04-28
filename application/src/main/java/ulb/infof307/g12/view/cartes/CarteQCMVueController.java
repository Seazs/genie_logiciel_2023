package ulb.infof307.g12.view.cartes;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import ulb.infof307.g12.controller.javafx.connexion.MenuPrincipal;
import ulb.infof307.g12.model.CarteQcm;


import java.io.IOException;
import java.util.ArrayList;

public class CarteQCMVueController {
    @FXML
    TextFlow questionText;
    @FXML
    ListView<String> reponsesList;
    private String response;

    /**
     * Affichage des cartes
     * @param carte
     */
    public void showCarte(CarteQcm carte){
        ArrayList<String> list = carte.getQCMInfo();
        Text text = new Text(list.get(0));
        questionText.getChildren().add(text);
        reponsesList.getItems().addAll(list.stream().toList().subList(1, list.size()-1));
        response = list.get(list.size()-1);
        MenuPrincipal.getINSTANCE().showCarteQCM(carte);
    }

    /**
     * Effectue l'event lorsqu'il y a un clic
     * @param e
     */
    public void onClick(ActionEvent e){
        String userReponse = reponsesList.getSelectionModel().getSelectedItem();
        MenuPrincipal.getINSTANCE().showQCMResponse(userReponse,response);
    }

}
