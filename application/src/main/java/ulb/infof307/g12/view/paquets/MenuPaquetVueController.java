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
import ulb.infof307.g12.controller.javafx.connexion.MenuPrincipal;
import ulb.infof307.g12.view.listeners.MenuPaquetListener;
import ulb.infof307.g12.model.Paquet;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class MenuPaquetVueController implements Initializable {
    @FXML
    private ListView<Paquet> paquetListView;
    List<Paquet> saveListPaquet = new ArrayList<>();

    @FXML
    private TextField RechercheLabel;

    @Setter
    private MenuPaquetListener listener;

    /**
     * Initialiser la vue du menu paquet
     * @param url adresse
     * @param rb ressource bundle
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Ajouter les paquets de cartes à la liste
        paquetListView.getItems().addAll(
                MenuPrincipal.getINSTANCE().getUserPaquets()
        );
        saveListPaquet.addAll(paquetListView.getItems());
        // Personnaliser l'affichage des éléments de la liste
        updateVisuelListePaquet();

        RechercheLabel.textProperty().addListener((observable, oldValue, newValue) -> {
            filtrageCategorie();
        });

    }

    /**
     * Charge le fichier FXML paquet de carte en chargeant les noms et catégories de chaque paquet
     */
    private void updateVisuelListePaquet() {
        paquetListView.setCellFactory(param -> new ListCell<Paquet>() {
            @Override
            protected void updateItem(Paquet item, boolean empty) {
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
     * Ouvre la view profil
     * @param event event
     */
    public void ouvrirProfil(ActionEvent event) {
        MenuPrincipal.getINSTANCE().openProfile();
    }

    public void ouvrirStore(ActionEvent event) {
        MenuPrincipal.getINSTANCE().openStore();
    }

    /**
     * Créer un nouveau paquet vide
     * @throws IOException Erreur de la création de nouveau paquet vide
     */
    public void creerPaquet() throws IOException {
        // Envoyer au listener
        Paquet nouveauPaquet = listener.creerPaquet() ;
        // Ajouter le nouveau paquet provenant du listener à la vue
        paquetListView.getItems().addAll(nouveauPaquet);
    }

    /**
     * Supprimer un paquet
     */
    public void deletePaquet(){
        Paquet paquet = paquetListView.getSelectionModel().getSelectedItem();
        if (paquet != null) {
            listener.supprimerPaquet(paquet);
            paquetListView.getItems().remove(paquet);
        }
    }

    /**
     * Ouvrir le menu d'édition de paquet
     */
    public void ouvrirEdition(ActionEvent event) {
        // Obtenir le paquet selectionné pour modifier et Envoyer demande de modification au listener
        listener.editerPaquet(paquetListView.getSelectionModel().getSelectedItem());
        // Après la modification, recharger la vue pour tenir compte des modifications
        rechargerListView();
    }

    /**
     * Recharger la vue contenant la liste de paquets de l'utilisateur
     */
    public void rechargerListView(){
        // Créer et initialiser un observableArrayList nécessaire pour l'utilisation d'un ListView
        ObservableList<Paquet> data = FXCollections.observableArrayList();
        data.addAll(MenuPrincipal.getINSTANCE().getUserPaquets()) ;
        // Injecter les données de l'observableArrayList dans la ListView
        paquetListView.setItems(data) ;

    }

    /**
     * Met à jour la liste visuel des paquets en fonction du filtre entré
     */
    public void filtrageCategorie() {
        String recherche = RechercheLabel.getText().toLowerCase();
        paquetListView.getItems().clear();
        saveListPaquet.forEach(
                paquet -> {
            boolean result = paquet.getCategories()
                    .stream()
                    .anyMatch(s -> s.toLowerCase().contains(recherche.toLowerCase()));
            if(result && ! paquetListView.getItems().contains(paquet))
                paquetListView.getItems().add(paquet);
        });
    }

    /**
     * Permet de changer vers une view "session d'étude"
     * @param event event
     */
    @FXML
    public void sessionEtude(ActionEvent event){
        if (listener!=null) {
            Paquet paquet = paquetListView.getSelectionModel().getSelectedItem();
            listener.CarteEtude(paquet);
        }
    }
}