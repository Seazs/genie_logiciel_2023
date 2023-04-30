package com.ulb.infof307.g12.server.api;

import com.ulb.infof307.g12.server.model.Paquet;
import com.ulb.infof307.g12.server.service.PaquetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

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
     * @param id
     * @return le paquet correspondant à l'id ou null si il n'existe pas
     */
    @GetMapping("{id}")
    public Paquet getPaquet(@PathVariable UUID id){
        paquetService.getPaquet(id);
        return paquetService.getPaquet(id)
                .orElse(null);
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
     * @param nom
     */
    @PostMapping
    public void createPaquet(@RequestBody String nom){
        paquetService.createPaquet(nom);
    }


}
