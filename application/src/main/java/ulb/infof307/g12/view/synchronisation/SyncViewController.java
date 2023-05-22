package ulb.infof307.g12.view.synchronisation;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import lombok.Setter;
import ulb.infof307.g12.view.listeners.SyncListener;

public class SyncViewController {
    @FXML
    Button uploadButton;
    @FXML
    Button downloadButton;
    @Setter
    SyncListener listener;

    /**
     * Permet d'uploader la base de données locale
     */
    public void uploadLocal() {
        listener.uploadLocal();
    }

    /**
     * Ce qui s'exécute lorsqu'on clique sur le bouton de téléchargement de la base de données distante
     */
    public void downloadRemote() {
        listener.downloadRemote();
    }
}
