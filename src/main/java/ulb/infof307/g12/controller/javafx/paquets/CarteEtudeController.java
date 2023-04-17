package ulb.infof307.g12.controller.javafx.paquets;

import javafx.stage.Stage;
import lombok.Getter;
import ulb.infof307.g12.controller.javafx.BaseController;
import ulb.infof307.g12.controller.javafx.connexion.MenuPrincipal;
import ulb.infof307.g12.controller.listeners.CarteEtudeListener;
import ulb.infof307.g12.controller.storage.GestionnairePaquet;
import ulb.infof307.g12.model.Carte;
import ulb.infof307.g12.model.Paquet;
import ulb.infof307.g12.model.Utilisateur;
import ulb.infof307.g12.view.paquets.CarteEtudeVueController;

import java.io.IOException;
import java.util.ArrayList;

public class CarteEtudeController extends BaseController implements CarteEtudeListener {
    @Getter
    private ArrayList<Carte> cartesEtude = new ArrayList<Carte>();
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
    public void tresMauvais(int index){
        cartesEtude.get(index).setConnaissance(1);
    }
    public void mauvais(int index){
        cartesEtude.get(index).setConnaissance(2);
    }
    public void moyen(int index){
        cartesEtude.get(index).setConnaissance(3);
    }
    public void bon(int index){
        cartesEtude.get(index).setConnaissance(4);
    }
    public void tresBon(int index){
        cartesEtude.get(index).setConnaissance(5);

    }
    public void saveCartes(){
        try {
            GestionnairePaquet gestionnairePaquet = MenuPrincipal.getINSTANCE().getGestionnairePaquet();
            gestionnairePaquet.save(MenuPrincipal.getINSTANCE().getUserPrincipale());
        } catch (IOException e) {
            e.printStackTrace();
            MenuPrincipal.getINSTANCE().showErrorPopup("Impossible de sauvegarder le paquet !");
        }
    }

}



