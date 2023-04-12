package ulb.infof307.g12.controller.javafx.paquets;

import javafx.stage.Stage;
import ulb.infof307.g12.controller.javafx.BaseController;
import ulb.infof307.g12.controller.javafx.connexion.MenuPrincipal;
import ulb.infof307.g12.controller.listeners.CarteEtudeListener;
import ulb.infof307.g12.controller.listeners.EditionVueListener;

import java.io.IOException;
import java.net.URL;

public class CarteEtudeController extends BaseController implements CarteEtudeListener {

    public CarteEtudeController(Stage stage, URL resource, String title) throws IOException {
        super(stage, resource, title);
    }

    @Override
    public void CarteEtude(){
        MenuPrincipal instance = MenuPrincipal.getINSTANCE();
        instance.showCarteEtude();

    }

}