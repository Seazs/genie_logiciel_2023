package ulb.infof307.g12.controller.listeners;

import javafx.event.ActionEvent;
import ulb.infof307.g12.model.Carte;

import java.util.ArrayList;

public interface CarteEtudeListener {
    public ArrayList<Carte> getCartesEtude();

    public ArrayList<Integer> getCartesEtudeScore();

    public void tresMauvais(int index);
    public void mauvais(int index);
    public void moyen(int index);
    public void bon(int index);
    public void tresBon(int index);
    public void saveCartes();
}
