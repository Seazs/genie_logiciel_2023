package com.ulb.infof307.g12.server.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

@AllArgsConstructor
public class Paquet {


    @Getter

    private UUID id;
    @Getter
    private String nom;
    @Getter
    private ArrayList<String> categories = new ArrayList<>();
    @Getter
    private ArrayList<Carte> cartes= new ArrayList<>();


    /**
     * Crée un paquet dont le nom doit être unique.
     * @param nom nom du paquet
     * @param categorie catégorie du paquet
     * @throws IllegalArgumentException si le paquet n'a pas de catérorie ou de nom
     */
    public Paquet(String nom, String... categorie) throws IllegalArgumentException{
        if(nom == null || nom.equals("") || categorie == null)
            throw new IllegalArgumentException("Le paquet doit posséder un nom ou une catégorie");

        this.nom = nom;
        this.categories.addAll(Arrays.asList(categorie));
    }

    /**
     * Fonction qui ajoute une carte au paquet
     * @param carte
     * @throws IllegalArgumentException
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
