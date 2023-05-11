package ulb.infof307.g12.controller.javafx.paquets;

import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.stage.Stage;
import lombok.Getter;
import ulb.infof307.g12.controller.javafx.BaseController;
import ulb.infof307.g12.controller.javafx.connexion.MenuPrincipal;
import ulb.infof307.g12.controller.storage.GestionnairePaquet;
import ulb.infof307.g12.view.dto.PaquetDTO;
import ulb.infof307.g12.view.listeners.MenuPaquetListener;
import ulb.infof307.g12.model.Paquet;
import ulb.infof307.g12.model.Utilisateur;
import ulb.infof307.g12.view.paquets.MenuPaquetVueController;


import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class MenuPaquetController extends BaseController implements MenuPaquetListener {

    @Getter
    private final Utilisateur user;

    private List<Paquet> saveListPaquet;

    /**
     * Controller du menuPaquet
     * @param user utilisateur
     * @param stage fenetre
     * @throws IOException exception
     */
    public MenuPaquetController(Utilisateur user,Stage stage) throws IOException {
        super(stage,MenuPaquetVueController.class.getResource("menuPaquet.fxml"),"");
        this.user = user;
        MenuPaquetVueController controller = (MenuPaquetVueController) super.controller;
        controller.setListener(this);
        saveListPaquet = MenuPrincipal.getINSTANCE().getUserPaquets();
        controller.rechargerListView();
    }

    /**
     * Création d'un nouveau paquet vide
     * @return Paquet vide avec un nom et catégorie génériques
     */
    @Override
    public PaquetDTO creerPaquet() {
        // Créer le paquet et l'ajouter à la liste de paquet de l'utilisateur
        Paquet nouveauPaquet = new Paquet("Nouveau Paquet") ;
        //editerPaquet(nouveauPaquet.getDTO());
        user.addPaquet(nouveauPaquet);
        return nouveauPaquet.getDTO();
    }

    /**
     * Supprime un paquet de l'utilisateur
     * @param paquetDTO Paquet à supprimer
     */
    @Override
    public void supprimerPaquet(PaquetDTO paquetDTO) {
        Optional<Paquet> paquet = paquetDTO.getPaquet();
        GestionnairePaquet gestionnairePaquet = MenuPrincipal.getINSTANCE().getGestionnairePaquet();
        if(paquet.isEmpty()) {
            MenuPrincipal.getINSTANCE().showErrorPopup("Veuillez sélectionner un paquet à supprimer");
            return;
        }
        user.removePaquet(paquet.get().getNom());
        gestionnairePaquet.remove(MenuPrincipal.getINSTANCE().getPrincipalUser(), paquet.get());
    }

    /**
     * filtre les paquets selon leurs catégories
     * @param filter filtre à appliquer sur les catégories de chaque paquet
     * @return la liste des paquets filtrés par catégorie
     */
    @Override
    public Collection<PaquetDTO> filterPaquet(String filter) {
        return saveListPaquet.stream()
                .filter(paquet -> paquet.getCategories().stream().anyMatch(category -> category.contains(filter.toLowerCase())))
                .map(Paquet::getDTO)
                .toList();
    }

    /**
     * Récupère la liste des paquets à afficher (sous forme de DTO)
     * @return la liste des paquets à afficher
     */
    @Override
    public Collection<PaquetDTO> getPaquetDTOList() {
        return saveListPaquet.stream()
                .map(paquet -> new PaquetDTO(paquet.getNom(), paquet.getCategories()))
                .toList();
    }

    @Override
    public void importPaquet(File file) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Paquet newPaquet = objectMapper.readValue(file, Paquet.class);
            System.out.println("Paquet créé");
            MenuPrincipal.getINSTANCE().getPrincipalUser().addPaquet(newPaquet);
            MenuPrincipal.getINSTANCE().getGestionnairePaquet().save(MenuPrincipal.getINSTANCE().getPrincipalUser());
            System.out.println("Importation du paquet " + file.getName() + "réussie !");
        } catch (IOException e) {
            MenuPrincipal.getINSTANCE().showErrorPopup("OUI UNE ERREUR MIAM MIAM");
        }
    }

    /**
     * Lancer le menu d'édition avec le paquet choisit par l'utilisateur
     * @param paquetDTO Paquet choisit par l'utilisateur à être modifié
     */
    @Override
    public void editerPaquet(PaquetDTO paquetDTO) {
        try {
            Optional<Paquet> paquet = paquetDTO.getPaquet();
            MenuPrincipal.getINSTANCE().showMenuEdition(paquet.get());
        }catch (NullPointerException e){
            MenuPrincipal.getINSTANCE().showErrorPopup("Vous devez sélectionner un paquet à éditer !");
        }
    }

    /**
     * Démarre une session d'étude avec le paquet choisit par l'utilisateur
     * @param paquetDTO Paquet choisit par l'utilisateur à être étudié
     */
    @Override
    public void CarteEtude(PaquetDTO paquetDTO) {
        MenuPrincipal instance = MenuPrincipal.getINSTANCE();

        try {
            Optional<Paquet> paquet = paquetDTO.getPaquet();
            Paquet paquetInstance = paquet.get();
            if (paquetInstance.cartes.size() == 0){
                //Attention, le showErrorPopup ne fontionne pas, à corriger pour être plus propre.
                instance.showErrorPopup("Vous devez creer des cartes avant de pouvoir les étudier !");

            }else{
                instance.showCarteEtude(this,paquetInstance);
            }
        }catch (NullPointerException e){
            instance.showErrorPopup("Vous devez sélectionner un paquet à étudier !");
        }
    }


    public void updatePaquets() {
        saveListPaquet = MenuPrincipal.getINSTANCE().getUserPaquets();
        MenuPaquetVueController controller = (MenuPaquetVueController) super.controller;
        controller.rechargerListView();
    }


}
