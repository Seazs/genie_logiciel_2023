package com.ulb.infof307.g12.server.dao;

import com.ulb.infof307.g12.server.model.Carte;
import com.ulb.infof307.g12.server.model.Paquet;
import com.ulb.infof307.g12.server.model.STATUS;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Repository("database")
public class PaquetDataAccessService implements PaquetDao {
    public STATUS status;
    private List<Paquet> db_paquets = new ArrayList<>();
    private File db_paquet_folder;

    public PaquetDataAccessService(){
        try {
            db_paquet_folder = new File("src/main/resources/stockage/paquet");
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
     * Sauvegarder l'état actuel de la base de données dans un fichier .txt
     * @throws IOException Erreur d'écriture dans le fichier
     */
    @Override
    public void save() throws IOException {
        for (Paquet paquet : db_paquets) {
            // On crée un fichier avec le nom du paquet dans le dossier de l'utilisateur
            File db_paquet_file = new File(db_paquet_folder, paquet.getNom());
            //Crée le fichier s'il n'existe pas
            db_paquet_file.createNewFile();

            FileWriter writer = new FileWriter(db_paquet_file);
            BufferedWriter out = new BufferedWriter(writer);
            // Écrire le nom du paquet
            out.write(paquet.getNom());
            out.newLine();
            // Écrire ses catégories
            out.write(categoriesToString(paquet));
            // Écrire les cartes dans le fichier
            save_card(paquet, out);
            out.close();
        }
    }

    /**
     * Genère un string des catégories d'un paquet
     *
     * @param paquet Paquet dont générer les strings des catégories
     * @return String des catégories du paquet
     */
    public static String categoriesToString(Paquet paquet) {
        StringBuilder save = new StringBuilder();
        for (int i = 0; i < paquet.getCategories().size(); i++) {
            paquet.getCategories().get(i);
            save.append(paquet.getCategories().get(i));
            save.append("#");
        }
        return save.toString();
    }

    /**
     * Sauvegarder les cartes appartenant à un paquet dans le fichier .txt
     *
     * @param paquet Paquet dont on veut sauvegarder les cartes
     * @param out    BufferedWriter
     * @throws IOException Erreur d'écriture dans le fichier
     */
    private void save_card(Paquet paquet, BufferedWriter out) throws IOException {
        // Écriture de toutes les cartes dans le fichier
        for (int i = 0; i < paquet.getCartes().size(); i++) {
            Carte carte = paquet.getCartes().get(i);
            out.newLine();
            out.write(carte.getType() + "#" + carte.getRecto() + "#" + carte.getVerso());
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
        File[] listOfFilePaquet = db_paquet_folder.listFiles();
        List<Paquet> loadedListOfPaquet = new ArrayList<>();

        //Si le dossier est vide, on renvoie une liste vide
        assert listOfFilePaquet != null;

        for (File file : listOfFilePaquet) {
            //Pour chaque fichier dans le dossier
            FileReader fileReader = new FileReader(file);
            BufferedReader reader = new BufferedReader(fileReader);
            // Lire les noms et catégories
            String lineNom = reader.readLine();
            String lineCategorie = reader.readLine();
            // Créer le paquet
            Paquet newPaquet = new Paquet(lineNom, loadCategories(lineCategorie));
            // Lire et créer les cartes
            load_all_cards(reader, newPaquet);
            // Ajouter les cartes dans le paquet
            loadedListOfPaquet.add(newPaquet);
            reader.close();
        }
        return loadedListOfPaquet;

    }

    /**
     * Charger toutes les cartes du paquet
     *
     * @param reader    BufferedReader
     * @param newPaquet Paquet dont on charge les cartes
     * @throws IOException Erreurs de lecture du fichier
     */
    private void load_all_cards(BufferedReader reader, Paquet newPaquet) throws IOException {
        int i = 0;
        String line;
        while ((line = reader.readLine()) != null) {
            String[] lineData = line.split("#");
            //Si la carte est un simple
            if (lineData[0].equals("rv")) {
                Carte bufferCarte = new Carte();
                bufferCarte.setId(i);
                bufferCarte.setRecto(lineData[1]);
                bufferCarte.setVerso(lineData[2]);
                newPaquet.ajouterCarte(bufferCarte);
            }
            i++;
        }
    }


    /**
     * Récupération des catégories sous forme de liste
     *
     * @param line de type cat1#cat2#...#cat3
     * @return String[] des catégories
     */
    private String[] loadCategories(String line) {
        try {return line.split("#");
        } catch (NullPointerException e) {
            return new String[0];
        }

    }


    /**
     * @see PaquetDao#createPaquet(UUID, String, ArrayList, ArrayList)
     */
    @Override
    public void createPaquet(UUID id, String nom, ArrayList<String> categories, ArrayList<Carte> cartes) {
        db_paquets.add(new Paquet(id, nom, categories, cartes));
        try {
            this.save();
            System.out.println("SAVING DB...");
        } catch (IOException exception) {
            status = STATUS.DB_COULD_NOT_BE_SAVED;
            System.out.println("ERROR DB: " + status.getMsg());
            exception.printStackTrace();

        }
        status = STATUS.OK;
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
        return db_paquets;
    }

    @Override
    public void deletePaquet(UUID id) {
        db_paquets.removeIf(paquet -> paquet.getId().equals(id));
    }

}
