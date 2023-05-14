package ulb.infof307.g12.controller.javafx.synchronisation;

import javafx.stage.Stage;
import ulb.infof307.g12.controller.javafx.BaseController;
import ulb.infof307.g12.controller.javafx.connexion.MenuPrincipal;
import ulb.infof307.g12.model.Paquet;
import ulb.infof307.g12.model.Server;
import ulb.infof307.g12.view.listeners.SyncListener;
import ulb.infof307.g12.view.synchronisation.SyncVueController;

import java.io.IOException;
import java.util.List;

public class SyncController extends BaseController implements SyncListener {

    /**
     * Controller de la synchronisation
     * @param stage    stage
     * @throws IOException exception
     */
    public SyncController(Stage stage) throws IOException {
        super(stage, SyncVueController.class.getResource("sync.fxml"), "Synchronisation");
        SyncVueController controller = (SyncVueController) super.controller;
        controller.setListener(this);
    }

    @Override
    public void uploadLocal() {
        Server server = MenuPrincipal.getINSTANCE().getServer();
        String pseudo = MenuPrincipal.getINSTANCE().getPrincipalUser().getPseudo();
        List<Paquet> paquets= MenuPrincipal.getINSTANCE().getUserPaquets();
        try {
            server.envoiPaquetUser(paquets, pseudo);
        } catch (IOException e) {
            MenuPrincipal.getINSTANCE().showErrorPopup("Erreur lors de l'envoi des paquets");
        }
        MenuPrincipal.getINSTANCE().rechargerPaquets();
        this.hide();
    }

    @Override
    public void downloadRemote() {
        Server server = MenuPrincipal.getINSTANCE().getServer();
        String pseudo = MenuPrincipal.getINSTANCE().getPrincipalUser().getPseudo();
        try {
            List<Paquet> paquets = server.getPaquetsUser(pseudo);
            MenuPrincipal.getINSTANCE().setUserPaquets(paquets);
        } catch (IOException e) {
            MenuPrincipal.getINSTANCE().showErrorPopup("Erreur lors de la récupération des paquets");
        }
        MenuPrincipal.getINSTANCE().rechargerPaquets();
        this.hide();
    }
}
