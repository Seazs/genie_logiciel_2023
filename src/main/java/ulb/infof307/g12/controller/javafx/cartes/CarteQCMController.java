package ulb.infof307.g12.controller.javafx.cartes;

import javafx.stage.Stage;
import ulb.infof307.g12.controller.javafx.BaseController;
import ulb.infof307.g12.controller.listeners.QCMListener;
import ulb.infof307.g12.model.Carte;
import ulb.infof307.g12.view.cartes.CarteQCMVueController;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;

public class CarteQCMController extends BaseController implements QCMListener {
    public CarteQCMController(Stage stage, URL resource, String title,Carte carte) throws IOException {
        super(stage, resource, title);
        if (!Objects.equals(carte.getType(), "qcm")) throw new IllegalArgumentException();
        CarteQCMVueController controller = (CarteQCMVueController) super.controller;
        controller.showCarte(carte);
        controller.setListener(this);
    }
}
