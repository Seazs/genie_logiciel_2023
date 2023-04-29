package com.ulb.infof307.g12.server.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public class User {
    private String username;
    @Setter
    private String password;

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
}
