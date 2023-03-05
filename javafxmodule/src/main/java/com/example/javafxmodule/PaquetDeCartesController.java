package com.example.javafxmodule;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class PaquetDeCartesController {
    @FXML
    private Label nomLabel;
    @FXML
    private Label categorieLabel;

    public void setPaquetDeCartes(Paquet paquetDeCartes) {
        nomLabel.setText(paquetDeCartes.getNom());
        categorieLabel.setText(paquetDeCartes.getCategorie());
    }
}
