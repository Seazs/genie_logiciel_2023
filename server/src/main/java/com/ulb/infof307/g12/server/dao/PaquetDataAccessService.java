package com.ulb.infof307.g12.server.dao;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ulb.infof307.g12.server.model.Paquet;
import com.ulb.infof307.g12.server.model.STATUS;
import org.json.JSONObject;
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
    private File db_paquet_folder;
    private File db_paquetUser_folder;

    public PaquetDataAccessService() {
        try {
            db_paquet_folder = new File("server/src/main/resources/stockage/paquet");
            db_paquetUser_folder = new File("server/src/main/resources/stockage/paquetUser");
            if (!db_paquet_folder.exists()) {
                // Création du dossier paquet
                if (!db_paquet_folder.mkdirs()) {
                    throw new IOException("Paquet folder could not create.");
                }
            } else {
                if (db_paquet_folder.listFiles() != null) {
                    // Dossier paquet existe déjà et n'est pas vide
                    db_paquetsStore = this.load();
                }
            }
            if(!db_paquetUser_folder.mkdirs()){
                if (!db_paquet_folder.mkdirs()) {
                    throw new IOException("Paquet folder could not create.");
                }
            }
            status = STATUS.OK;
        } catch (IOException exception) {
            status = STATUS.DB_COULD_NOT_BE_LOADED;
        }
    }



    /**
     * Sauvegarder l'état actuel de la base de données dans un fichier .txt
     *
     * @throws IOException Erreur d'écriture dans le fichier
     */
    @Override
    public void save() throws IOException {
        for (Paquet paquet : db_paquetsStore) {
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                File f = new File(db_paquet_folder, paquet.getId() + ".json");
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
        File[] listOfFilePaquet = db_paquet_folder.listFiles(); //Enumère les fichiers dans le dossier de l'utilisateur
        List<Paquet> loadedListOfPaquet = new ArrayList<Paquet>();

        assert listOfFilePaquet != null; //Si le dossier est vide, on renvoie une liste vide
        for (File file : listOfFilePaquet) { //Pour chaque fichier dans le dossier de l'utilisateur
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                Paquet newPaquet = objectMapper.readValue(file, Paquet.class);
                System.out.println("Successfully read JSON file and created object");
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

    @Override
    public STATUS deletePaquet(UUID id) {
        db_paquetsStore.removeIf(paquet -> paquet.getId().equals(id));
        return STATUS.OK;
    }

    @Override
    public STATUS syncPaquets(String infoString) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(infoString);
            JsonNode paquets = rootNode.get("paquets");
            String username = rootNode.get("username").asText();
            File f = new File(db_paquetUser_folder + "/", username + ".json");
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
     * @see PaquetDao#getUserPaquet(String)
     * @param username Nom de l'utilisateur
     * @return
     */
    @Override
    public String getUserPaquet(String username) {
        /*
        File[] listOfFolderUser = db_paquetUser_folder.listFiles();
        List<Paquet> paquets = new ArrayList<>();
        assert listOfFolderUser != null;
        for (File file : listOfFolderUser){
            if (file.getName().equals(username + ".json")){
                ObjectMapper objectMapper = new ObjectMapper();
                try {
                    paquets = objectMapper.readValue(file, new TypeReference<List<Paquet>>() {});
                    return paquets;
                } catch (IOException e) {
                    return null;
                }
            }
        }
        return paquets;
        }
        */
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
