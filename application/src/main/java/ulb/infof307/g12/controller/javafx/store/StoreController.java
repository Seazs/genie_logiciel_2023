package ulb.infof307.g12.controller.javafx.store;

import javafx.stage.Stage;
import org.json.JSONArray;
import ulb.infof307.g12.controller.JsonParser.JsonParser;
import ulb.infof307.g12.controller.javafx.BaseController;
import ulb.infof307.g12.controller.javafx.connexion.MenuPrincipal;
import ulb.infof307.g12.model.Paquet;
import ulb.infof307.g12.view.dto.PaquetDTO;
import ulb.infof307.g12.view.listeners.StoreVueListener;
import ulb.infof307.g12.view.store.StoreVueController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * Controller for the store view
 *
 */
public class StoreController extends BaseController implements StoreVueListener {

    private List<Paquet> saveListPaquet = new ArrayList<>();

    public StoreController(Stage stage) throws IOException {
        super(stage, StoreVueController.class.getResource("menuStore.fxml"), "");
        StoreVueController controller = (StoreVueController) super.controller;
        controller.setListener(this);
        controller.rechargerListView();
    }


    /**
     * @param paquet télécharger le paquet et l'ajoute a sa collection
     */
    @Override
    public void downloadPaquet(PaquetDTO paquet) {

    }

    /**
     * @return la liste des paquets du store pour l'affichage
     */
    @Override
    public Collection<PaquetDTO> getStorePaquets() {
        JSONArray paquetsJson = MenuPrincipal.getINSTANCE().getServer().getPaquets();
        JsonParser jsonParser = new JsonParser();
        saveListPaquet = jsonParser.jsonToListePaquets(paquetsJson);

        return saveListPaquet.stream()
                .map(Paquet::getDTO)
                .toList();
    }

    @Override
    public void deletePaquetStore(PaquetDTO paquet) {
        //
        //if (paquet.getUuid() in MenuPrincipal.getINSTANCE().getUser().getPaquets()){ //Rechercher parmi tt les uuid
        //  MenuPrincipal.getINSTANCE().getServer().deletePaquet(paquet.getUuid());
        //}

    }

    /**
     * @param paquet ajoute un paquet dans la liste des paquets du store
     */
    public void uploadPaquet(PaquetDTO paquet) throws IOException {
        try {
            Optional<Paquet> paquetOptional = paquet.getPaquet();
            MenuPrincipal.getINSTANCE().getServer().postPaquet(paquetOptional.get());
        }catch (NullPointerException e){
            MenuPrincipal.getINSTANCE().showErrorPopup("Erreur, veuillez selectionner un paquet à uploader");
        }

    }

    /**
     * filtre les paquets selon leurs catégories
     * @param recherche filtre à appliquer sur les catégories de chaque paquet
     * @return la liste des paquets filtrés par catégorie
     */
    @Override
    public Collection<PaquetDTO> filterPaquet(String recherche) {
        return saveListPaquet.stream()
                .filter(paquet -> paquet.getCategories().stream().anyMatch(category -> category.contains(recherche.toLowerCase())))
                .map(Paquet::getDTO)
                .toList();
    }

    @Override
    public Collection<PaquetDTO> getUserPaquets() {
        return MenuPrincipal.getINSTANCE().getUserPaquets().stream()
                .map(Paquet::getDTO)
                .toList();
    }


}
