package com.ulb.infof307.g12.server.dao;

import com.ulb.infof307.g12.server.model.Paquet;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.fasterxml.jackson.databind.type.LogicalType.Collection;

@Repository("database")
// Ceci est la DataBase
public class PaquetDataAccessService implements PaquetDao{
    private static List<Paquet> DB = new ArrayList<>();

    /**
     * @see PaquetDao#createPaquet(UUID, String)
     */
    @Override
    public void createPaquet(UUID id, String nom) {
        DB.add(new Paquet(id,nom));
    }


    /**
     * @see PaquetDao#getPaquet(UUID)
     */
    @Override
    public Optional<Paquet> getPaquet(UUID id) {
        Optional<Paquet> t;

        return DB.stream().filter(paquet -> paquet.getId().equals(id)).findFirst();
    }

    /**
     * @see PaquetDao#getAllPaquets()
     */
    @Override
    public List<Paquet> getAllPaquets() {
        return DB;
    }

}
