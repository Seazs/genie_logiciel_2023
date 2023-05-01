package com.ulb.infof307.g12.server.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
public class User {

    private String username;
    @Setter
    private String password;
    @Getter
    @Setter
    private List<Paquet> listPaquet = new ArrayList<>();

    /**
     * Récupère un utilisateur à partir d'un DTO envoyé par le client sous forme de String
     * @param obj le DTO utilisateur sous forme de String
     */
    public User(String obj) throws IllegalArgumentException{
        if(!obj.contains("#"))
            throw new IllegalArgumentException("Pas de séparateur !");
        String[] args = obj.split("#");
        if(args.length != 2)
            throw new IllegalArgumentException("Nombre d'arguments détectés invalide !");
        this.username = args[0];
        this.password = args[1];
    }
    public User(String username, String password) throws IllegalArgumentException{
        this.username = username;
        this.password = password;
    }
}
