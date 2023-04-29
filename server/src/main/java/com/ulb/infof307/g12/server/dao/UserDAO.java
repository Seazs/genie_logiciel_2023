package com.ulb.infof307.g12.server.dao;

import com.ulb.infof307.g12.server.model.User;

import java.util.Optional;

public interface UserDAO {

    /**
     * crée un compte utilisateur sur le serveur
     * @param username pseudo
     * @param password mot de passe
     */
    void createUser(String username, String password);

    /**
     * crée un utilisateur sur le serveur
     * @param user l'utilisateur
     */
    void createUser(User user);

    /**
     * Récupère le mot de passe d'un utilisateur
     * @param username pseudo de l'utilisateur
     * @return le mot de passe de l'utilisateur
     */
    String getPassword(String username);

}
