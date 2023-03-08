package com.example.javafxmodule;

public class Paquet {
    private final String nom;
    private final String categorie;

    public Paquet(String nom, String categorie) {
        this.nom = nom;
        this.categorie = categorie;
    }

    public String getNom() {
        return nom;
    }

    public String getCategorie() {
        return categorie;
    }
}
