package ulb.infof307.g12.controller.listeners;

import javafx.collections.ObservableList;
import ulb.infof307.g12.model.Carte;

import java.util.ArrayList;

public interface EditionVueListener {
    public void enregistrerPaquet();
    public ArrayList<Carte> loadCartes();
}
