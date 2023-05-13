package com.ulb.infof307.g12.server.service;

import com.ulb.infof307.g12.server.dao.UserDAO;
import com.ulb.infof307.g12.server.model.STATUS;
import com.ulb.infof307.g12.server.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserDAO userDAO;

    /**
     * @param userDAO Dao permettant de gérer les utilisateurs (généré automatiquement par Spring)
     */
    @Autowired
    // Auto Wired crée une instance de PaquetDao (comme ça on ne doit pas s'occuper de le créer nous même)
    public UserService(@Qualifier("users") UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    /**
     * Récupère le mot de passe d'un utilisateur
     * @param username pseudo de l'utilisateur
     * @return le mot de passe de l'utilisateur
     */
    public String getPassword(String username){
        return userDAO.getPassword(username);
    }

    /**
     * Enregistre un utilisateur dans le serveur
     *
     * @param user l'utilisateur
     * @return le status de la création
     */
    public STATUS createUser(User user){
        return userDAO.createUser(user);
    }

    /**
     * @see UserDAO#updateUser(User)
     */
    public STATUS updateUser(User user){
        return userDAO.updateUser(user);
    }

    public List<User> getAllUsers() {
        return userDAO.getAllUsers();
    }

    public STATUS deleteUser(String username) {
        return userDAO.deleteUser(username);
    }
}
