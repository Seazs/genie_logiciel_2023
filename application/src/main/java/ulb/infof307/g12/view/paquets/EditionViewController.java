package ulb.infof307.g12.view.paquets;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import lombok.Setter;
import ulb.infof307.g12.controller.javafx.connexion.MenuPrincipal;
import ulb.infof307.g12.view.dto.CardDTO;
import ulb.infof307.g12.view.listeners.EditionViewListener;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class EditionViewController {

    @FXML
    private TableColumn<CardDTO, String> reponseCol;
    @FXML
    private TableColumn<CardDTO, String> questionCol;
    @FXML
    private TextField categoriePaquetTextField;
    @FXML
    private TextField nomPaquetTextField;
    @FXML
    private TextField rep1, rep2, rep3, reponsettTextField, questionTextField, reponseTextField;
    @FXML
    private TableView<CardDTO> tableQR;
    @FXML
    private ChoiceBox<String> typechoix;
    @FXML
    private TextArea SpecialquestionField,SpecialreponseField;
    @Setter
    private EditionViewListener listener;

    /**
     * Charge la vue du menu d'édition avec les informations existantes du paquet
     *
     * @param name nom du paquet modifié
     */
    public void loadEditionView(String name) {

        categoriePaquetTextField.setPromptText("Catégorie");
        nomPaquetTextField.setText(name);

        questionCol.setCellValueFactory(data->new SimpleStringProperty(data.getValue().question()));
        reponseCol.setCellValueFactory(data->new SimpleStringProperty(data.getValue().reponse()));

        questionCol.setEditable(true);
        reponseCol.setEditable(true);

        questionCol.setCellFactory(TextFieldTableCell.forTableColumn());
        reponseCol.setCellFactory(TextFieldTableCell.forTableColumn());

        questionCol.setOnEditCommit(event -> {
            String newQuestion = event.getRowValue().question();
            listener.editQuestion(newQuestion);
        });

        reponseCol.setOnEditCommit(event -> {
            String newReponse = event.getRowValue().reponse();
            listener.editResponse(newReponse);
        });
        setChoicebox();
        reloadTable();
    }

    /**
     * Fonction qui change le type d'édition de carte en fonction du type de carte
     * @param value quand on valide le choix de la checkbox

     */
    void switchType(String value){
        switch (value) {
            case "QCM" -> showQcm();
            case "Simple" -> showQR();
            case "Texte à trous" -> showTt();
            case "Latex","HTML"-> showSpecial();
        }
    }

    /**
     * Fonction qui affiche les éléments pour l'édition d'une carte QCM
     */
    void showQcm(){
        tableQR.setVisible(true);
        rep1.setVisible(true);
        rep2.setVisible(true);
        rep3.setVisible(true);
        reponseTextField.setVisible(true);
        questionTextField.setVisible(true);
        reponsettTextField.setVisible(false);
        questionTextField.promptTextProperty().setValue("Question");
        reponseTextField.promptTextProperty().setValue("Réponse");
        SpecialquestionField.setVisible(false);
        SpecialreponseField.setVisible(false);
    }

    /**
     * Fonction qui affiche les éléments pour l'édition d'une carte QR
     */
    void showQR(){
        tableQR.setVisible(true);
        rep1.setVisible(false);
        rep2.setVisible(false);
        rep3.setVisible(false);
        reponseTextField.setVisible(true);
        questionTextField.promptTextProperty().setValue("Question");
        reponseTextField.promptTextProperty().setValue("Réponse");
        questionTextField.setVisible(true);
        reponsettTextField.setVisible(false);
        SpecialquestionField.setVisible(false);
        SpecialreponseField.setVisible(false);
    }

    /**
     * Fonction qui affiche les éléments pour l'édition d'une carte Texte à trous
     */
    void showTt(){
        tableQR.setVisible(true);
        rep1.setVisible(false);
        rep2.setVisible(false);
        rep3.setVisible(false);
        reponseTextField.setVisible(true);
        reponseTextField.promptTextProperty().setValue("Fin de phrase");
        questionTextField.setVisible(true);
        questionTextField.promptTextProperty().setValue("Début de phrase");
        reponsettTextField.setVisible(true);
        SpecialquestionField.setVisible(false);
        SpecialreponseField.setVisible(false);
    }
    /**
     * Fonction qui affiche les éléments pour l'édition d'une carte Special
     */
    void showSpecial(){
        rep1.setVisible(false);
        rep2.setVisible(false);
        rep3.setVisible(false);
        reponseTextField.setVisible(false);
        questionTextField.setVisible(false);
        reponsettTextField.setVisible(false);
        tableQR.setVisible(false);
        SpecialquestionField.setVisible(true);
        SpecialreponseField.setVisible(true);
        SpecialquestionField.clear();
        SpecialreponseField.clear();
    }

    /**
     * Revenir sur le menu précedent sans sauvegarder les modifications
     */
    @FXML
    void cancelEdition(ActionEvent event) {
        MenuPrincipal.getINSTANCE().returnFromEditionToMenuPaquet();
    }

    /**
     * Rajouter une carte au paquet
     */
    @FXML
    void addCard(ActionEvent event) {
        if (Objects.equals(typechoix.getValue(), "Simple")) {
            addCardQR();
        } else if (Objects.equals(typechoix.getValue(), "QCM")) {
            addCardQCM();
        } else if (Objects.equals(typechoix.getValue(), "Texte à trous")) {
            addCardTT();
        }
        else if (Objects.equals(typechoix.getValue(), "Latex")) {
            addCardSpecial("latex");
            typechoix.setValue("Simple");
            showQR();
        }
        else if (Objects.equals(typechoix.getValue(), "HTML")) {
            addCardSpecial("html");
            typechoix.setValue("Simple");
            showQR();
        }
    }

    /**
     * Ajoute une carte Texte à trou dans le paquet
     */
    private void addCardTT() {
        // Prendre les informations
        String debut = questionTextField.getText();
        String fin = reponseTextField.getText();
        String verso = reponsettTextField.getText();
        if (listener.checkTt(debut, fin, verso)){
            String recto = debut + "§" + fin;
            // Envoyer au listener
            listener.addCardTT(recto, verso);
            // Nettoyer les entrées
            questionTextField.clear();
            reponseTextField.clear();
            reponsettTextField.clear();
            // Recharger la table*/
            reloadTable();
        }
    }

    /**
     * Ajoute une carte qcm dans le paquet
     */
    private void addCardQCM() {
        // Prendre les informations
        String question = questionTextField.getText();
        String choice1 = rep1.getText();
        String choice2 = rep2.getText();
        String choice3 = rep3.getText();
        String verso = reponseTextField.getText();
        if (listener.checkQcm(question, choice1, choice2, choice3, verso)){
            String recto = question + "§" + choice1 + "§" + choice2 + "§" + choice3;
            // Envoyer au listener
            listener.addCardQCM(recto, verso);
            // Nettoyer les entrées
            questionTextField.clear();
            reponseTextField.clear();
            rep1.clear();
            rep2.clear();
            rep3.clear();
            // Recharger la table*/
            reloadTable();
        }
    }


    /**
     * Ajout d'une carte question réponse dans le paquet
     */
    private void addCardQR() {
        String recto = questionTextField.getText();
        String verso = reponseTextField.getText();
        listener.addCard(recto, verso);
        questionTextField.clear();
        reponseTextField.clear();
        reloadTable();
    }

    /**
     * Ajout d'une carte question réponse dans le paquet
     */
    private void addCardSpecial(String lang) {
        String recto = SpecialquestionField.getText();
        String verso = SpecialreponseField.getText();
        listener.addCardSpecial(recto, verso,lang);
        questionTextField.clear();
        reponseTextField.clear();
        reloadTable();
    }

    /**
     * Recharger la table de cartes du parquet
     */
    void reloadTable() {
        // Création et initialisation d'un observableArrayList nécessaire pour l'usage du tableau
        ObservableList<CardDTO> observed = FXCollections.observableArrayList();
        List<CardDTO> data = listener.getData();
        observed.addAll(data);
        // Injecter l'observableArrayList dans la table
        tableQR.setItems(observed);
    }

    /**
     * Sauvergader les modifications du paquet et revenir sur le menu précedent
     */
    @FXML
    void savePaquet() {
        // Prendre les modifications du nom et la catégorie à rajouter
        String nouveauNom = nomPaquetTextField.getText();
        String nouvelleCategorie = categoriePaquetTextField.getText();
        // Envoyer au listener
        listener.savePaquet(nouveauNom, nouvelleCategorie);
    }

    /**
     * Mets les options possibles dans la choicebox
     */
    void setChoicebox() {
        typechoix.getItems().addAll("Simple", "QCM", "Texte à trous","Latex","HTML");
        typechoix.setValue("Simple");
        typechoix.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            // Appel de la fonction switchtype avec la nouvelle valeur sélectionnée en paramètre
            switchType(newValue);
        });

    }
}