package ulb.infof307.g12.controller.javafx.paquets;

import javafx.stage.Stage;
import lombok.Getter;
import ulb.infof307.g12.controller.javafx.BaseController;
import ulb.infof307.g12.controller.javafx.connexion.MenuPrincipal;
import ulb.infof307.g12.controller.storage.PaquetManager;
import ulb.infof307.g12.model.*;
import ulb.infof307.g12.view.dto.CardDTO;
import ulb.infof307.g12.view.listeners.EditionViewListener;
import ulb.infof307.g12.view.paquets.EditionViewController;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class EditionController extends BaseController implements EditionViewListener {

    @Getter
    private final Paquet paquet;


    /**
     * Controller de l'édition
     * @param stage stage
     * @param paquet paquet
     * @throws IOException exception
     */
    public EditionController(Stage stage, Paquet paquet) throws IOException, IllegalArgumentException {
        super(stage, EditionViewController.class.getResource("editionPaquet.fxml"), "");
        if (paquet == null) {
            throw new IllegalArgumentException("Le paquet ne peut pas être null");
        }
        this.paquet = paquet;
        EditionViewController controller = (EditionViewController) super.controller;
        controller.setListener(this);
        controller.loadEditionView(paquet.getNom());
    }

    /**
     * Sauvegarder le paquet suite à des modifications
     * @param nom Nom du paquet
     * @param categorie Catégorie à être rajoutée
     */
    @Override
    public void savePaquet(String nom, String categorie) {
        try {
            // Enregistrer le nom et ajouter la nouvelle categorie
            paquet.setNom(nom);
            paquet.addCategory(categorie);
            PaquetManager paquetManager = MenuPrincipal.getINSTANCE().getPaquetManager();
            paquetManager.save(MenuPrincipal.getINSTANCE().getPrincipalUser());
            MenuPrincipal.getINSTANCE().returnFromEditionToMenuPaquet(); // Revenir sur le menu principal
        } catch (IOException e) {
            MenuPrincipal.getINSTANCE().showErrorPopup("Impossible de sauvegarder le paquet !");
        } catch (IllegalArgumentException e) {
            if (e.getMessage().equals("Le champ catégorie ne peut pas être vide")) {
                MenuPrincipal.getINSTANCE().showErrorPopup("La catégorie ne peut pas être vide !");
            } else if (e.getMessage().equals("Le nom de la catégorie ne peut pas contenir le caractère #")) {
                MenuPrincipal.getINSTANCE().showErrorPopup("La catégorie ne peut pas contenir # !");
            }
        }
    }


    /**
     * Renvoyer les cartes du paquet
     * @return ArrayList<Carte>
     */
    public ArrayList<Card> loadCards() {
        return paquet.getCards();
    }

    /**
     * Créer une nouvelle carte simple et l'ajoute au paquet qui est modifié
     */
    @Override
    public void addCard(String recto, String verso) {
        int id = paquet.getCards().size() + 1 ;
        try {
            Card card = new Card(id, recto, verso) ;
            paquet.addCard(card);
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
        int id = paquet.getCards().size() + 1 ;
        try {
            CardQcm carte = new CardQcm(id, recto, verso) ;
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
        int id = paquet.getCards().size() + 1 ;
        try {
            CardTt carte = new CardTt(id, recto, verso) ;
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
        int id = paquet.getCards().size() + 1 ;
        try {
            CardSpec carte = new CardSpec(id, recto, verso,lang) ;
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
       return paquet.getCards().stream().map(Card::getDTO).collect(Collectors.toList());
    }

    /**
     * Change la question de la carte
     * @param newQuestion nouvelle question
     */
    @Override
    public void editQuestion(String newQuestion) {
        try{
            paquet.getCards().stream()
                    .filter(carte -> carte.getRecto().equals(newQuestion))
                    .findFirst()
                    .ifPresent(carte -> carte.editRecto(newQuestion));
        }catch (IllegalArgumentException e){
            error(e.getMessage());
        }
    }

    /**
     * Change la réponse de la carte
     * @param newReponse nouvelle réponse
     */
    @Override
    public void editResponse(String newReponse) {
        try {
            paquet.getCards().stream()
                    .filter(carte -> carte.getVerso().equals(newReponse))
                    .findFirst()
                    .ifPresent(carte -> carte.editVerso(newReponse));
        }catch (IllegalArgumentException e){
            error(e.getMessage());
        }
    }
}
