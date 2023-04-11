package ulb.infof307.g12.controller.listeners;

import javafx.collections.ObservableList;
import ulb.infof307.g12.model.Carte;

import java.io.IOException;
import java.util.ArrayList;

public interface EditionVueListener {
    public void enregistrerPaquet(String nom, String categorie) throws IOException;
    public ArrayList<Carte> loadCartes();
    public void ajouterCarte(String recto, String verso);
}
