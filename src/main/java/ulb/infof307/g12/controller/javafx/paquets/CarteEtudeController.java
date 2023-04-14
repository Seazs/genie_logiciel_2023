package ulb.infof307.g12.controller.javafx.paquets;

import javafx.stage.Stage;
import lombok.Getter;
import ulb.infof307.g12.controller.javafx.BaseController;
import ulb.infof307.g12.controller.javafx.connexion.MenuPrincipal;
import ulb.infof307.g12.controller.listeners.CarteEtudeListener;
import ulb.infof307.g12.controller.listeners.EditionVueListener;
import ulb.infof307.g12.model.Carte;
import ulb.infof307.g12.model.Paquet;
import ulb.infof307.g12.model.Utilisateur;
import ulb.infof307.g12.view.paquets.CarteEtudeVueController;
import ulb.infof307.g12.view.paquets.EditionVueController;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class CarteEtudeController extends BaseController implements CarteEtudeListener {
    @Getter
    private ArrayList<Carte> cartesEtude = new ArrayList<Carte>();

    private ArrayList<Carte> cartesEtudeScore = new ArrayList<Carte>();
    @Getter
    private Paquet paquet;
    public CarteEtudeController(Stage stage, Paquet paquet) throws IOException {
        super(stage,CarteEtudeVueController.class.getResource("carteEtude.fxml"), "");
        CarteEtudeVueController controller = (CarteEtudeVueController) super.controller;
        this.paquet=paquet;
        controller.setListener(this);
        cartesEtude = paquet.getCartes();
        controller.chargerCarteEtudeVue(cartesEtude);
    }
    @Override
    public ArrayList<Carte> getCartesEtude(){
        return cartesEtude;
    }

}



