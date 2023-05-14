package ulb.infof307.g12.view.store;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import lombok.Setter;
import ulb.infof307.g12.controller.javafx.connexion.MenuPrincipal;
import ulb.infof307.g12.view.dto.PaquetDTO;
import ulb.infof307.g12.view.listeners.StoreVueListener;
import ulb.infof307.g12.view.paquets.MenuPaquetVueController;
import ulb.infof307.g12.view.paquets.PaquetDeCartesVueController;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class StoreVueController implements Initializable{


    @Setter
    private StoreVueListener listener;

    @FXML
    private ListView<PaquetDTO> storePaquetListView;
    @FXML
    private ListView<PaquetDTO> mesPaquetListView;
    @FXML
    private TextField RechercheLabel;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Personnaliser l'affichage des éléments de la liste
        updateVisuelListeViewPaquet(mesPaquetListView);
        updateVisuelListeViewPaquet(storePaquetListView);

        RechercheLabel.textProperty().addListener((observable, oldValue, newValue) -> filtrageCategorie());

    }


    /**
     * Charge le fichier FXML paquet de carte en chargeant les noms et catégories de chaque paquet
     * @param paquetListView liste des paquets à afficher
     */
    private void updateVisuelListeViewPaquet(ListView<PaquetDTO> paquetListView) {
        paquetListView.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(PaquetDTO item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    try {
                        // Charger la vue FXML pour la cellule
                        FXMLLoader loader = new FXMLLoader(MenuPaquetVueController.class.getResource("paquetDeCarte.fxml"));
                        AnchorPane cellLayout = loader.load();

                        // Obtenir le contrôleur pour la vue FXML
                        PaquetDeCartesVueController controller = loader.getController();

                        // Définir les valeurs des éléments de la vue FXML à partir de l'objet PaquetDeCartes
                        controller.setPaquetDeCartes(item);

                        // Définir la vue FXML comme élément de la cellule
                        setGraphic(cellLayout);
                    } catch (IOException e) {
                        MenuPrincipal.getINSTANCE().showErrorPopup("Impossible de charger les textures du paquet de carte");
                    }
                }
            }
        });
    }

    /**
     * Met à jour la liste visuel des paquets en fonction du filtre entré
     */
    public void filtrageCategorie() {
        String recherche = RechercheLabel.getText().toLowerCase();
        storePaquetListView.getItems().clear();
        storePaquetListView.getItems().addAll(
                listener.filterPaquet(recherche)
        );
    }

    /**
     * Retourne au menu précédent
     */
    public void retourMenuPaquet() {
        MenuPrincipal.getINSTANCE().returnFromStoreToMenuPaquet();
    }

    /**
     * Envoie à  storeController  le paquet sélectionné pour le telecharger
     */
    public void downloadPaquet(){
        PaquetDTO paquet = storePaquetListView.getSelectionModel().getSelectedItem();
        listener.downloadPaquet(paquet);
        rechargerListView();
    }

    /**
     * Envoie à  storeController  le paquet sélectionné pour l’uploader
     */
    public void uploadPaquet() throws IOException {
        PaquetDTO paquet = mesPaquetListView.getSelectionModel().getSelectedItem();
        listener.uploadPaquet(paquet);
        refresh();
    }

    /**
     * Envoie l’ordre de refresh à storeController
     */
    private void refresh(){

        storePaquetListView.getItems().setAll(
                listener.getStorePaquets()
        );
    }

    /**
     * Recharger la vue contenant la liste de paquets de l'utilisateur
     */
    public void rechargerListView(){
        // Créer et initialiser un observableArrayList nécessaire pour l'utilisation d'un ListView
        ObservableList<PaquetDTO> data = FXCollections.observableArrayList();
        data.addAll(listener.getUserPaquets()) ;
        // Injecter les données de l'observableArrayList dans la ListView
        mesPaquetListView.setItems(data) ;
        refresh();
    }

    /**
     * Supprime un paquet du store
     */
    public void deletePaquet(){
        PaquetDTO paquet = storePaquetListView.getSelectionModel().getSelectedItem();
        listener.deletePaquetStore(paquet);
        rechargerListView();
    }
}