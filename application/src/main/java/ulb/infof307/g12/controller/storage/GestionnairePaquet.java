package ulb.infof307.g12.controller.storage;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import ulb.infof307.g12.Main;
import ulb.infof307.g12.controller.javafx.connexion.MenuPrincipal;
import ulb.infof307.g12.model.Paquet;
import ulb.infof307.g12.model.Utilisateur;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GestionnairePaquet {
    @Getter
    private final String folderStockagePath;

    /**
     * Constructeur du gestionnaire de paquet
     */
    public GestionnairePaquet() {
        folderStockagePath = Main.getStockageFolderPath();
    }

    /**
     * Destiné aux tests
     * @param folderPath chemin du dossier de stockage
     */
    public GestionnairePaquet(String folderPath) {
        folderStockagePath = folderPath+"/";
    }
    /**
     * Sauvegarde un paquet de cartes sous forme de fichier Json dans le dossier de l'utilisateur.
     * @param user utilisateur
     * @throws IOException exception
     */
    public void save(Utilisateur user) throws IOException {
        List<Paquet> listPaquet = user.getListPaquet();
        for (Paquet paquet : listPaquet){
            savePaquet(user, paquet);
        }
    }

    /**
     * Sauvegarde un paquet de cartes sous forme de fichier Json dans le dossier de l'utilisateur.
     * @param user utilisateur
     * @param paquet paquet à sauvegarder
     */
    public void savePaquet(Utilisateur user, Paquet paquet) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            File f = new File(folderStockagePath+ user.getPseudo(), paquet.getId()+".json");
            f.getParentFile().mkdirs();
            f.createNewFile();
            objectMapper.writeValue(f, paquet);
        } catch (IOException e) {
            MenuPrincipal.getINSTANCE().showErrorPopup("Erreur lors de la sauvegarde du paquet "+ paquet.getNom());
        }
    }

    /**
     * Charge la liste des paquets correspondant à l'utilisateur en mémoire.
     * @param user utilisateur dont on veut les paquets
     * @return liste des paquets de l'utilisateur
     */
    public List<Paquet> load(Utilisateur user) {

        File userfolder = new File(folderStockagePath+user.getPseudo());
        File[] listOfFilePaquet = userfolder.listFiles(); //Enumère les fichiers dans le dossier de l'utilisateur
        List<Paquet> loadedListOfPaquet = new ArrayList<>();

        assert listOfFilePaquet != null; //Si le dossier est vide, on renvoie une liste vide
        for (File file : listOfFilePaquet) { //Pour chaque fichier dans le dossier de l'utilisateur
            loadPaquet(loadedListOfPaquet, file);
        }
        return loadedListOfPaquet;
    }

    /**
     * Charge un paquet en mémoire
     * @param loadedListOfPaquet liste des paquets chargés
     * @param file fichier à charger
     */
    public void loadPaquet(List<Paquet> loadedListOfPaquet, File file) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Paquet newPaquet = objectMapper.readValue(file, Paquet.class);
            loadedListOfPaquet.add(newPaquet);
        } catch (IOException e) {
            MenuPrincipal.getINSTANCE().showErrorPopup("Erreur lors du chargement du paquet");
        }
    }


    /**
     * Supprime le fichier associé au paquet voulu et supprime le paquet de la mémoire
     * @param user utilisateur
     * @param paquet paquet à supprimer
     */
    public void remove(Utilisateur user, Paquet paquet) {
        File f = new File(folderStockagePath+user.getPseudo(),paquet.getId()+".json");
        try{
            if(f.delete())
                user.removePaquet(paquet.getId());
        } catch (Exception e) {
            MenuPrincipal.getINSTANCE().showErrorPopup("Impossible de retirer le paquet "+paquet.getNom()+" rattaché à l'utilisateur "+user.getPseudo()+" !");
        }


    }

}
