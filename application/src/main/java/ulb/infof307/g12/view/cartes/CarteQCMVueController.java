package ulb.infof307.g12.view.cartes;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import ulb.infof307.g12.controller.javafx.connexion.MenuPrincipal;


import java.util.ArrayList;
import java.util.Collections;

public class CarteQCMVueController {
    @FXML
    TextFlow questionText;
    @FXML
    ListView<String> reponsesList;
    private String response;

    /**
     * Affichage des cartes
     */
    public void showCarte(String question, String[] propositions, String answer){
        Text text = new Text(question);
        ArrayList<String> list = new ArrayList<>();
        Collections.addAll(list, propositions);
        questionText.getChildren().add(text);
        reponsesList.getItems().addAll(list.stream().toList().subList(1, list.size()-1));
        response = answer;


    }

    /**
     * Effectue l'event lorsqu'il y a un clic
     * @param e event
     */
    public void onClick(ActionEvent e){
        String userReponse = reponsesList.getSelectionModel().getSelectedItem();
        MenuPrincipal.getINSTANCE().showQCMResponse(userReponse,response);
    }
}
