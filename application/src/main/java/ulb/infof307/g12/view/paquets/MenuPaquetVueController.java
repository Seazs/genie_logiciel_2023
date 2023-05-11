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
import ulb.infof307.g12.view.dto.PaquetDTO;
import ulb.infof307.g12.view.listeners.MenuPaquetListener;
import javax.swing.*;
import java.awt.* ;
import java.io.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MenuPaquetVueController implements Initializable {
    @FXML
    private ListView<PaquetDTO> paquetListView;


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
        PaquetDTO nouveauPaquet = listener.creerPaquet() ;
        listener.editerPaquet(nouveauPaquet);
        rechargerListView();
        // Ajouter le nouveau paquet provenant du listener à la vue
        //paquetListView.getItems().addAll(nouveauPaquet);
    }

    /**
     * Supprimer un paquet
     */
    public void deletePaquet(){
        PaquetDTO paquet = paquetListView.getSelectionModel().getSelectedItem();
        if (paquet != null) {
            listener.supprimerPaquet(paquet);
            paquetListView.getItems().remove(paquet);
        }else {
            MenuPrincipal.getINSTANCE().showErrorPopup("Veuillez sélectionner un paquet"); // TO DO
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
        ObservableList<PaquetDTO> data = FXCollections.observableArrayList();
        data.addAll(listener.getPaquetDTOList()) ;
        // Injecter les données de l'observableArrayList dans la ListView
        paquetListView.setItems(data) ;

    }

    /**
     * Met à jour la liste visuel des paquets en fonction du filtre entré
     */
    public void filtrageCategorie() {
        String recherche = RechercheLabel.getText().toLowerCase();
        paquetListView.getItems().clear();
        listener.filterPaquet(recherche);
    }

    /**
     * Permet de changer vers une view "session d'étude"
     * @param event event
     */
    @FXML
    public void sessionEtude(ActionEvent event){
        if (listener!=null) {
            PaquetDTO paquet = paquetListView.getSelectionModel().getSelectedItem();
            listener.CarteEtude(paquet);
        }
    }

    /**
     * Lorsque l'utilisateur clique sur le bouton "importer paquet", une fenêtre s'ouvre pour sélectionner un fichier
     * @param actionEvent event
     */
    public void importPaquet(ActionEvent actionEvent) {
        FileDialog fileDialog = new FileDialog((JFrame) null, "Select a File to import", FileDialog.LOAD);
        // show the file dialog
        fileDialog.setVisible(true);
        fileDialog.setFilenameFilter((dir, name) -> name.endsWith(".json"));
        // get the selected file
        File[] files = fileDialog.getFiles();
        if (files.length == 1) {
            listener.importPaquet(files[0]);
            rechargerListView();}
    }

    /**
     * Lorsque l'utilisateur clique sur le bouton "exporter paquet", une fenêtre s'ouvre pour sélectionner un fichier
     * @param actionEvent event
     */
    public void exportPaquet(ActionEvent actionEvent) {
        FileDialog fileDialog = new FileDialog((JFrame) null, "Select a File to import", FileDialog.SAVE);
        // show the file dialog
        fileDialog.setVisible(true);
        PaquetDTO paquet = paquetListView.getSelectionModel().getSelectedItem();
        if (paquet != null) {
            listener.exportPaquet(paquet, fileDialog.getDirectory() + fileDialog.getFile());
        }
    }
}