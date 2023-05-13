package com.ulb.infof307.g12.server.dao;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ulb.infof307.g12.server.model.Paquet;
import com.ulb.infof307.g12.server.model.STATUS;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.util.*;


@Repository("database")
public class PaquetDataAccessService implements PaquetDao {
    public STATUS status;
    private List<Paquet> db_paquets = new ArrayList<>();
    private File db_paquet_folder;

    /**Cosntructeur
     * Charge en mémoire les paquets de l'utilisateur
     */
    public PaquetDataAccessService(){
        try {
            db_paquet_folder = new File("server/src/main/resources/stockage/paquet");
            if (! db_paquet_folder.exists()){
                // Création du dossier paquet
                if (!db_paquet_folder.mkdirs()){
                    throw new IOException("Paquet folder could not create.");
                };
            }else{
                if (db_paquet_folder.listFiles() != null) {
                    // Dossier paquet existe déjà et n'est pas vide
                    db_paquets = this.load();
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
        for (Paquet paquet : db_paquets) {
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                File f = new File(db_paquet_folder,paquet.getId()+".json");
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
            System.out.println("Successfully read JSON file and created object");
            if(db_paquets.stream().anyMatch(paquet -> paquet.getId().equals(newPaquet.getId()))) {
                status = STATUS.DUPLICATE;
                return status;
            }
                db_paquets.add(newPaquet);
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
        Optional<Paquet> paquet = db_paquets.stream().filter(paquet1 -> paquet1.getId().equals(id)).findFirst();
        return paquet.orElse(null);
    }

    /**
     * @see PaquetDao#getAllPaquets()
     */
    @Override
    public List<Paquet> getAllPaquets() {
        return Collections.unmodifiableList(db_paquets);
    }

    /**
     * @see PaquetDao#deletePaquet(UUID)
     */
    @Override
    public STATUS deletePaquet(UUID id) {
        db_paquets.removeIf(paquet -> paquet.getId().equals(id));
        return STATUS.OK;
    }

}
