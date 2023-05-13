package com.ulb.infof307.g12.server.service;

import com.ulb.infof307.g12.server.dao.PaquetDao;
import com.ulb.infof307.g12.server.model.Carte;
import com.ulb.infof307.g12.server.model.Paquet;
import com.ulb.infof307.g12.server.model.STATUS;
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
    // Auto Wired crée une instance de PaquetDao (comme ça on ne doit pas s'occuper de le créer nous même)
    public PaquetService(@Qualifier("database") PaquetDao paquetDao) {
        this.paquetDao = paquetDao;
    }

    /**
     * Crée un paquet avec le nom donné
     * @param paquetString nom du paquet
     */
    public STATUS createPaquet(String paquetString){
        return paquetDao.createPaquet(paquetString) ;
    }

    /**
     * Renvoie le paquet correspondant à l'id
     * @param id id du paquet
     * @return le paquet correspondant à l'id ou null s'il n'existe pas
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

    /**
     * Supprime le paquet correspondant à l'id
     * @param id id du paquet à supprimer
     * @return
     */
    public STATUS deletePaquet(UUID id){
        return paquetDao.deletePaquet(id);
    }
}
