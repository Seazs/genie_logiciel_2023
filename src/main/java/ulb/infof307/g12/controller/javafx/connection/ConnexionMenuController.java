package ulb.infof307.g12.controller.javafx.connection;

import javafx.stage.Stage;
import ulb.infof307.g12.controller.javafx.BaseController;
import ulb.infof307.g12.controller.listeners.UserCredentialsListener;
import ulb.infof307.g12.controller.storage.GestionnaireUtilisateur;
import ulb.infof307.g12.model.Utilisateur;
import ulb.infof307.g12.view.connection.ConnectionVueController;

import java.io.FileNotFoundException;
import java.io.IOException;

public class ConnexionMenuController extends BaseController implements UserCredentialsListener {

    private GestionnaireUtilisateur gestionnaire;
    private ConnexionSuccessListener listener;

    public ConnexionMenuController(Stage stage,GestionnaireUtilisateur gestionnaireUtilisateur,ConnexionSuccessListener listener) throws IOException {
        super(stage,ConnectionVueController.class.getResource("connexion-menu-view.fxml"),"Application Title");
        this.listener = listener;
        gestionnaire = gestionnaireUtilisateur;

        ConnectionVueController controller = (ConnectionVueController) super.controller;
        controller.setListener(this);
    }

    /*
    protected void onConnectButtonClick(ActionEvent event) throws IOException {

            FXMLLoader loader = new FXMLLoader(MenuPaquetController.class.getResource("menuPaquet.fxml"));
            Parent root = loader.<Parent>load();
            Scene scene = new Scene(root);
            MenuPaquetController controller = loader.getController();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
    }
    */

    @Override
    public String onRegister(String username, String password) {

        String result = "";

        try {
            if (gestionnaire.register(username, password)) {
                result = "Register: " + username + " = " + password;
            } else {
                result = gestionnaire.getStatusMsg();
            }
        } catch (IOException e) {
            //
        }

        return result;

    }

    @Override
    public String onLogin(String username, String password) {
        String result = "";

        try {
            if (gestionnaire.connect(username, password)) {
                result = "Connecting: " + username + " = " + password;

                Utilisateur connectedUser = new Utilisateur(username,password);

                listener.connect(connectedUser,this);
            } else {
                result = gestionnaire.getStatusMsg();
            }
        } catch (FileNotFoundException e) {
            //
        }

        return result;
    }


    public interface ConnexionSuccessListener{

        void connect(Utilisateur user,ConnexionMenuController parent);

    }

}