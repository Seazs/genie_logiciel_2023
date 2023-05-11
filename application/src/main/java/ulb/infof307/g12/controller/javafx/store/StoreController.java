package ulb.infof307.g12.controller.javafx.store;

import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.stage.Stage;
import org.json.JSONArray;
import org.json.JSONObject;
import ulb.infof307.g12.controller.javafx.BaseController;
import ulb.infof307.g12.controller.javafx.connexion.MenuPrincipal;
import ulb.infof307.g12.model.Paquet;
import ulb.infof307.g12.model.STATUS;
import ulb.infof307.g12.model.Utilisateur;
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
     * @param paquetDTO télécharger le paquet et l'ajoute a sa collection
     */
    @Override
    public void downloadPaquet(PaquetDTO paquetDTO) {
        Optional<Paquet> paquetOptional = paquetDTO.getPaquet(saveListPaquet);
        MenuPrincipal singleton = MenuPrincipal.getINSTANCE();
        Utilisateur currentUser = singleton.getUserPrincipale();
        paquetOptional.ifPresent(paquet -> {
            try {
                boolean result = currentUser.addPaquet(paquet);

                if(result)
                    singleton.showErrorPopup("Vous possédez déjà ce paquet !");
                else
                    singleton.getGestionnairePaquet().save(currentUser);

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
        for (int i = 0; i < paquetsJson.length(); i++) {
            try {
                JSONObject paquet = paquetsJson.getJSONObject(i);
                System.out.println(paquet.toString());
                Paquet newPaquet = objectMapper.readValue(paquet.toString(), Paquet.class);
                System.out.println("Successfully read JSON file and created object");
                saveListPaquet.add(newPaquet);
            } catch (IOException e) {
                MenuPrincipal.getINSTANCE().showErrorPopup("Erreur lors du chargement du paquet");
                e.printStackTrace();
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
}
