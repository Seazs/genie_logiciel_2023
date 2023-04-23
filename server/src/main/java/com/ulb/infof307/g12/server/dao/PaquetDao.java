package com.ulb.infof307.g12.server.dao;

import com.ulb.infof307.g12.server.model.Paquet;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PaquetDao {
    public void createPaquet(UUID id ,String nom);
    default public void createPaquet(String nom) {
        UUID id = UUID.randomUUID();
        createPaquet(id, nom);
    }
    public Optional<Paquet> getPaquet(UUID id);

    List<Paquet> getAllPaquets();
}
