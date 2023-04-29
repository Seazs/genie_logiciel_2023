package ulb.infof307.g12.controller.javafx.paquets;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.stage.Stage;
import lombok.Getter;
import ulb.infof307.g12.controller.javafx.BaseController;
import ulb.infof307.g12.controller.javafx.connexion.MenuPrincipal;
import ulb.infof307.g12.controller.listeners.EditionVueListener;
import ulb.infof307.g12.controller.storage.GestionnairePaquet;
import ulb.infof307.g12.model.*;
import ulb.infof307.g12.view.paquets.EditionVueController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

public class EditionController extends BaseController implements EditionVueListener {

    @Getter
    private final Paquet paquet;
    private EditionQRController editionQRController;

    /**
     * Controller de l'édition
     * @param stage stage
     * @param paquet paquet
     * @throws IOException exception
     */
    public EditionController(Stage stage, Paquet paquet) throws IOException {
        super(stage, EditionVueController.class.getResource("editionPaquet.fxml"), "");
        editionQRController = new EditionQRController();
        this.paquet = paquet;
        EditionVueController controller = (EditionVueController) super.controller;
        controller.setListener(this);
        controller.chargerEditionVue(paquet.getNom());
    }

    /**
     * Sauvegarder le paquet suite à des modifications
     * @param nom Nom du paquet
     * @param categorie Catégorie à être rajoutée
     */
    @Override
    public void enregistrerPaquet(String nom, String categorie){
        try {
            // Enregistrer le nom et ajouter la nouvelle categorie
            paquet.setNom(nom);
            paquet.ajouterCategorie(categorie);
            GestionnairePaquet gestionnairePaquet = MenuPrincipal.getINSTANCE().getGestionnairePaquet();
            gestionnairePaquet.save(MenuPrincipal.getINSTANCE().getUserPrincipale());
        }catch (IOException e){
            e.printStackTrace();
            MenuPrincipal.getINSTANCE().showErrorPopup("Impossible de sauvegarder le paquet !");
        }
    }

    /**
     * Renvoyer les cartes du paquet
     * @return ArrayList<Carte>
     */
    public ArrayList<Carte> loadCartes() {
        return paquet.getCartes();
    }

    /**
     * Créer une nouvelle carte simple et l'ajoute au paquet qui est modifié
     */
    public void ajouterCarte(){
        String recto = editionQRController.getQuestion(),
        verso = editionQRController.getReponse();
        System.out.println(recto + " | " + verso);
        int id = paquet.getCartes().size() + 1 ;
        try {
            Carte carte = new Carte(id, recto, verso) ;
            paquet.ajouterCarte(carte);
            editionQRController.clear();
        }catch (IllegalArgumentException e){
            MenuPrincipal.getINSTANCE().showErrorPopup("La carte doit posseder un recto et un verso !");
        }

    }

    /**
     * Créer une nouvelle carte QCM et l'ajoute au paquet qui est modifié
     * @param recto recto
     * @param verso verso
     */
    public void ajouterCarteQCM(String recto, String verso) {
        int id = paquet.getCartes().size() + 1 ;
        try {
            CarteQcm carte = new CarteQcm(id, recto, verso) ;
            paquet.ajouterCarte(carte);
        }catch (IllegalArgumentException e){
            MenuPrincipal.getINSTANCE().showErrorPopup("La carte doit posseder un recto et un verso !");
        }
    }

    /**
     * Créer une nouvelle carte Texte à Trou et l'ajoute au paquet qui est modifié
     * @param recto recto
     * @param verso verso
     */
    public void ajouterCarteTT(String recto, String verso) {
        int id = paquet.getCartes().size() + 1 ;
        try {
            CarteTt carte = new CarteTt(id, recto, verso) ;
            paquet.ajouterCarte(carte);
        }catch (IllegalArgumentException e){
            MenuPrincipal.getINSTANCE().showErrorPopup("La carte doit posseder un recto et un verso !");
        }
    }

    /**
     * @see EditionVueListener#changeCarteType(String)
     * @param type type
     */
    @Override
    public Optional<Node> changeCarteType(String type) {
        Optional<Node> node;
        try {
            String fxmlFile = switch (type) {
                case "QCM" -> "editCarteQcm.fxml";
                case "Texte à trous" -> "editCarteTt.fxml";
                default -> null;
            };

            if(fxmlFile == null){
                node = Optional.of(editionQRController.getNode());
            }else {
                FXMLLoader loader = new FXMLLoader(EditionVueController.class.getResource(fxmlFile));
                node = Optional.of(loader.load());
            }
        } catch (IOException e) {
            MenuPrincipal.getINSTANCE().showErrorPopup("Impossible de charger le type de carte suivant: "+type+" !");
            node = Optional.empty();
        }
        return node;
    }

}
