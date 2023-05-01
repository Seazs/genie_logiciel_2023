package com.ulb.infof307.g12.server.dao;

import com.ulb.infof307.g12.server.model.Carte;
import com.ulb.infof307.g12.server.model.Paquet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public interface PaquetDao {

    void save() throws IOException;
    /**
     * Crée un paquet avec l'id et le nom donné
     * @param id id du paquet
     * @param nom nom du paquet
     */
    void createPaquet(UUID id , String nom, ArrayList<String> categories, ArrayList<Carte> cartes);

    /**
     * Crée un paquet avec le nom donné et un id généré aléatoirement
     * (Utilisé sans id donné)
     * @param nom nom du paquet
     */
    default UUID createPaquet(String nom, ArrayList<String> categories, ArrayList<Carte> cartes) {
        UUID id = UUID.randomUUID();
        createPaquet(id, nom, categories, cartes);
        return id;
    }

    /**
     * Renvoie le paquet correspondant à l'id
     * @param id id du paquet
     * @return le paquet correspondant à l'id ou null s'il n'existe pas
     */
    Paquet getPaquet(UUID id);

    /**
     * @return la liste de tous les paquets
     */
    List<Paquet> getAllPaquets();

    void deletePaquet(UUID id);
}
