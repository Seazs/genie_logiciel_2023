package com.ulb.infof307.g12.server.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.UUID;

@AllArgsConstructor
public class Paquet {


    @Getter

    private UUID id;
    @Getter
    private String nom;
    @Getter
    private ArrayList<String> categories;
    @Getter
    private ArrayList<Carte> cartes;



}
