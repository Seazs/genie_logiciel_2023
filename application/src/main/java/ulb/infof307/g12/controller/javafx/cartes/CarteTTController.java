package ulb.infof307.g12.controller.javafx.cartes;

import javafx.stage.Stage;
import ulb.infof307.g12.controller.javafx.BaseController;
import ulb.infof307.g12.model.CarteTt;
import ulb.infof307.g12.view.cartes.CarteTTVueController;

import java.io.IOException;
import java.util.Objects;

public class CarteTTController extends BaseController {

    /**
     * Controller de la carte texte à trous
     * @param stage stage
     * @param title titre
     * @param card carte
     * @throws IOException exceptions
     */
    public CarteTTController(Stage stage, String title, CarteTt card) throws IOException {
        super(stage, CarteTTVueController.class.getResource("CarteTT.fxml"), title);
        if (!Objects.equals(card.getType(), "tt")) throw new IllegalArgumentException();
        CarteTTVueController controller = (CarteTTVueController) super.controller;
        controller.showCarte(card.getBegin(),card.getEnd(),card.getAnswer());
    }

}
