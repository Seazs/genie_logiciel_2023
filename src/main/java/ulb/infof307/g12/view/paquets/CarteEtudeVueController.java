package ulb.infof307.g12.view.paquets;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import lombok.Setter;
import ulb.infof307.g12.controller.listeners.CarteEtudeListener;


public class CarteEtudeVueController {
    @Setter
    private CarteEtudeListener listener;

    @FXML
    public void sessionEtude(ActionEvent event){
        if (listener!=null) {
            listener.CarteEtude();
        }

    }
}
