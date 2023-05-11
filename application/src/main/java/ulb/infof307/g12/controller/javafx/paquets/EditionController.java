package ulb.infof307.g12.controller.javafx.paquets;

import javafx.stage.Stage;
import lombok.Getter;
import ulb.infof307.g12.controller.javafx.BaseController;
import ulb.infof307.g12.controller.javafx.connexion.MenuPrincipal;
import ulb.infof307.g12.model.*;
import ulb.infof307.g12.view.dto.CardDTO;
import ulb.infof307.g12.view.listeners.EditionVueListener;
import ulb.infof307.g12.controller.storage.GestionnairePaquet;
import ulb.infof307.g12.view.paquets.EditionVueController;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class EditionController extends BaseController implements EditionVueListener {

    @Getter
    private final Paquet paquet;


    /**y
     * Controller de l'édition
     * @param stage stage
     * @param paquet paquet
     * @throws IOException exception
     */
    public EditionController(Stage stage, Paquet paquet) throws IOException, IllegalArgumentException {
        super(stage, EditionVueController.class.getResource("editionPaquet.fxml"), "");
        if (paquet == null) {
            throw new IllegalArgumentException("Le paquet ne peut pas être null");
        }
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
    public void savePaquet(String nom, String categorie){
        try {
            // Enregistrer le nom et ajouter la nouvelle categorie
            paquet.setNom(nom);
            paquet.addCategory(categorie);
            GestionnairePaquet gestionnairePaquet = MenuPrincipal.getINSTANCE().getGestionnairePaquet();
            gestionnairePaquet.save(MenuPrincipal.getINSTANCE().getPrincipalUser());
            MenuPrincipal.getINSTANCE().returnFromEditionToMenuPaquet();// Revenir sur le menu principal
        }catch (IOException e){
            MenuPrincipal.getINSTANCE().showErrorPopup("Impossible de sauvegarder le paquet !");
        }catch (IllegalArgumentException e){
            MenuPrincipal.getINSTANCE().showErrorPopup("La catégorie ne peut pas contenir # !");
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
    @Override
    public void addCard(String recto, String verso) {
        int id = paquet.getCartes().size() + 1 ;
        try {
            Carte carte = new Carte(id, recto, verso) ;
            paquet.addCard(carte);
        }catch (IllegalArgumentException e){
            MenuPrincipal.getINSTANCE().showErrorPopup("La carte doit posséder un recto et un verso!");
        }
    }

    /**
     * Créer une nouvelle carte QCM et l'ajoute au paquet qui est modifié
     * @param recto recto
     * @param verso verso
     */
    public void addCardQCM(String recto, String verso) {
        int id = paquet.getCartes().size() + 1 ;
        try {
            CarteQcm carte = new CarteQcm(id, recto, verso) ;
            paquet.addCard(carte);
        }catch (IllegalArgumentException e){
            MenuPrincipal.getINSTANCE().showErrorPopup("La carte doit posséder un recto et un verso !");
        }
    }

    /**
     * Créer une nouvelle carte Texte à Trou et l'ajoute au paquet qui est modifié
     * @param recto recto
     * @param verso verso
     */
    public void addCardTT(String recto, String verso) {
        int id = paquet.getCartes().size() + 1 ;
        try {
            CarteTt carte = new CarteTt(id, recto, verso) ;
            paquet.addCard(carte);
        }catch (IllegalArgumentException e){
            MenuPrincipal.getINSTANCE().showErrorPopup("La carte doit posséder un recto et un verso !");
        }
    }
    /**
     * Créer une nouvelle carte Speciale et l'ajoute au paquet qui est modifié
     * @param recto recto
     * @param verso verso
     * @param lang langue de la carte
     */
    public void addCardSpecial(String recto, String verso,String lang) {
        int id = paquet.getCartes().size() + 1 ;
        try {
            CarteSpec carte = new CarteSpec(id, recto, verso,lang) ;
            paquet.addCard(carte);
        }catch (IllegalArgumentException e){
            MenuPrincipal.getINSTANCE().showErrorPopup(e.getMessage());
        }
    }

    /**
     * @param begin begin
     * @param end end
     * @param gap gap
     * @return true if the fields are correct (no # or §)
     */
    @Override
    public boolean checkTt(String begin, String end, String gap){
        if (begin.contains("§") || end.contains("§") || gap.contains("§")){
            MenuPrincipal.getINSTANCE().showErrorPopup("Les champs ne peuvent pas contenir le caractère § !");
            return false;
        } else if (begin.contains("#") || end.contains("#") || gap.contains("#")) {
            MenuPrincipal.getINSTANCE().showErrorPopup("Les champs ne peuvent pas contenir le caractère # !");
            return false;
        }
        return true;
    }

    /**
     * @param question      question
     * @param answer1       answer1
     * @param answer2       answer2
     * @param answer3       answer3
     * @param correctAnswer correctAnswer
     * @return true if the fields are correct (no # or §)
     */
    @Override
    public boolean checkQcm(String question, String answer1, String answer2, String answer3, String correctAnswer){
        if (question.contains("§") || answer1.contains("§") || answer2.contains("§") || answer3.contains("§") || correctAnswer.contains("§")){
            MenuPrincipal.getINSTANCE().showErrorPopup("Les champs ne peuvent pas contenir le caractère § !");
            return false;
        } else if (question.contains("#") || answer1.contains("#") || answer2.contains("#") || answer3.contains("#") || correctAnswer.contains("#")) {
            MenuPrincipal.getINSTANCE().showErrorPopup("Les champs ne peuvent pas contenir le caractère # !");
            return false;
        }
        return true;
    }

    /**
     * @param message message d'erreur à afficher
     */
    @Override
    public void error(String message){
        MenuPrincipal.getINSTANCE().showErrorPopup(message);
    }

    @Override
    public List<CardDTO> getData() {
       return paquet.getCartes().stream().map(Carte::getDTO).collect(Collectors.toList());
    }

    @Override
    public void editQuestion(String newQuestion) {
        try{
            paquet.getCartes().stream()
                    .filter(carte -> carte.getRecto().equals(newQuestion))
                    .findFirst()
                    .ifPresent(carte -> carte.editRecto(newQuestion));

        }catch (IllegalArgumentException e){
            error(e.getMessage());
        }
    }

    @Override
    public void editReponse(String newReponse) {
        try {
            paquet.getCartes().stream()
                    .filter(carte -> carte.getVerso().equals(newReponse))
                    .findFirst()
                    .ifPresent(carte -> carte.editVerso(newReponse));
        }catch (IllegalArgumentException e){
            error(e.getMessage());
        }
    }
}
