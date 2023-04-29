package ulb.infof307.g12.view.paquets;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.AnchorPane;
import lombok.Setter;
import ulb.infof307.g12.controller.javafx.connexion.MenuPrincipal;
import ulb.infof307.g12.controller.listeners.EditionVueListener;
import ulb.infof307.g12.model.Carte;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;

public class EditionVueController{

    @FXML
    private AnchorPane editionqr;
    @FXML
    private TableColumn<Carte, String> reponseCol;
    @FXML
    private TextField categoriePaquetTextField;
    @FXML
    private TextField nomPaquetTextField;
    @FXML
    private TableColumn<Carte, String> questionCol;
    @FXML
    private TextField question,rep1, rep2, rep3,rep,
            debutTextField, finTextField,reponsettTextField;

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
        Optional<Node> view = listener.changeCarteType(value);
        if(view.isPresent()){
            editionqr.getChildren().clear();
            editionqr.getChildren().add(view.get());
        }
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
    void ajouterCarte(ActionEvent event) {
        if (Objects.equals(typechoix.getValue(), "Simple")) {
            addCarteQr();
        } else if (Objects.equals(typechoix.getValue(), "QCM")) {
            addCarteQcm();
        } else if (Objects.equals(typechoix.getValue(), "Texte à trous")) {
            addCarteTt();
        }

    }

    /**
     * Ajoute une carte Texte à trou dans le paquet
     */
    private void addCarteTt() {
        // Prendre les informations
        /*String recto = questionTextField.getText();
        String verso = reponseTextField.getText();
        // Envoyer au listener
        listener.ajouterCarteTT(recto, verso);
        // Nettoyer les entrées
        questionTextField.clear();
        reponseTextField.clear();
        // Recharger la table*/
        reloadTable();
    }

    /**
     * Ajoute une carte qcm dans le paquet
     */
    private void addCarteQcm() {
        /*// Prendre les informations
        String recto = questionTextField.getText();
        String verso = reponseTextField.getText();
        // Envoyer au listener
        listener.ajouterCarteQCM(recto, verso);
        // Nettoyer les entrées
        questionTextField.clear();
        reponseTextField.clear();
        // Recharger la table*/
        reloadTable();
    }

    /**
     * Ajout d'une carte question réponse dans le paquet
     */
    private void addCarteQr() {
        // Prendre les informations
        // Envoyer au listener
        listener.ajouterCarte();
        // Nettoyer les entrées
        // Recharger la table
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
        listener.enregistrerPaquet(nouveauNom, nouvelleCategorie);
        // Revenir sur le menu principal
        MenuPrincipal.getINSTANCE().returnFromEditionToMenuPaquet();
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