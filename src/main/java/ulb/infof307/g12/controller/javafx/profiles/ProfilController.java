package ulb.infof307.g12.controller.javafx.profiles;

import javafx.stage.Stage;
import ulb.infof307.g12.controller.javafx.BaseController;
import ulb.infof307.g12.controller.javafx.connection.MenuPrincipal;
import ulb.infof307.g12.controller.javafx.paquets.MenuPaquetController;
import ulb.infof307.g12.controller.listeners.ProfilVueListener;
import ulb.infof307.g12.controller.storage.GestionnaireUtilisateur;
import ulb.infof307.g12.model.Utilisateur;
import ulb.infof307.g12.view.profiles.ProfilVueController;

import java.io.IOException;
import java.util.Optional;

public class ProfilController extends BaseController implements ProfilVueListener {

    private Utilisateur user;
    private MenuPaquetController parent;

    public ProfilController(Stage stage, MenuPaquetController parent) throws IOException {
        super(stage,ProfilVueController.class.getResource("profil.fxml"),"");
        user = parent.getUser();
        this.parent = parent;

        ProfilVueController controller = (ProfilVueController) super.controller;
        controller.setListener(this);

    }


    @Override
    public void parentReturn() {
        this.hide();
        parent.show();
    }

    @Override
    public String changePassword(Optional<String> password) {
        String username = user.getPseudo(),
                oldPassword = user.getMdp(),
                result = "";

        if (password.isPresent()) {
            String newPassword = password.get();
            GestionnaireUtilisateur gestionnaire = MenuPrincipal.getINSTANCE().getGestionnaireUtilisateur();
            try {
                gestionnaire.modifierMotDePasse(username,newPassword,oldPassword);
                result = gestionnaire.getStatusMsg();
            } catch (IOException e) {
                result = "Une erreur s'est produite. Veuillez réessayer.";
            }

        }
        return result;
    }

    public boolean deconnexion(){
    return true;}
}
