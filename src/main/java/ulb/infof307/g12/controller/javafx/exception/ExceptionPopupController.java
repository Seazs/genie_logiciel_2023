package ulb.infof307.g12.controller.javafx.exception;

import javafx.stage.Stage;
import ulb.infof307.g12.controller.javafx.BaseController;
import ulb.infof307.g12.view.cartes.CarteReponseVueController;
import ulb.infof307.g12.view.exception.ExceptionPopupVueController;

import java.io.IOException;

public class ExceptionPopupController extends BaseController {


    /**
     * Controller des exceptions des pop up
     * @param stage stage
     * @throws IOException exception
     */
    public ExceptionPopupController(Stage stage) throws IOException {
        super(stage, ExceptionPopupVueController.class.getResource("ExceptionPopup.fxml"), "Erreur !");
    }

    /**
     * Création d'une erreur
     * @param msg message d'erreur
     */
    public void createError(String msg){
        ExceptionPopupVueController controller = (ExceptionPopupVueController) super.controller;
        controller.setErrorMsg(msg);
        this.show();
    }

}
