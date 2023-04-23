package com.ulb.infof307.g12.server.dao;

import com.ulb.infof307.g12.server.model.Paquet;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PaquetDao {
    /**
     * Crée un paquet avec l'id et le nom donné
     * @param id id du paquet
     * @param nom nom du paquet
     */
    public void createPaquet(UUID id ,String nom);

    /**
     * Crée un paquet avec le nom donné et un id généré aléatoirement
     * (Utilisé sans id donné)
     * @param nom nom du paquet
     */
    default public void createPaquet(String nom) {
        UUID id = UUID.randomUUID();
        createPaquet(id, nom);
    }

    /**
     * Renvoie le paquet correspondant à l'id
     * @param id id du paquet
     * @return le paquet correspondant à l'id ou null si il n'existe pas
     */
    public Optional<Paquet> getPaquet(UUID id);

    /**
     * @return la liste de tous les paquets
     */
    List<Paquet> getAllPaquets();
}
