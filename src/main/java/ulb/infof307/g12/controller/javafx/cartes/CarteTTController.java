package ulb.infof307.g12.controller.javafx.cartes;

import javafx.stage.Stage;
import ulb.infof307.g12.controller.javafx.BaseController;
import ulb.infof307.g12.model.Carte;
import ulb.infof307.g12.view.cartes.CarteQCMVueController;
import ulb.infof307.g12.view.cartes.CarteTTVueController;

import java.io.IOException;
import java.util.Objects;

public class CarteTTController extends BaseController {

    /**
     * Controller de la carte texte à trous
     * @param stage
     * @param title
     * @param card
     * @throws IOException
     */
    public CarteTTController(Stage stage, String title, Carte card) throws IOException {
        super(stage, CarteTTVueController.class.getResource("CarteTT.fxml"), title);
        if (!Objects.equals(card.getType(), "tt")) throw new IllegalArgumentException();
        CarteTTVueController controller = (CarteTTVueController) super.controller;
        controller.showCarte(card);
    }
}
