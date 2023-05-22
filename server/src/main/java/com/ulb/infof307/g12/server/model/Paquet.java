package com.ulb.infof307.g12.server.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.ArrayList;
import java.util.UUID;

public class Paquet {


    @Getter
    private final UUID id;
    @Getter
    @JsonProperty("nom")
    private final String nom;
    @Getter
    @JsonProperty("categories")
    private ArrayList<String> categories = new ArrayList<>();
    @Getter
    @JsonProperty("cartes")
    private ArrayList<Card> cards = new ArrayList<>();

    /**Constructeur pour la désérialisation avec jackson
     * @param id id du paquet
     * @param nom nom de paquet
     * @param categories catégorie(s) du paquet
     * @param cards liste des cartes du paquet
     * @throws IllegalArgumentException exception si le paquet de carte ne possède pas de catégorie
     */
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

    /**Verifie si deux paquets sont egaux
     * @param objects object
     * @return true si les paquets ont les mêmes noms, catégories et id
     */
    @Override
    public boolean equals(Object objects) {
        if(!(objects instanceof Paquet obj)) {
            return false;
        }
        return (this.getNom().equals(obj.getNom()) && this.getCategories().equals(obj.categories) && this.getId().equals(obj.getId()));
    }
}
