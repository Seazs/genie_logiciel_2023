package com.ulb.infof307.g12.server.dao;

import com.ulb.infof307.g12.server.model.User;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository("users")
public class UserDataAccessService implements UserDAO{

    List<User> userList = new ArrayList<>();

    public UserDataAccessService() {
        //TODO: retirer la valeur de test
        User user = new User("admin","test");
        createUser(user);
        System.out.println("User: "+user.getUsername()+" Pass: "+user.getPassword());
    }

    /**
     * @see UserDAO#createUser(String, String)
     * @param username pseudo
     * @param password mot de passe
     */
    @Override
    public void createUser(String username, String password) {
        User user = new User(username,password);
        createUser(user);
    }

    /**
     * @see UserDAO#createUser(User)
     * @param user l'utilisateur
     */
    @Override
    public void createUser(User user) {
        userList.add(user);
    }

    /**
     * @see UserDAO#getPassword(String)
     * @param username pseudo
     * @return le mot de passe de l'utilisateur
     */
    @Override
    public String getPassword(String username) {
        return userList.stream()
                .filter(user -> user.getUsername().equals(username))
                .findFirst()
                .map(User::getPassword)
                .orElse(null);
    }
}
