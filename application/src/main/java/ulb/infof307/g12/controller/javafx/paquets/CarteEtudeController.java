package ulb.infof307.g12.controller.javafx.paquets;

import javafx.stage.Stage;
import lombok.Getter;
import ulb.infof307.g12.controller.javafx.BaseController;
import ulb.infof307.g12.controller.javafx.connexion.MenuPrincipal;
import ulb.infof307.g12.controller.listeners.CarteEtudeListener;
import ulb.infof307.g12.controller.storage.GestionnairePaquet;
import ulb.infof307.g12.controller.textToSpeech.textToSpeechController;
import ulb.infof307.g12.model.Carte;
import ulb.infof307.g12.model.Paquet;
import ulb.infof307.g12.view.paquets.CarteEtudeVueController;


import java.io.IOException;
import java.util.ArrayList;

public class CarteEtudeController extends BaseController implements CarteEtudeListener {

    private ArrayList<Carte> cartesEtude = new ArrayList<Carte>();

    private final ArrayList<Integer> cartesEtudeScore = new ArrayList<Integer>();//liste des scores des cartes
    @Getter
    private final Paquet paquet;

    private final textToSpeechController textToSpeechController = new textToSpeechController();

    /**
     * Controller de l'étude de carte
     * @param stage stage
     * @param paquet paquet
     * @throws IOException  exception
     */
    public CarteEtudeController(Stage stage, Paquet paquet) throws IOException {
        super(stage,CarteEtudeVueController.class.getResource("carteEtude.fxml"), "");
        CarteEtudeVueController controller = (CarteEtudeVueController) super.controller;
        this.paquet=paquet;
        controller.setListener(this);
        cartesEtude = paquet.getCartes();
        for(int i=0;i<cartesEtude.size();i++){
            cartesEtudeScore.add(0);
        }
        controller.loadViewStudyCard();
    }

    /**
     * @return CartesEtudes
     */
    @Override
    public ArrayList<Carte> getCartesEtude(){
        return cartesEtude;
    }
    @Override
    public ArrayList<Integer> getCartesEtudeScore(){
        return cartesEtudeScore;
    }

    /**
     * @param index  index
     */
    @Override
    public void tresMauvais(int index){
        cartesEtude.get(index).setConnaissance(1);
    }

    /**
     * @param index index
     */
    @Override
    public void mauvais(int index){
        cartesEtude.get(index).setConnaissance(2);
    }

    /**
     * @param index index
     */
    @Override
    public void moyen(int index){
        cartesEtude.get(index).setConnaissance(3);
    }
    public void bon(int index){
        cartesEtude.get(index).setConnaissance(4);
    }

    /**
     * @param index index
     */
    @Override
    public void tresBon(int index){
        cartesEtude.get(index).setConnaissance(5);

    }

    /**
     * Sauvegarde des cartes
     */
    @Override
    public void saveCartes(){
        try {
            GestionnairePaquet gestionnairePaquet = MenuPrincipal.getINSTANCE().getGestionnairePaquet();
            gestionnairePaquet.save(MenuPrincipal.getINSTANCE().getUserPrincipale());
        } catch (IOException e) {
            e.printStackTrace();
            MenuPrincipal.getINSTANCE().showErrorPopup("Impossible de sauvegarder le paquet !");
        }
    }

    @Override
    public void parlerTexte(String text){
        textToSpeechController.synthese_vocal(text);
    }

}



