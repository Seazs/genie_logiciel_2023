package ulb.infof307.g12.controller.javafx.cartes;

import javafx.stage.Stage;
import ulb.infof307.g12.controller.javafx.BaseController;
import ulb.infof307.g12.model.Carte;
import ulb.infof307.g12.view.cartes.CarteQCMVueController;

import java.io.IOException;
import java.util.Objects;

public class CarteQCMController extends BaseController {
    /**
     * Controller de la carteQCM
     * @param stage stage
     * @param title titre
     * @param carte carte
     * @throws IOException exception
     */
    public CarteQCMController(Stage stage, String title,Carte carte) throws IOException {
        super(stage, CarteQCMVueController.class.getResource("CarteQCM.fxml"), title);
        if (!Objects.equals(carte.getType(), "qcm")) throw new IllegalArgumentException();
        CarteQCMVueController controller = (CarteQCMVueController) super.controller;
        controller.showCarte(carte);
    }
}
