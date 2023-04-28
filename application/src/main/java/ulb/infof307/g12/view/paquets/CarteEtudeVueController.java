package ulb.infof307.g12.view.paquets;


import java.util.concurrent.ThreadLocalRandom;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import lombok.Setter;
import ulb.infof307.g12.controller.javafx.connexion.MenuPrincipal;
import ulb.infof307.g12.controller.listeners.CarteEtudeListener;
import ulb.infof307.g12.model.Carte;

import java.util.ArrayList;



public class CarteEtudeVueController{
    @Setter
    private CarteEtudeListener listener;
    @FXML
    private Text affichageCarte;
    @FXML
    private Button boutonSuivant;
    @FXML
    private Button boutonChange;
    private ArrayList<Carte> cartesEtude;
    private ArrayList<Integer> cartesEtudeScore;
    @FXML

    private int indexCarte = 0;
    private int cote = 0; // 0 = recto, 1 = verso


    /**
     * Chargement de la vue des cartes d'études
     * @param cartesEtude
     */
    public void chargerCarteEtudeVue(ArrayList<Carte> cartesEtude) {
        indexCarte=indexRandom();
        affichageCarte.setText(cartesEtude.get(indexCarte).getRecto());
    }

    /**
     * Change de côté de la carte entre recto et verso
     * @return
     */
    public void changeCote(){
        cartesEtude = listener.getCartesEtude();
        if (cote == 0){
            affichageCarte.setText(cartesEtude.get(indexCarte).getVerso());
            boutonChange.setText("Recto");
            cote = 1;
        }
        else{
            affichageCarte.setText(cartesEtude.get(indexCarte).getRecto());
            boutonChange.setText("Verso");
            cote = 0;
        }
    }

    /**
     * Passe à la carte suivante
     */
    public void carteSuivante(){
        cartesEtude = listener.getCartesEtude();
        indexCarte=indexRandom();
        affichageCarte.setText(cartesEtude.get(indexCarte).getRecto());
        if (cote == 1){
            changeCote();
            }
    }

    /**
     * Retourne à la carte précédente
     */
    public void cartePrecedente(){
        cartesEtude = listener.getCartesEtude();
        if (indexCarte > 0){
            indexCarte--;
            affichageCarte.setText(cartesEtude.get(indexCarte).getRecto());
            if (cote == 1){
                changeCote();
            }
        }

    }

    /**
     * Fonction qui appelle la fonction terminer de CarteEtudeListener
     */
    public void terminer() {
        MenuPrincipal.getINSTANCE().returnFromCarteEtudeToMenuPaquet();
        listener.saveCartes();
    }

    /**
     * Fonction qui appelle la fonction tresMauvais de CarteEtudeListener
     */
    @FXML
    public void veryBad() { listener.tresMauvais(indexCarte); }

    /**
     * Fonction qui appelle la fonction mauvais de CarteEtudeListener
     */
    public void bad() {
        listener.mauvais(indexCarte);
    }

    /**
     * Fonction qui appelle la fonction moyen de CarteEtudeListener
     */
    public void middle() {
        listener.moyen(indexCarte);
    }

    /**
     * Fonction qui appelle la fonction bon de CarteEtudeListener
     */
    public void good() {
        listener.bon(indexCarte);
    }

    /**
     * Fonction qui appelle la fonction tresBon de CarteEtudeListener
     */
    public void veryGood() {
        listener.tresBon(indexCarte);
    }

    /**
     * Vérifie si toutes les cartes ont été vues au moins une fois. Si c’est le cas:
     * On tire une carte du paquet au hasard. On tire un nombre "apparition" au hasard entre 0 et scoreConnaissance*10 + 1.
     * tant que le nombre "apparition" est plus grand que 20, on continue à tirer une carte au hasard et une "apparition".
     * Cela signifie que les cartes avec un score de connaissance élevé sont tirées moins souvent car leur "apparition" à plus de chance d’être supérieur à 20.
     */
    public int indexRandom(){
        cartesEtude = listener.getCartesEtude();
        if(cartesLues()){
            indexCarte = ThreadLocalRandom.current().nextInt(0, cartesEtude.size());
            int apparition = ThreadLocalRandom.current().nextInt(0,(cartesEtude.get(indexCarte).getConnaissance()*10)+1);
            while(apparition>20){
                indexCarte = ThreadLocalRandom.current().nextInt(0, cartesEtude.size());
                apparition = ThreadLocalRandom.current().nextInt(0,(cartesEtude.get(indexCarte).getConnaissance()*10)+1);
            }
        }
        return indexCarte;
    }

    /**
     * Fonction qui vérifie si toutes les cartes ont été lues
     * @return true si toutes les cartes ont été lues
     */
    public boolean cartesLues() {
        cartesEtude = listener.getCartesEtude();
        indexCarte=0;
        while (indexCarte < cartesEtude.size()-1) {
            indexCarte++;
            if (cartesEtude.get(indexCarte).getConnaissance() == 0) {
                return false;
            }
        }
        return true;
    }
}
