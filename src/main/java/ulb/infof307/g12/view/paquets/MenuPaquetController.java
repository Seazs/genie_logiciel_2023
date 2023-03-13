package ulb.infof307.g12.view.paquets;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import ulb.infof307.g12.model.Paquet;
import ulb.infof307.g12.view.profiles.ProfilController;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MenuPaquetController implements Initializable {
    @FXML
    private ListView<Paquet> paquetListView;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Ajouter les paquets de cartes à la liste
        paquetListView.getItems().addAll(
                new Paquet("Paquet 1", "Catégorie 1"),
                new Paquet("Paquet 2", "Catégorie 2"),
                new Paquet("Paquet 3", "Catégorie 3")
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
                        FXMLLoader loader = new FXMLLoader(MenuPaquetController.class.getResource("paquetDeCarte.fxml"));
                        BorderPane cellLayout = loader.load();

                        // Obtenir le contrôleur pour la vue FXML
                        PaquetDeCartesController controller = loader.getController();

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
        FXMLLoader loader = new FXMLLoader(ProfilController.class.getResource("profil.fxml"));
        Parent nouvellePage = loader.load();

        // Créer une nouvelle scène à partir de la racine de la nouvelle page
        Scene nouvelleScene = new Scene(nouvellePage);

        // Obtenir une référence au stage actuel
        Button bouton = (Button) event.getSource();
        Stage stageActuel = (Stage) bouton.getScene().getWindow();

        // Définir la nouvelle scène sur le stage actuel et afficher le stage
        stageActuel.setScene(nouvelleScene);
        stageActuel.show();
    }
}