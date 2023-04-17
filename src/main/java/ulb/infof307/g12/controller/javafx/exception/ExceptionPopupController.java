package ulb.infof307.g12.controller.javafx.exception;

import javafx.stage.Stage;
import lombok.Setter;
import ulb.infof307.g12.controller.javafx.BaseController;
import ulb.infof307.g12.view.cartes.CarteReponseVueController;
import ulb.infof307.g12.view.exception.ExceptionPopupVueController;

import java.io.IOException;
import java.net.URL;

public class ExceptionPopupController extends BaseController {


    /**
     * Controller des exceptions des pop up
     * @param stage
     * @throws IOException
     */
    public ExceptionPopupController(Stage stage) throws IOException {
        super(stage, CarteReponseVueController.class.getResource("CarteReponse.fxml"), "Erreur !");
    }

    /**
     * Création d'une erreur
     * @param msg
     */
    public void createError(String msg){
        ExceptionPopupVueController controller = (ExceptionPopupVueController) super.controller;
        controller.setErrorMsg(msg);
        this.show();
    }

}
