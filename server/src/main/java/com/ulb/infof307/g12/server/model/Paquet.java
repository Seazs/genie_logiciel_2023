package com.ulb.infof307.g12.server.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.ArrayList;
import java.util.UUID;

public class Paquet {


    @Getter
    private UUID id;
    @Getter
    @JsonProperty("nom")
    private String nom;
    @Getter
    @JsonProperty("categories")
    private ArrayList<String> categories = new ArrayList<>();
    @Getter
    @JsonProperty("cartes")
    private ArrayList<Carte> cartes= new ArrayList<>();

    @JsonCreator
    public Paquet(@JsonProperty("id") UUID id,@JsonProperty("nom") String nom, @JsonProperty("categories") ArrayList<String> categories,@JsonProperty("cartes") ArrayList<Carte> cartes) throws IllegalArgumentException{
        if(nom == null || nom.equals("") || categories == null)
            throw new IllegalArgumentException("Le paquet doit posséder un nom ou une catégorie");
        this.id = id;
        this.nom = nom;
        this.categories.addAll(categories);
        this.cartes.addAll(cartes);
    }

    /**
     * Fonction qui ajoute une carte au paquet
     * @param carte carte à ajouter
     * @throws IllegalArgumentException si la carte existe déjà
     */
    public void ajouterCarte(Carte carte) throws IllegalArgumentException{
        for(Carte car: this.cartes){
            if(car.getId()==carte.getId()) {
                throw new IllegalArgumentException("La carte existe déjà");
            }
        }
        cartes.add(carte);
    }
}
