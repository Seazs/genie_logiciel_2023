package ulb.infof307.g12.controller.javafx.store;

import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.stage.Stage;
import org.json.JSONArray;
import org.json.JSONObject;
import ulb.infof307.g12.controller.javafx.BaseController;
import ulb.infof307.g12.controller.javafx.connexion.MenuPrincipal;
import ulb.infof307.g12.model.Paquet;
import ulb.infof307.g12.model.STATUS;
import ulb.infof307.g12.model.User;
import ulb.infof307.g12.view.dto.PaquetDTO;
import ulb.infof307.g12.view.listeners.StoreViewListener;
import ulb.infof307.g12.view.store.StoreViewController;

import java.io.IOException;
import java.util.*;

/**
 * Controller for the store view
 */
public class StoreController extends BaseController implements StoreViewListener {

    private List<Paquet> saveListPaquet = new ArrayList<>();

    public StoreController(Stage stage) throws IOException {
        super(stage, StoreViewController.class.getResource("menuStore.fxml"), "");
        StoreViewController controller = (StoreViewController) super.controller;
        controller.setListener(this);
        controller.rechargerListView();
    }


    /**
     * @param paquetDTO télécharger le paquet et l'ajoute a sa collection
     */
    @Override
    public void downloadPaquet(PaquetDTO paquetDTO) {
        Optional<Paquet> paquetOptional = paquetDTO.getPaquet(saveListPaquet);
        MenuPrincipal singleton = MenuPrincipal.getINSTANCE();
        User currentUser = singleton.getPrincipalUser();
        paquetOptional.ifPresent(paquet -> {
            try {
                boolean result = currentUser.addPaquet(paquet);

                if(result)
                    singleton.showErrorPopup("Vous possédez déjà ce paquet !");
                else
                    singleton.getPaquetManager().save(currentUser);

            } catch (IOException e) {
                singleton.showErrorPopup("Erreur lors du téléchargement du paquet !");
            }
        });
    }

    /**
     * @return la liste des paquets du store pour l'affichage
     */
    @Override
    public Collection<PaquetDTO> getStorePaquets() {
        JSONArray paquetsJson = MenuPrincipal.getINSTANCE().getServer().getPaquets();
        ObjectMapper objectMapper = new ObjectMapper();

        saveListPaquet.clear();

        for (int i = 0; i < paquetsJson.length(); i++) {
            try {
                JSONObject paquet = paquetsJson.getJSONObject(i);
                Paquet newPaquet = objectMapper.readValue(paquet.toString(), Paquet.class);
                saveListPaquet.add(newPaquet);
            } catch (IOException e) {
                MenuPrincipal.getINSTANCE().showErrorPopup("Erreur lors du chargement du paquet");
            }
        }


        return saveListPaquet.stream()
                .map(Paquet::getDTO)
                .toList();
    }

    /**
     * @param paquet ajoute un paquet dans la liste des paquets du store
     */
    public void uploadPaquet(PaquetDTO paquet) throws IOException {
        if (paquet == null) {
            MenuPrincipal.getINSTANCE().showErrorPopup("Erreur, veuillez selectionner un paquet à uploader");
            return;
        }


        Optional<Paquet> paquetOptional = paquet.getPaquet();

        if (paquetOptional.isEmpty()) {
            MenuPrincipal.getINSTANCE().showErrorPopup("Erreur, veuillez selectionner un paquet à uploader");
            return;
        }

        STATUS status = MenuPrincipal.getINSTANCE().getServer().postPaquet(paquetOptional.get());

        System.out.println(status.getMsg());

        if (!STATUS.OK.equals(status))
            MenuPrincipal.getINSTANCE().showErrorPopup(status.getMsg());

    }

    /**
     * filtre les paquets selon leurs catégories
     *
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

    /**
     * Supprime un paquet du store
     * @param paquetDto paquet à supprimer
     */
    @Override
    public void deletePaquetStore(PaquetDTO paquetDto) {
        MenuPrincipal singleton = MenuPrincipal.getINSTANCE();
        User currentUser = singleton.getPrincipalUser();
        try {
            currentUser.belongToUser(paquetDto.getPaquet().get());//On vérifie que le paquet appartient bien à l'utilisateur
            MenuPrincipal.getINSTANCE().getServer().deletePaquet(UUID.fromString(paquetDto.uuid()));//Supprimer le paquet du store
        } catch (IllegalArgumentException | NoSuchElementException e) {
            singleton.showErrorPopup("Vous ne possédez pas ce paquet !");
        }
    }

    @Override
    public void show(){
        super.show();
        StoreViewController vue = (StoreViewController) super.controller;
        getStorePaquets();
        vue.rechargerListView();
    }
}
