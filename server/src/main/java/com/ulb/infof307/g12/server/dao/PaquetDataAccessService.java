package com.ulb.infof307.g12.server.dao;

import com.ulb.infof307.g12.server.model.Carte;
import com.ulb.infof307.g12.server.model.Paquet;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Repository("database")
// Ceci est la DataBase
public class PaquetDataAccessService implements PaquetDao{
    final private static List<Paquet> DB = new ArrayList<>();

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

}
