package ulb.infof307.g12.controller.javafx.paquets;

import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import lombok.Getter;
import ulb.infof307.g12.controller.javafx.BaseController;
import ulb.infof307.g12.controller.javafx.connexion.MenuPrincipal;
import ulb.infof307.g12.controller.storage.PaquetManager;
import ulb.infof307.g12.model.Paquet;
import ulb.infof307.g12.model.User;
import ulb.infof307.g12.view.dto.PaquetDTO;
import ulb.infof307.g12.view.listeners.MenuPaquetListener;
import ulb.infof307.g12.view.paquets.MenuPaquetViewController;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class MenuPaquetController extends BaseController implements MenuPaquetListener {

    @Getter
    private final User user;

    private final FileChooser fileChooser = new FileChooser();

    private final MenuPrincipal instance = MenuPrincipal.getINSTANCE();

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
        //support uniquement du format .JSON lors de l'import/export des paquets
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("JSON", "*.json"));
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
        PaquetManager paquetManager = instance.getPaquetManager();
        if(paquet.isEmpty()) {
            MenuPrincipal.getINSTANCE().showErrorPopup("Veuillez sélectionner un paquet à supprimer");
            return;
        }
        user.removePaquet(paquet.get().getId());
        paquetManager.remove(user, paquet.get());
    }

    /**
     * Filtre les paquets selon leurs catégories
     * @param filter filtre à appliquer sur les catégories de chaque paquet
     */
    @Override
    public void filterPaquet(String filter) {
        saveListPaquet =  getUser().getListPaquet().stream()
                .filter(paquet -> paquet.getCategories().stream().anyMatch(category -> category.toLowerCase().contains(filter.toLowerCase())))
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
     * @see MenuPaquetListener#importPaquet()
     */
    @Override
    public void importPaquet() {

        fileChooser.setTitle("Select a file to import");

        File file = fileChooser.showOpenDialog(this.stage);
        if (file == null) {
            showErrorPopup("Aucun fichier n'a été sélectionné !");
            return;
        }

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Paquet newPaquet = objectMapper.readValue(file, Paquet.class);
            instance.getPrincipalUser().addPaquet(newPaquet);
            instance.getPaquetManager().save(MenuPrincipal.getINSTANCE().getPrincipalUser());
            updatePaquets();
            System.out.println("Importation du paquet " + file.getName() + " réussie !");
        } catch (IOException e) {
            showErrorPopup("Erreur lors de l'importation du paquet");
        }
    }

    /**
     * @see MenuPaquetListener#exportPaquet(PaquetDTO)
     */
    @Override
    public void exportPaquet(PaquetDTO paquet) {

        if(paquet.getPaquet().isEmpty()) {
            showErrorPopup("Erreur lors de la récupération du paquet");
            return;
        }

        fileChooser.setTitle("Select a file to export");
        File selectedFile = fileChooser.showSaveDialog(this.stage);

        //Cas où l'utilisateur annule l'export
        if(selectedFile == null)
            return;

        ObjectMapper objectMapper = new ObjectMapper();

        try {
            objectMapper.writeValue(selectedFile, paquet.getPaquet().get());
            System.out.println("Exportation du paquet " + paquet.nom() + " réussie !");
        } catch (IOException e) {
            showErrorPopup("Erreur lors de l'exportation du paquet");
        }
    }

    /**
     * @see MenuPaquetListener#sync()
     */
    @Override
    public void sync() {
        instance.showSyncMenu();
    }

    /**
     * @see MenuPaquetListener#showErrorPopup(String)
     * @param s message d'erreur à afficher
     */
    @Override
    public void showErrorPopup(String s) {
        instance.showErrorPopup(s);
    }

    /**
     * @see MenuPaquetListener#openProfile()
     */
    @Override
    public void openProfile() {
        instance.openProfile();
    }

    /**
     * @see MenuPaquetListener#openStore()
     */
    @Override
    public void openStore() {
        instance.openStore();
    }

    /**
     * Lancer le menu d'édition avec le paquet choisit par l'utilisateur
     * @param paquetDTO Paquet choisit par l'utilisateur à être modifié
     */
    @Override
    public void editPaquet(PaquetDTO paquetDTO) {
        Optional<Paquet> paquet = paquetDTO.getPaquet();
        paquet.ifPresent(instance::showMenuEdition);
    }

    /**
     * Démarre une session d'étude avec le paquet choisit par l'utilisateur
     * @param paquetDTO Paquet choisit par l'utilisateur à être étudié
     */
    @Override
    public void cardStudy(PaquetDTO paquetDTO) {
        Optional<Paquet> paquet = paquetDTO.getPaquet();

        if(paquet.isEmpty()){
            instance.showErrorPopup("Vous devez sélectionner un paquet à étudier !");
            return;
        }

        Paquet paquetInstance = paquet.get();

        if (paquetInstance.cards.size() == 0){
            instance.showErrorPopup("Vous devez creer des cartes avant de pouvoir les étudier !");
            return;
        }

        instance.showCardStudy(this,paquetInstance);
    }

    /**
     * Affiche la scène et met à jour la liste des paquets affichés
     * @see BaseController#show()
     */
    @Override
    public void show(){
        super.show();
        updatePaquets();
    }

    /**
     * Met à jour la liste des paquets de l'utilisateur
     */
    public void updatePaquets() {
        saveListPaquet = instance.getUserPaquets();
        MenuPaquetViewController controller = (MenuPaquetViewController) super.controller;
        controller.reloadListView();
    }
}
