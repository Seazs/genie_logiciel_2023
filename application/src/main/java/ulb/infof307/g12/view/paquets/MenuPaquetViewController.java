package ulb.infof307.g12.view.paquets;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import lombok.Setter;
import ulb.infof307.g12.view.dto.PaquetDTO;
import ulb.infof307.g12.view.listeners.MenuPaquetListener;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MenuPaquetViewController implements Initializable {
    @FXML
    private ListView<PaquetDTO> paquetListView;


    @FXML
    private TextField searchLabel;

    @Setter
    private MenuPaquetListener listener;

    /**
     * Initialiser la vue du menu paquet
     * @param url adresse
     * @param rb ressource bundle
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Personnaliser l'affichage des éléments de la liste
        updateVisualPaquetList();
        searchLabel.textProperty().addListener((observable, oldValue, newValue) -> filterCategory());
    }

    /**
     * Charge le fichier FXML paquet de carte en chargeant les noms et catégories de chaque paquet
     */
    private void updateVisualPaquetList() {
        paquetListView.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(PaquetDTO item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                    return;
                }

                try {
                    // Charger la vue FXML pour la cellule
                    FXMLLoader loader = new FXMLLoader(MenuPaquetViewController.class.getResource("paquetOfCard.fxml"));
                    AnchorPane cellLayout = loader.load();
                    // Obtenir le contrôleur pour la vue FXML
                    PaquetOfCardsViewController controller = loader.getController();
                    // Définir les valeurs des éléments de la vue FXML à partir de l'objet PaquetDeCartes
                    controller.setPaquetOfCards(item);

                    // Définir la vue FXML comme élément de la cellule
                    setGraphic(cellLayout);
                } catch (IOException e) {
                    listener.showErrorPopup("Impossible de charger les textures du paquet de carte");
                }
            }
        });
    }

    /**
     * Ouvre la view profil
     * @param event event
     */
    public void openProfile(ActionEvent event) {
        listener.openProfile();
    }

    /**
     * Ouvre la view store
     * @param event event
     */
    public void openStore(ActionEvent event) {
        listener.openStore();
    }

    /**
     * Créer un nouveau paquet vide
     * @throws IOException Erreur de la création de nouveau paquet vide
     */
    public void createPaquet() throws IOException {
        PaquetDTO nouveauPaquet = listener.createPaquet() ;
        listener.editPaquet(nouveauPaquet);
        reloadListView();
    }

    /**
     * Supprimer un paquet
     */
    public void deletePaquet(){
        PaquetDTO paquet = paquetListView.getSelectionModel().getSelectedItem();
        if (paquet != null) {
            listener.deletePaquet(paquet);
            paquetListView.getItems().remove(paquet);
        }else {
            listener.showErrorPopup("Veuillez sélectionner un paquet");
        }
    }

    /**
     * Ouvrir le menu d'édition de paquet
     */
    public void openEdition(ActionEvent event) {
        PaquetDTO paquet = paquetListView.getSelectionModel().getSelectedItem(); // Obtenir le paquet selectionné pour modifier et Envoyer demande de modification au listener
        if (paquet != null) listener.editPaquet(paquet);
        else listener.showErrorPopup("Veuillez sélectionner un paquet");
        // Après la modification, recharger la vue pour tenir compte des modifications
        reloadListView();
    }

    /**
     * Recharger la vue contenant la liste de paquets de l'utilisateur
     */
    public void reloadListView(){
        // Créer et initialiser un observableArrayList nécessaire pour l'utilisation d'un ListView
        ObservableList<PaquetDTO> data = FXCollections.observableArrayList();
        data.addAll(listener.getPaquetDTOList()) ;
        // Injecter les données de l'observableArrayList dans la ListView
        paquetListView.setItems(data) ;

    }

    /**
     * Met à jour la liste visuel des paquets en fonction du filtre entré
     */
    public void filterCategory() {
        String search = searchLabel.getText().toLowerCase();
        paquetListView.getItems().clear();
        listener.filterPaquet(search);
        reloadListView();
    }

    /**
     * Permet de changer vers une view "session d'étude"
     * @param event event
     */
    @FXML
    public void studySession(ActionEvent event){
        if (listener!=null) {
            PaquetDTO paquet = paquetListView.getSelectionModel().getSelectedItem();
            listener.cardStudy(paquet);
        }
    }

    /**
     * Lorsque l'utilisateur clique sur le bouton "importer paquet", une fenêtre s'ouvre pour sélectionner un fichier
     * @param actionEvent event
     */
    public void importPaquet(ActionEvent actionEvent) {
        listener.importPaquet();
    }

    /**
     * Lorsque l'utilisateur clique sur le bouton "exporter paquet", une fenêtre s'ouvre pour sélectionner un fichier
     * @param actionEvent event
     */
    public void exportPaquet(ActionEvent actionEvent) {
        PaquetDTO paquet = paquetListView.getSelectionModel().getSelectedItem();

        if (paquet == null) {
            listener.showErrorPopup("Veuillez sélectionner un paquet");
        }else {
            listener.exportPaquet(paquet);
        }
    }

    /**
     * Ouvre la fenêtre de synchronisation
     * @param actionEvent event
     */
    public void sync(ActionEvent actionEvent) {
        listener.sync();
    }
}