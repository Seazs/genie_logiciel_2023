package ulb.infof307.g12.controller.listeners;

import javafx.event.ActionEvent;
import ulb.infof307.g12.model.Carte;

import java.util.ArrayList;

public interface CarteEtudeListener {
    public ArrayList<Carte> getCartesEtude();

    public ArrayList<Integer> getCartesEtudeScore();

    /**
     * @param index index
     */
    public void tresMauvais(int index);

    /**
     * @param index index
     */
    public void mauvais(int index);

    /**
     * @param index index
     */
    public void moyen(int index);

    /**
     * @param index index
     */
    public void bon(int index);

    /**
     * @param index index
     */
    public void tresBon(int index);

    /**
     * Sauvegarde des cartes
     */
    public void saveCartes();
}
