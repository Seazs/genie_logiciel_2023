package ulb.infof307.g12.controller.javafx.profiles;

import javafx.stage.Stage;
import lombok.Getter;
import ulb.infof307.g12.controller.javafx.BaseController;
import ulb.infof307.g12.controller.javafx.connexion.MenuPrincipal;
import ulb.infof307.g12.view.listeners.ProfilVueListener;
import ulb.infof307.g12.controller.storage.GestionnaireUtilisateur;
import ulb.infof307.g12.model.Utilisateur;
import ulb.infof307.g12.view.profiles.ProfilVueController;

import java.io.IOException;
import java.util.Optional;

public class ProfilController extends BaseController implements ProfilVueListener {

    @Getter
    private Utilisateur user;

    /**
     * Controller du profil
     * @param stage stage
     * @param user utilisateur
     * @throws IOException exceptiony
     */
    public ProfilController(Stage stage, Utilisateur user) throws IOException {
        super(stage,ProfilVueController.class.getResource("profil.fxml"),"");
        this.user = user;

        ProfilVueController controller = (ProfilVueController) super.controller;
        controller.setListener(this);
        controller.setPseudoLabel(user.getPseudo());
        controller.setMdpLabel(user.getMdp());
    }

    /**
     * Changement de mot de passe de l'utilisateur
     * @param password nouveau mot de passe
     * @return le résultat
     */
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
                MenuPrincipal.getINSTANCE().getPrincipalUser().setMdp(newPassword);
                result = gestionnaire.getStatusMsg();
            } catch (IOException e) {
                result = "Une erreur s'est produite. Veuillez réessayer.";
            }

        }
        return result;
    }

    /**
     * Déconnexion de l'utilisateur
     */
    @Override
    public void deconnexion(){
        MenuPrincipal instance = MenuPrincipal.getINSTANCE();
        instance.getGestionnaireUtilisateur().disconnect();
        instance.showConnexionMenu(this);
    }
}
