package ulb.infof307.g12.controller.javafx.paquets;

import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.stage.Stage;
import lombok.Getter;
import ulb.infof307.g12.controller.javafx.BaseController;
import ulb.infof307.g12.controller.javafx.connexion.MenuPrincipal;
import ulb.infof307.g12.controller.storage.PaquetManager;
import ulb.infof307.g12.model.User;
import ulb.infof307.g12.view.dto.PaquetDTO;
import ulb.infof307.g12.view.listeners.MenuPaquetListener;
import ulb.infof307.g12.model.Paquet;
import ulb.infof307.g12.view.paquets.MenuPaquetViewController;


import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class MenuPaquetController extends BaseController implements MenuPaquetListener {

    @Getter
    private final User user;

    private List<Paquet> saveListPaquet;

    /**
     * Controller du menuPaquet
     * @param user utilisateur
     * @param stage fenetre
     * @throws IOException exception
     */
    public MenuPaquetController(User user, Stage stage) throws IOException {
        super(stage, MenuPaquetViewController.class.getResource("menuPaquet.fxml"),"");
        this.user = user;
        MenuPaquetViewController controller = (MenuPaquetViewController) super.controller;
        controller.setListener(this);
        saveListPaquet = MenuPrincipal.getINSTANCE().getUserPaquets();
        controller.reloadListView();
    }

    /**
     * Création d'un nouveau paquet vide
     * @return Paquet vide avec un nom et catégorie génériques
     */
    @Override
    public PaquetDTO createPaquet() {
        // Créer le paquet et l'ajouter à la liste de paquet de l'utilisateur
        Paquet nouveauPaquet = new Paquet("Nouveau Paquet") ;
        user.addPaquet(nouveauPaquet);
        return nouveauPaquet.getDTO();
    }

    /**
     * Supprime un paquet de l'utilisateur
     * @param paquetDTO Paquet à supprimer
     */
    @Override
    public void deletePaquet(PaquetDTO paquetDTO) {
        Optional<Paquet> paquet = paquetDTO.getPaquet();
        PaquetManager paquetManager = MenuPrincipal.getINSTANCE().getPaquetManager();
        if(paquet.isEmpty()) {
            MenuPrincipal.getINSTANCE().showErrorPopup("Veuillez sélectionner un paquet à supprimer");
            return;
        }
        user.removePaquet(paquet.get().getId());
        paquetManager.remove(user, paquet.get());
    }

    /**
     * Filtre les paquets selon leurs catégories
     *
     * @param filter filtre à appliquer sur les catégories de chaque paquet
     */
    @Override
    public void filterPaquet(String filter) {
        saveListPaquet.stream()
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
                .map(Paquet::getDTO)
                .toList();
    }

    /**
     * @see MenuPaquetListener#importPaquet(File)
     */
    @Override
    public void importPaquet(File file) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Paquet newPaquet = objectMapper.readValue(file, Paquet.class);
            MenuPrincipal.getINSTANCE().getPrincipalUser().addPaquet(newPaquet);
            MenuPrincipal.getINSTANCE().getPaquetManager().save(MenuPrincipal.getINSTANCE().getPrincipalUser());
            System.out.println("Importation du paquet " + file.getName() + " réussie !");
        } catch (IOException e) {
            MenuPrincipal.getINSTANCE().showErrorPopup("Erreur lors de l'importation du paquet");
        }
    }

    /**
     * @see MenuPaquetListener#exportPaquet(PaquetDTO, String)
     */
    @Override
    public void exportPaquet(PaquetDTO paquet, String path) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            objectMapper.writeValue(new File(path+ ".json"), paquet.getPaquet().get());
            System.out.println("Exportation du paquet " + paquet.nom() + " réussie !");
        } catch (IOException e) {
            MenuPrincipal.getINSTANCE().showErrorPopup("Erreur lors de l'exportation du paquet");
        }
    }

    /**
     * @see MenuPaquetListener#sync()
     */
    @Override
    public void sync() {MenuPrincipal.getINSTANCE().showSyncMenu();}

    /**
     * @see MenuPaquetListener#showErrorPopup(String)
     * @param s message d'erreur à afficher
     */
    @Override
    public void showErrorPopup(String s) {MenuPrincipal.getINSTANCE().showErrorPopup(s);}

    /**
     * @see MenuPaquetListener#openProfile()
     */
    @Override
    public void openProfile() {MenuPrincipal.getINSTANCE().openProfile();}

    /**
     * @see MenuPaquetListener#openStore()
     */
    @Override
    public void openStore() {MenuPrincipal.getINSTANCE().openStore();}

    /**
     * Lancer le menu d'édition avec le paquet choisit par l'utilisateur
     * @param paquetDTO Paquet choisit par l'utilisateur à être modifié
     */
    @Override
    public void editPaquet(PaquetDTO paquetDTO) {
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
    public void cardStudy(PaquetDTO paquetDTO) {
        MenuPrincipal instance = MenuPrincipal.getINSTANCE();

        try {
            Optional<Paquet> paquet = paquetDTO.getPaquet();
            Paquet paquetInstance = paquet.get();
            if (paquetInstance.cards.size() == 0){
                instance.showErrorPopup("Vous devez creer des cartes avant de pouvoir les étudier !");
            }else{
                instance.showCardStudy(this,paquetInstance);
            }
        }catch (NullPointerException e){
            instance.showErrorPopup("Vous devez sélectionner un paquet à étudier !");
        }

    }

    @Override
    public void show(){
        super.show();
        updatePaquets();
    }

    /**
     * Met à jour la liste des paquets de l'utilisateur
     */
    public void updatePaquets() {
        saveListPaquet = MenuPrincipal.getINSTANCE().getUserPaquets();
        MenuPaquetViewController controller = (MenuPaquetViewController) super.controller;
        controller.reloadListView();
    }
}
