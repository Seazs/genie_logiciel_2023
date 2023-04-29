package ulb.infof307.g12.controller.javafx.paquets;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.stage.Stage;
import lombok.Getter;
import ulb.infof307.g12.controller.javafx.BaseController;
import ulb.infof307.g12.view.paquets.EditionQRVueController;
import ulb.infof307.g12.view.paquets.EditionVueController;

import java.io.IOException;
import java.util.Optional;

public class EditionQRController {

    private EditionQRVueController vue;
    @Getter
    private Node node;

    /**
     * Controller des édition QR
     * @throws IOException exception
     */
    public EditionQRController() throws IOException {
        FXMLLoader loader = new FXMLLoader(EditionVueController.class.getResource("editCarte.fxml"));
        node = loader.load();
        vue = loader.getController();
    }

    public String getQuestion() {
        return vue.getQuestion();
    }

    public String getReponse() {
        return vue.getReponse();
    }

    public void clear(){
        vue.clear();
    }
}
