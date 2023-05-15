package ulb.infof307.g12.controller.javafx.profiles;

import javafx.stage.Stage;
import lombok.Getter;
import ulb.infof307.g12.controller.javafx.BaseController;
import ulb.infof307.g12.controller.javafx.connexion.MenuPrincipal;
import ulb.infof307.g12.controller.storage.UserManager;
import ulb.infof307.g12.model.STATUS;
import ulb.infof307.g12.model.Server;
import ulb.infof307.g12.model.User;
import ulb.infof307.g12.view.listeners.ProfileViewListener;
import ulb.infof307.g12.view.profiles.ProfileViewController;

import java.io.IOException;
import java.util.Optional;

public class ProfileController extends BaseController implements ProfileViewListener {

    @Getter
    private User user;

    /**
     * Controller du profil
     *
     * @param stage stage
     * @param user  utilisateur
     * @throws IOException exceptiony
     */
    public ProfileController(Stage stage, User user) throws IOException {
        super(stage, ProfileViewController.class.getResource("profil.fxml"), "");
        this.user = user;

        ProfileViewController controller = (ProfileViewController) super.controller;
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
        if (!MenuPrincipal.getINSTANCE().isOnline()){
            return STATUS.CANNOT_CHANGE_PASSWORD_OFFLINE.getMsg();
        }
        String username = user.getPseudo(),
                oldPassword = user.getMdp(),
                result = "";
        if (password.isPresent()) {
            String newPassword = password.get();
            UserManager gestionnaire = MenuPrincipal.getINSTANCE().getUserManager();
            Server server = MenuPrincipal.getINSTANCE().getServer();
            try {
                String response = server.changeUserPassword(username, newPassword);
                if (!STATUS.valueOf(response).equals(STATUS.OK)){
                    return STATUS.SERVER_CANNOT_CHANGE_PASSWORD.getMsg();
                }
                gestionnaire.changePassword(username, newPassword, oldPassword);
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
    public void disconnect() {
        MenuPrincipal instance = MenuPrincipal.getINSTANCE();
        instance.getUserManager().disconnect();
        instance.showConnexionMenu(this);
    }
}
