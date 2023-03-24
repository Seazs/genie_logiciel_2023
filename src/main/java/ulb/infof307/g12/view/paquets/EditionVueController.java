package ulb.infof307.g12.view.paquets;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import lombok.Setter;
import ulb.infof307.g12.controller.javafx.connection.MenuPrincipal;
import ulb.infof307.g12.controller.listeners.EditionVueListener;

public class EditionVueController {
    @FXML
    private TableColumn<?, ?> ReponseCol;
    @FXML
    private Button ajouterCarte;
    @FXML
    private TextField categoriePaquetTextField;
    @FXML
    private Button enregistrerBouton;
    @FXML
    private TextField nomPaquetTextField;
    @FXML
    private TableColumn<?, ?> questionCol;
    @FXML
    private TextField questionTextField;
    @FXML
    private TextField reponseTextField;
    @FXML
    private Button retourBouton;
    @FXML
    private TableView<?> tableQR;
    @Setter
    private EditionVueListener listener;

    @FXML
    void annulerEdition(ActionEvent event) {
        MenuPrincipal.getINSTANCE().returnToMenuPaquet();
    }

    @FXML
    void enregistrerPaquet(ActionEvent event) {

    }

}
