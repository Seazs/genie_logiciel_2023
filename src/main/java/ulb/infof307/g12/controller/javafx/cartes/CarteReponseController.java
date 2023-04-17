package ulb.infof307.g12.controller.javafx.cartes;

import javafx.stage.Stage;
import ulb.infof307.g12.controller.javafx.BaseController;
import ulb.infof307.g12.view.cartes.CarteReponseVueController;

import java.io.IOException;

public class CarteReponseController extends BaseController {
    /**
     * Controller de la carte Réponse
     * @param stage stage
     * @param title titre
     * @param result résultat
     * @param rightAnswer bonne réponse
     * @throws IOException exception
     */
    public CarteReponseController(Stage stage, String title,String result,String rightAnswer) throws IOException {
        super(stage, CarteReponseVueController.class.getResource("CarteReponse.fxml"), title);
        CarteReponseVueController controller = (CarteReponseVueController) super.controller;
        controller.showReponse(result,rightAnswer);
    }
}
