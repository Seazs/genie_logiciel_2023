package com.ulb.infof307.g12.server.service;

import com.ulb.infof307.g12.server.dao.UserDAO;
import com.ulb.infof307.g12.server.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.Optional;
import java.util.UUID;

public class UserService {
    private final UserDAO userDAO;

    /**
     * @param userDAO Dao permettant de gérer les utilisateurs (généré automatiquement par Spring)
     */
    @Autowired
    // Auto Wired crée une instance de PaquetDao (comme ça on doit pas s'occuper de le créer nous même)
    public UserService(@Qualifier("user_db") UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    /**
     * Récupère un utilisateur selon son pseudo
     * @param username pseudo
     * @return l'utilisateur ou null si il n'existe pas
     */
    public Optional<User> getUser(String username, String password){
        return userDAO.getUser(username, password);
    }

    /**
     * Enregistre un utilisateur dans le serveur
     * @param username pseudo
     * @param password mot de passe
     */
    public void createUser(String username, String password){
        userDAO.createUser(username, password);
    }

    /**
     * Enregistre un utilisateur dans le serveur
     * @param user l'utilisateur
     */
    public void createUser(User user){
        userDAO.createUser(user);
    }
}
