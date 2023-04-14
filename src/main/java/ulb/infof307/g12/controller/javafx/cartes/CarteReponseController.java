package ulb.infof307.g12.controller.javafx.cartes;

import javafx.stage.Stage;
import ulb.infof307.g12.controller.javafx.BaseController;
import ulb.infof307.g12.view.cartes.CarteQCMVueController;
import ulb.infof307.g12.view.cartes.CarteReponseVueController;

import java.io.IOException;
import java.net.URL;

public class CarteReponseController extends BaseController {
    public CarteReponseController(Stage stage, String title,String result,String rightAnswer) throws IOException {
        super(stage, CarteReponseVueController.class.getResource("CarteReponse.fxml"), title);
        CarteReponseVueController controller = (CarteReponseVueController) super.controller;
        controller.showReponse(result,rightAnswer);
    }
}
