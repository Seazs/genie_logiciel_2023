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
import lombok.Setter;
import ulb.infof307.g12.controller.javafx.connection.MenuPrincipal;
import ulb.infof307.g12.controller.listeners.EditionVueListener;
import ulb.infof307.g12.model.Carte;

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


    public void chargerEditionVue() {
        System.out.println("VueController oui oui");
        ObservableList<Carte> data = FXCollections.<Carte>observableArrayList();
        ArrayList<Carte> cartes = listener.loadCartes();

        data.addAll(cartes) ;
        System.out.println(data);

        questionCol.setCellValueFactory(new PropertyValueFactory<Carte,String>("recto"));
        reponseCol.setCellValueFactory(new PropertyValueFactory<Carte,String>("verso"));

        tableQR.setItems(data);

    }

    @FXML
    void annulerEdition(ActionEvent event) {
        MenuPrincipal.getINSTANCE().returnToMenuPaquet();
    }

    @FXML
    void enregistrerPaquet(ActionEvent event) {
        System.out.println("enregistrerPaquet");
    }

}
