package ulb.infof307.g12.controller.javafx.paquets;

import javafx.stage.Stage;
import lombok.Getter;
import ulb.infof307.g12.controller.javafx.BaseController;
import ulb.infof307.g12.controller.javafx.connexion.MenuPrincipal;
import ulb.infof307.g12.controller.listeners.MenuPaquetListener;
import ulb.infof307.g12.model.Paquet;
import ulb.infof307.g12.model.Utilisateur;
import ulb.infof307.g12.view.paquets.MenuPaquetVueController;


import java.io.IOException;

public class MenuPaquetController extends BaseController implements MenuPaquetListener {

    @Getter
    private Utilisateur user;

    public MenuPaquetController(Utilisateur user,Stage stage) throws IOException {
        super(stage,MenuPaquetVueController.class.getResource("menuPaquet.fxml"),"");
        this.user = user;
        MenuPaquetVueController controller = (MenuPaquetVueController) super.controller;
        controller.setListener(this);
    }
    @Override
    public void openPaquet(Paquet paquet) {
        //TODO: faire le paquet
    }

    @Override
    public Paquet creerPaquet() throws IOException {
        Paquet nouveauPaquet = new Paquet("Nouveau Paquet", "Catégorie") ;
        user.addPaquet(nouveauPaquet);
        return nouveauPaquet;
    }

    @Override
    public void editerPaquet(Paquet paquet) {
        MenuPrincipal.getINSTANCE().showMenuEdition(paquet);
    }

    @Override
    public void CarteEtude(){
        MenuPrincipal instance = MenuPrincipal.getINSTANCE();
        instance.showCarteEtude();
    }


}
