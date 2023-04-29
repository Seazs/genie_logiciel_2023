package ulb.infof307.g12.controller.listeners;

import javafx.scene.Node;
import ulb.infof307.g12.model.Carte;

import java.util.ArrayList;
import java.util.Optional;

public interface EditionVueListener {
    /**
     * Enregistrement du paquet
     * @param nom
     * @param categorie
     */
    void enregistrerPaquet(String nom, String categorie);
    ArrayList<Carte> loadCartes();

    /**
     * Ajout de cartes
     */
    void ajouterCarte();

    /**
     * Ajout de cartes
     * @param recto
     * @param verso
     */
    void ajouterCarteQCM(String recto, String verso);

    /**
     * Ajout de cartes
     * @param recto
     * @param verso
     */
    void ajouterCarteTT(String recto, String verso);

    /**
     * Changement du type de carte sélectionné
     * @param type le type de carte
     * @return la vue fxml de l'édition de la carte
     */
    Optional<Node> changeCarteType(String type);
}
