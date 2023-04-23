package com.ulb.infof307.g12.server.service;

import com.ulb.infof307.g12.server.dao.PaquetDao;
import com.ulb.infof307.g12.server.model.Paquet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PaquetService {
    private final PaquetDao paquetDao;
    @Autowired
    // Auto Wired crée une instance de PaquetDao (comme ça on doit pas s'occuper de le crée nous même)
    public PaquetService(@Qualifier("database") PaquetDao paquetDao) {
        this.paquetDao = paquetDao;
    }

    public void createPaquet(String nom){
        paquetDao.createPaquet(nom);
    }
    public Optional<Paquet> getPaquet(UUID id){
        return paquetDao.getPaquet(id);
    }

    public List<Paquet> getAllPaquets() {
        return paquetDao.getAllPaquets();
    }
}
