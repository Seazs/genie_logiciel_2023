package com.ulb.infof307.g12.server.dao;

import com.ulb.infof307.g12.server.model.Carte;
import com.ulb.infof307.g12.server.model.Paquet;
import com.ulb.infof307.g12.server.model.User;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static java.lang.Integer.parseInt;


@Repository("database")
// Ceci est la DataBase
public class PaquetDataAccessService implements PaquetDao{
    final private static List<Paquet> DB = new ArrayList<>();


    @Override
    public void save(User user) throws IOException {
        List<Paquet> listPaquet = user.getListPaquet();
        for (Paquet paquet : listPaquet){
            File paquetdatabase = new File("./stockage/"+user.getUsername(),paquet.getNom()); // On crée un fichier avec le nom du paquet dans le dossier de l'utilisateur
            FileWriter writer = new  FileWriter(paquetdatabase);
            BufferedWriter out = new BufferedWriter(writer);

            out.write(paquet.getNom());
            out.newLine();
            out.write(saveCategories(paquet));
            save_card(paquet, out);
            out.close();
        }


    }
    private void save_card(Paquet paquet, BufferedWriter out) throws IOException {
        for(int i = 0; i < paquet.getCartes().size() ; i++){ //Ecriture de toutes les cartes dans le fichier
            Carte carte = paquet.getCartes().get(i);
            out.newLine();
            out.write(carte.getType()+ "#"+ carte.getRecto() + "#" + carte.getVerso());
            out.write("#" + carte.getConnaissance());
        }

    }
    public static String saveCategories(Paquet paquet){
        String save = "";
        for (int i = 0; i < paquet.getCategories().size(); i++){
            paquet.getCategories().get(i);
            save = save + paquet.getCategories().get(i)+ "#";
        }
        return save;
    }

    /**
     * @see PaquetDao#createPaquet(UUID, String, ArrayList, ArrayList)
     */
    @Override
    public void createPaquet(UUID id, String nom, ArrayList<String> categories, ArrayList<Carte> cartes) {
        DB.add(new Paquet(id,nom, categories, cartes));
    }


    /**
     * @see PaquetDao#getPaquet(UUID)
     */
    @Override
    public Paquet getPaquet(UUID id) {
        Optional<Paquet> paquet = DB.stream().filter(paquet1 -> paquet1.getId().equals(id)).findFirst();
        return paquet.orElse(null);
    }

    /**
     * @see PaquetDao#getAllPaquets()
     */
    @Override
    public List<Paquet> getAllPaquets() {
        return DB;
    }

    @Override
    public void deletePaquet(UUID id){
        DB.removeIf(paquet -> paquet.getId().equals(id));
    }

}
