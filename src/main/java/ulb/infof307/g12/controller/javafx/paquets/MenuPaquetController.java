package ulb.infof307.g12.controller.javafx.paquets;

import javafx.stage.Stage;
import lombok.Getter;
import ulb.infof307.g12.controller.javafx.BaseController;
import ulb.infof307.g12.controller.javafx.profiles.ProfilController;
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
    public void openProfile(Utilisateur user) {
        ProfilController profilController = null;
        try {
            profilController = new ProfilController(stage,this);
            this.hide();
            profilController.show();
        } catch (IOException e) {
            //TODO: Renvoyer l'erreur à l'utilisateur
        }
    }

    @Override
    public void openPaquet(Paquet paquet) {
        //TODO: faire le paquet
    }
}
