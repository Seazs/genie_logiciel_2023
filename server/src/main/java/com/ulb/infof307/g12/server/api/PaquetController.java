package com.ulb.infof307.g12.server.api;

import com.ulb.infof307.g12.server.model.Carte;
import com.ulb.infof307.g12.server.model.Paquet;
import com.ulb.infof307.g12.server.model.STATUS;
import com.ulb.infof307.g12.server.service.PaquetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import java.util.UUID;

import org.json.JSONObject;
/**
 * Class permettant de gérer les requêtes HTTP pour les paquets
 */
@RequestMapping("api/v1/paquet")
@RestController
public class PaquetController {
    private final PaquetService paquetService;

    /**
     * Constructeur de la classe
     * @param paquetService Service permettant de gérer les paquets
     */
    @Autowired
    public PaquetController(PaquetService paquetService) {
        this.paquetService = paquetService;
    }

    /**
     * Renvoie le paquet correspondant à l'id
     * @param id l'id du paquet
     * @return le paquet correspondant à l'id ou null s'il n'existe pas
     */
    @GetMapping("{id}")
    public Paquet getPaquet(@PathVariable UUID id){
        return paquetService.getPaquet(id);

    }

    /**
     * @return la liste de tous les paquets
     */
    @GetMapping
    public List<Paquet> getAllPaquets(){
        return paquetService.getAllPaquets();
    }

    /**
     * Crée un paquet avec le nom donné
     * @param paquetEnString paquet en string
     */
    @PostMapping
    public String createPaquet(@RequestBody String paquetEnString) {
        STATUS result = paquetService.createPaquet(paquetEnString);
        return result.toString();
    }

    /**
     * Supprime le paquet correspondant à l'id
     * @param id id du paquet a supprimer
     */
    @DeleteMapping("{id}")
    public String deletePaquet(@PathVariable UUID id){
        STATUS result = paquetService.deletePaquet(id);
        return result.toString();
    }


}
