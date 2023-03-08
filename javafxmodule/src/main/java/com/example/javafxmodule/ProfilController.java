package com.example.javafxmodule;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ProfilController {

    public void retourMenuPaquet(ActionEvent event) throws Exception{

        FXMLLoader loader = new FXMLLoader(getClass().getResource("menuPaquet.fxml"));
        Parent menuPaquet = loader.load();

        // Créer une nouvelle scène à partir de la racine de la nouvelle page
        Scene menuPaquetScene = new Scene(menuPaquet);

        // Obtenir une référence au stage actuel et fermer la fenêtre
        Stage stageActuel = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stageActuel.setScene(menuPaquetScene);
    }
}
