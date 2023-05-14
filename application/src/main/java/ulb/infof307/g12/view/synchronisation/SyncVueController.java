package ulb.infof307.g12.view.synchronisation;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import lombok.Setter;
import ulb.infof307.g12.view.listeners.SyncListener;

public class SyncVueController {
    @FXML
    Button uploadButton;
    @FXML
    Button downloadButton;
    @Setter
    SyncListener listener;

    public void uploadLocal() {
        listener.uploadLocal();
    }
    public void downloadRemote() {
        listener.downloadRemote();
    }
}
