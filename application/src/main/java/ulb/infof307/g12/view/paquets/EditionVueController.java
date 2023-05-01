package ulb.infof307.g12.view.paquets;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import lombok.Setter;
import ulb.infof307.g12.controller.javafx.connexion.MenuPrincipal;
import ulb.infof307.g12.controller.listeners.EditionVueListener;
import ulb.infof307.g12.model.Carte;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class EditionVueController{

    @FXML
    private TableColumn<Carte, String> reponseCol;
    @FXML
    private TextField categoriePaquetTextField;
    @FXML
    private TextField nomPaquetTextField;
    @FXML
    private TableColumn<Carte, String> questionCol;
    @FXML
    private TextField rep1, rep2, rep3, reponsettTextField, questionTextField, reponseTextField;
    @FXML
    private TableView<Carte> tableQR;
    @FXML
    private ChoiceBox<String> typechoix;
    @Setter
    private EditionVueListener listener;

    /**
     * Charge la vue du menu d'édition avec les informations existantes du paquet
     *
     * @param name nom du paquet modifié
     */
    public void chargerEditionVue(String name) {

        categoriePaquetTextField.setPromptText("Catégorie");
        nomPaquetTextField.setText(name);

        questionCol.setCellValueFactory(new PropertyValueFactory<Carte, String>("recto"));
        reponseCol.setCellValueFactory(new PropertyValueFactory<Carte, String>("verso"));

        questionCol.setEditable(true);
        reponseCol.setEditable(true);

        questionCol.setCellFactory(TextFieldTableCell.forTableColumn());
        reponseCol.setCellFactory(TextFieldTableCell.forTableColumn());

        questionCol.setOnEditCommit(event -> {
            Carte carte = event.getRowValue();
            carte.editRecto(event.getNewValue());
        });

        reponseCol.setOnEditCommit(event -> {
            Carte carte = event.getRowValue();
            carte.editVerso(event.getNewValue());
        });
        setChoicebox();
        reloadTable();
    }

    /**
     * Fonction qui change le type d'édition de carte en fonction du type de carte
     * @param value quand on valide le choix de la checkbox

     */
    void switchType(String value) throws IOException {
        switch (value) {
            case "QCM" -> showQcm();
            case "Simple" -> showQR();
            case "Texte à trous" -> showTt();
            default -> {
            }
        }
    }

    /**
     * Fonction qui affiche les éléments pour l'édition d'une carte QCM
     */
    void showQcm(){
        rep1.setVisible(true);
        rep2.setVisible(true);
        rep3.setVisible(true);
        reponseTextField.setVisible(true);
        questionTextField.setVisible(true);
        reponsettTextField.setVisible(false);
        questionTextField.promptTextProperty().setValue("Question");
        reponseTextField.promptTextProperty().setValue("Réponse");
    }

    /**
     * Fonction qui affiche les éléments pour l'édition d'une carte QR
     */
    void showQR(){
        rep1.setVisible(false);
        rep2.setVisible(false);
        rep3.setVisible(false);
        reponseTextField.setVisible(true);
        questionTextField.promptTextProperty().setValue("Question");
        reponseTextField.promptTextProperty().setValue("Réponse");
        questionTextField.setVisible(true);
        reponsettTextField.setVisible(false);
    }

    /**
     * Fonction qui affiche les éléments pour l'édition d'une carte Texte à trous
     */
    void showTt(){
        rep1.setVisible(false);
        rep2.setVisible(false);
        rep3.setVisible(false);
        reponseTextField.setVisible(true);
        reponseTextField.promptTextProperty().setValue("Fin de phrase");
        questionTextField.setVisible(true);
        questionTextField.promptTextProperty().setValue("Début de phrase");
        reponsettTextField.setVisible(true);
    }

    /**
     * Revenir sur le menu précedent sans sauvegarder les modifications
     */
    @FXML
    void annulerEdition(ActionEvent event) {
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
     * Recharger la table de cartes du parquet
     */
    void reloadTable() {
        // Création et initialisation d'un observableArrayList nécessaire pour l'usage du tableau
        ObservableList<Carte> data = FXCollections.<Carte>observableArrayList();
        ArrayList<Carte> cartes = listener.loadCartes();
        data.addAll(cartes);
        // Injecter l'observableArrayList dans la table
        tableQR.setItems(data);
    }

    /**
     * Sauvergader les modifications du paquet et revenir sur le menu précedent
     */
    @FXML
    void enregistrerPaquet() {
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
        typechoix.getItems().addAll("Simple", "QCM", "Texte à trous");
        typechoix.setValue("Simple");
        typechoix.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            // Appel de la fonction switchtype avec la nouvelle valeur sélectionnée en paramètre
            try {
                switchType(newValue);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

    }
}