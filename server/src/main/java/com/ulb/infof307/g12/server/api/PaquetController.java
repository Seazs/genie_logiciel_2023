package com.ulb.infof307.g12.server.api;


import com.ulb.infof307.g12.server.model.Carte;
import com.ulb.infof307.g12.server.model.JsonParser;
import com.ulb.infof307.g12.server.model.Paquet;
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
     * @return le paquet correspondant à l'id ou null si il n'existe pas
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
    public UUID createPaquet(@RequestBody String paquetEnString) {
        JSONObject paquetEnJson = new JSONObject(paquetEnString);
        String nom = paquetEnJson.getString("nom");
        ArrayList<String> categories = JsonParser.parseJsonArray(paquetEnJson.getJSONArray("categories"));
        ArrayList<Carte> cartes = JsonParser.parseJsonArrayCarte(paquetEnJson.getJSONArray("cartes"));
        return paquetService.createPaquet(nom, categories, cartes);
    }

}
