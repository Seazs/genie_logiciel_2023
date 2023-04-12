package ulb.infof307.g12.controller.javafx.paquets;

import javafx.stage.Stage;
import ulb.infof307.g12.controller.javafx.BaseController;
import ulb.infof307.g12.controller.javafx.connexion.MenuPrincipal;
import ulb.infof307.g12.controller.listeners.CarteEtudeListener;
import ulb.infof307.g12.controller.listeners.EditionVueListener;
import ulb.infof307.g12.view.paquets.CarteEtudeVueController;
import ulb.infof307.g12.view.paquets.EditionVueController;

import java.io.IOException;
import java.net.URL;

public class CarteEtudeController extends BaseController implements CarteEtudeListener {

    public CarteEtudeController(Stage stage) throws IOException {
        super(stage, CarteEtudeVueController.class.getResource("carteEtude.fxml"),"");
    }

    @Override
    public void CarteEtude(){
        MenuPrincipal instance = MenuPrincipal.getINSTANCE();
        instance.showCarteEtude();

    }

}