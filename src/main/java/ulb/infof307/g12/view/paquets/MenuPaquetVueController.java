package ulb.infof307.g12.view.paquets;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import lombok.Setter;
import ulb.infof307.g12.controller.javafx.connection.MenuPrincipal;
import ulb.infof307.g12.controller.javafx.paquets.MenuPaquetController;
import ulb.infof307.g12.controller.listeners.MenuPaquetListener;
import ulb.infof307.g12.model.Paquet;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Stream;

public class MenuPaquetVueController implements Initializable {
    @FXML
    private ListView<Paquet> paquetListView;
    List<Paquet> saveListPaquet = new ArrayList<>();

    @FXML
    private TextField RechercheLabel;

    @Setter
    private MenuPaquetListener listener;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Ajouter les paquets de cartes à la liste
        paquetListView.getItems().addAll(
                new Paquet("Paquet 1", "Catégorie 1","cat 2"),
                new Paquet("Paquet 2", "Catégorie 2","cat 3"),
                new Paquet("Paquet 3", "Cat 3"),
                new Paquet("Paquet 5", "Cat 2"),
                new Paquet("Paquet 6", "lala"),
                new Paquet("Paquet 7", "Catégorie 1"),
                new Paquet("Paquet 8", "Catégorie 3")
        );
        saveListPaquet.addAll(paquetListView.getItems());
        // Personnaliser l'affichage des éléments de la liste
        updateVisuelListePaquet();

        RechercheLabel.textProperty().addListener((observable, oldValue, newValue) -> {
            filtrageCategorie();
        });

    }

    /**
     * charge le fichier FXML paquet de carte en chargeant les noms et catégories de chaque paquet
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
                        BorderPane cellLayout = loader.load();

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

    public void setListener(MenuPaquetController menuPaquetController) {
    }

    /**
     * mets à jour la liste visuel des paquets en fonction du filtre entré
     *
     */

    public void filtrageCategorie() {
        String recherche = RechercheLabel.getText().toLowerCase();
        paquetListView.getItems().clear();
        saveListPaquet.stream().forEach(
                paquet -> {
            boolean result = paquet.getCategories()
                    .stream()
                    .anyMatch(s -> s.toLowerCase().contains(recherche.toLowerCase()));
            if(result && ! paquetListView.getItems().contains(paquet))
                paquetListView.getItems().add(paquet);

        });
    }

}