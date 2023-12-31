package com.ulb.infof307.g12.server.dao;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ulb.infof307.g12.server.ServerApplication;
import com.ulb.infof307.g12.server.model.Paquet;
import com.ulb.infof307.g12.server.model.STATUS;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;


@Repository("database")
public class PaquetDataAccessService implements PaquetDao {
    public STATUS status;
    private List<Paquet> db_paquetsStore = new ArrayList<>();
    private File db_paquetStore_folder;
    private File db_paquetUser_folder;

    /**Cosntructeur
     * Charge en mémoire les paquets de l'utilisateur
     */
    public PaquetDataAccessService(){
        try {
            db_paquetStore_folder = new File(ServerApplication.getStockageFolderPath()+ "store/");
            db_paquetUser_folder = new File(ServerApplication.getStockageFolderPath() + "paquetUser/");
            if (!db_paquetStore_folder.exists()) {
                // Création du dossier paquet
                if (!db_paquetStore_folder.mkdirs()) {
                    throw new IOException("Paquet folder could not create.");
                }
            } else {
                if (db_paquetStore_folder.listFiles() != null) {
                    // Dossier paquet existe déjà et n'est pas vide
                    db_paquetsStore = this.load();
                }
            }
            if (!db_paquetUser_folder.mkdirs()) {
                if (!db_paquetStore_folder.mkdirs()) {
                    throw new IOException("Paquet folder could not create.");
                }
            }
            status = STATUS.OK;
        } catch (IOException exception) {
            status = STATUS.DB_COULD_NOT_BE_LOADED;
        }
    }


    /**
     * Sauvegarder l'état actuel de la base de données dans un fichier .json
     * @throws IOException Erreur d'écriture dans le fichier
     */
    @Override
    public void save() throws IOException {
        for (Paquet paquet : db_paquetsStore) {
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                File f = new File(db_paquetStore_folder, paquet.getId() + ".json");
                f.createNewFile();
                objectMapper.writeValue(f, paquet);
            } catch (IOException e) {
                status = STATUS.DB_COULD_NOT_BE_SAVED;
                System.out.println("ERROR DB: " + status.getMsg());
            }
        }
    }

    /**
     * Charger la base de données des paquets en mémoire
     *
     * @return Liste contenant tous les paquets de la database
     */
    public List<Paquet> load() throws IOException {
        System.out.println("LOADING DB...");
        //Enumère les fichiers dans le dossier de l'utilisateur
        File[] listOfFilePaquet = db_paquetStore_folder.listFiles();
        List<Paquet> loadedListOfPaquet = new ArrayList<>();

        //Si le dossier est vide, on renvoie une liste vide
        assert listOfFilePaquet != null;
        //Pour chaque fichier dans le dossier de l'utilisateur
        for (File file : listOfFilePaquet) {
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                Paquet newPaquet = objectMapper.readValue(file, Paquet.class);
                loadedListOfPaquet.add(newPaquet);
            } catch (IOException e) {
                status = STATUS.DB_COULD_NOT_BE_LOADED;
                System.out.println("ERROR DB: " + status.getMsg());
            }
        }
        return loadedListOfPaquet;
    }

    /**
     * @see PaquetDao#createPaquet(String)
     */
    @Override
    public STATUS createPaquet(String paquetString) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Paquet newPaquet = objectMapper.readValue(paquetString, Paquet.class);
            if (db_paquetsStore.stream().anyMatch(paquet -> paquet.getId().equals(newPaquet.getId()))) {
                status = STATUS.DUPLICATE;
                return status;
            }
            db_paquetsStore.add(newPaquet);
        } catch (IOException e) {
            status = STATUS.FILE_NOT_LOADED;
            System.out.println("ERROR DB: " + status.getMsg());
            return status;
        }
        try {
            this.save();
            System.out.println("SAVING DB...");
        } catch (IOException exception) {
            status = STATUS.DB_COULD_NOT_BE_SAVED;
            System.out.println("ERROR DB: " + status.getMsg());
            return status;

        }
        status = STATUS.OK;
        return status;
    }


    /**
     * @see PaquetDao#getPaquet(UUID)
     */
    @Override
    public Paquet getPaquet(UUID id) {
        Optional<Paquet> paquet = db_paquetsStore.stream().filter(paquet1 -> paquet1.getId().equals(id)).findFirst();
        return paquet.orElse(null);
    }

    /**
     * @see PaquetDao#getAllPaquets()
     */
    @Override
    public List<Paquet> getAllPaquets() {
        return Collections.unmodifiableList(db_paquetsStore);
    }

    /**
     * @see PaquetDao#deletePaquet(UUID)
     */
    @Override
    public STATUS deletePaquet(UUID id) {
        db_paquetsStore.removeIf(paquet -> paquet.getId().equals(id));

        // Supprimer le json correspondant du stockage store
        File[] listOfFilePaquet = db_paquetStore_folder.listFiles();
        //Si le dossier est vide, on renvoie une liste vide
        assert listOfFilePaquet != null;
        //Pour chaque fichier dans le dossier de l'utilisateur
        for (File file : listOfFilePaquet) {
            if (file.getName().equals(id+".json")){
                file.delete();
            }
        }
        return STATUS.OK;
    }

    /**
     * Syncroniser les paquets de l'utilisateur avec la base de données
     * @param infoString String json contenant les paquets de l'utilisateur
     * @return STATUS
     */
    @Override
    public STATUS syncPaquets(String infoString) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(infoString);
            JsonNode paquets = rootNode.get("paquets");
            String username = rootNode.get("username").asText();
            File f = new File(db_paquetUser_folder, username + ".json");
            f.createNewFile();
            objectMapper.writeValue(f, paquets);
        } catch (IOException e) {
            status = STATUS.COULD_NOT_SYNC;
            return status;
        }
        status = STATUS.OK;
        return status;
    }

    /**
     * @param username Nom de l'utilisateur
     * @return String json contenant les paquets de l'utilisateur, sinon message d'erreur
     * @see PaquetDao#getUserPaquet(String)
     */
    @Override
    public String getUserPaquet(String username) {
        String filePath = db_paquetUser_folder + "/" + username + ".json";
        try {
            byte[] jsonData = Files.readAllBytes(Paths.get(filePath));
            String jsonString = new String(jsonData, "UTF-8");
            return jsonString;
        } catch (Exception e) {
            return "";
        }
    }
}
