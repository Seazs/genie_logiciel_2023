package ulb.infof307.g12.view.paquets;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import lombok.Setter;
import ulb.infof307.g12.controller.javafx.connexion.MenuPrincipal;
import ulb.infof307.g12.controller.listeners.EditionVueListener;
import ulb.infof307.g12.model.Carte;
import ulb.infof307.g12.model.Paquet;

import java.io.IOException;
import java.util.ArrayList;

public class EditionVueController {
    @FXML
    private TableColumn<Carte, String> reponseCol;
    @FXML
    private Button ajouterCarte;
    @FXML
    private TextField categoriePaquetTextField;
    @FXML
    private Button enregistrerBouton;
    @FXML
    private TextField nomPaquetTextField;
    @FXML
    private TableColumn<Carte, String> questionCol;
    @FXML
    private TextField questionTextField;
    @FXML
    private TextField reponseTextField;
    @FXML
    private Button retourBouton;
    @FXML
    private TableView<Carte> tableQR;
    @Setter
    private EditionVueListener listener;


    public void chargerEditionVue(Paquet paquet) {
        categoriePaquetTextField.setText(paquet.getCategories().get(0));
        nomPaquetTextField.setText(paquet.getNom());

        questionCol.setCellValueFactory(new PropertyValueFactory<Carte,String>("recto"));
        reponseCol.setCellValueFactory(new PropertyValueFactory<Carte,String>("verso"));

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


        reloadTable();

    }

    @FXML
    void annulerEdition(ActionEvent event) {
        MenuPrincipal.getINSTANCE().returnFromEditionToMenuPaquet();
    }

    @FXML
    void ajouterCarte(ActionEvent event){
        String recto = questionTextField.getText();
        String verso = reponseTextField.getText() ;
        listener.ajouterCarte(recto, verso);
        questionTextField.clear();
        reponseTextField.clear();
        reloadTable();
    }

    void reloadTable(){
        ObservableList<Carte> data = FXCollections.<Carte>observableArrayList();
        ArrayList<Carte> cartes = listener.loadCartes();
        data.addAll(cartes) ;
        tableQR.setItems(data);
    }

    @FXML
    void enregistrerPaquet() {
        String nouveauNom = nomPaquetTextField.getText() ;
        String nouvelleCategorie = categoriePaquetTextField.getText() ;
        listener.enregistrerPaquet(nouveauNom, nouvelleCategorie);
        MenuPrincipal.getINSTANCE().returnFromEditionToMenuPaquet();
    }

}
