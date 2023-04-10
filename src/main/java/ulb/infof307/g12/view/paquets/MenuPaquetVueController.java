package ulb.infof307.g12.view.paquets;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import lombok.Setter;
import ulb.infof307.g12.controller.javafx.connection.MenuPrincipal;
import ulb.infof307.g12.controller.listeners.MenuPaquetListener;
import ulb.infof307.g12.controller.storage.GestionnairePaquet;
import ulb.infof307.g12.model.Carte;
import ulb.infof307.g12.model.Paquet;
import ulb.infof307.g12.model.Utilisateur;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class MenuPaquetVueController implements Initializable {
    @FXML
    private ListView<Paquet> paquetListView;

    @Setter
    private MenuPaquetListener listener ;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Ajouter les paquets de cartes à la liste
        paquetListView.getItems().addAll(
                MenuPrincipal.getINSTANCE().getUserPaquets()
        );

        // Personnaliser l'affichage des éléments de la liste
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
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public void ouvrirProfil(ActionEvent event) throws Exception {
        MenuPrincipal.getINSTANCE().openProfile();
    }

    public void creerPaquet() throws IOException {
        Paquet nouveauPaquet = listener.creerPaquet() ;
        ajouterPaquetAListe(nouveauPaquet);
    }

    public void ajouterPaquetAListe(Paquet paquet){
        paquetListView.getItems().addAll(paquet);
    }

    public void ouvrirEdition(ActionEvent event) throws Exception {
        Paquet paquet = paquetListView.getSelectionModel().getSelectedItem();
        listener.editerPaquet(paquet);
        rechargerListView();
    }

    public void rechargerListView(){
        ObservableList<Paquet> data = FXCollections.<Paquet>observableArrayList();
        data.addAll(MenuPrincipal.getINSTANCE().getUserPaquets()) ;
        paquetListView.setItems(data) ;

    }

}
