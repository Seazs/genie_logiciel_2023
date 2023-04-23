package com.ulb.infof307.g12.server.model;

import lombok.Getter;

import java.util.UUID;

public class Paquet {
    @Getter
    private String nom;
    @Getter
    private UUID id;
    public Paquet(UUID id, String nom){
        this.id = id;
        this.nom = nom;
    }
}
