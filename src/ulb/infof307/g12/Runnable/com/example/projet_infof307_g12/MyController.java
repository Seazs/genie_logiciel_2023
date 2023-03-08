package com.example.projet_infof307_g12;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class MyController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}