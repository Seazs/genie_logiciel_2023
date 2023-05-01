package com.ulb.infof307.g12.server.service;

import com.ulb.infof307.g12.server.dao.PaquetDao;
import com.ulb.infof307.g12.server.model.Carte;
import com.ulb.infof307.g12.server.model.Paquet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class PaquetService {
    private final PaquetDao paquetDao;

    /**
     * @param paquetDao Dao permettant de gérer les paquets (généré automatiquement par Spring)
     */
    @Autowired
    // Auto Wired crée une instance de PaquetDao (comme ça on doit pas s'occuper de le crée nous même)
    public PaquetService(@Qualifier("database") PaquetDao paquetDao) {
        this.paquetDao = paquetDao;
    }

    /**
     * Crée un paquet avec le nom donné
     * @param nom nom du paquet
     */
    public void createPaquet(String nom, ArrayList<String> categories, ArrayList<Carte> cartes){
        paquetDao.createPaquet(nom, categories, cartes) ;
    }

    /**
     * Renvoie le paquet correspondant à l'id
     * @param id id du paquet
     * @return le paquet correspondant à l'id ou null si il n'existe pas
     */
    public Paquet getPaquet(UUID id){
        return paquetDao.getPaquet(id);
    }

    /**
     * @return la liste de tous les paquets
     */
    public List<Paquet> getAllPaquets() {
        return paquetDao.getAllPaquets();
    }

    public void deletePaquet(UUID id){
        paquetDao.deletePaquet(id);
    }
}
