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
    private ArrayList<Card> cards = new ArrayList<>();

    @JsonCreator
    public Paquet(@JsonProperty("id") UUID id,@JsonProperty("nom") String nom, @JsonProperty("categories") ArrayList<String> categories,@JsonProperty("cartes") ArrayList<Card> cards) throws IllegalArgumentException{
        if(nom == null || nom.equals("") || categories == null)
            throw new IllegalArgumentException("Le paquet doit posséder un nom ou une catégorie");
        this.id = id;
        this.nom = nom;
        this.categories.addAll(categories);
        this.cards.addAll(cards);
    }

    /**
     * Fonction qui ajoute une carte au paquet
     * @param card carte à ajouter
     * @throws IllegalArgumentException si la carte existe déjà
     */
    public void addCarte(Card card) throws IllegalArgumentException{
        for(Card car: this.cards){
            if(car.getId()== card.getId()) {
                throw new IllegalArgumentException("La carte existe déjà");
            }
        }
        cards.add(card);
    }
    @Override
    public boolean equals(Object objects) {
        if(!(objects instanceof Paquet obj)) {
            return false;
        }
        return (this.getNom().equals(obj.getNom()) && this.getCategories().equals(obj.categories) && this.getId().equals(obj.getId()));
    }
}
