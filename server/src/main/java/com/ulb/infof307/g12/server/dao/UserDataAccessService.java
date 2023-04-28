package com.ulb.infof307.g12.server.dao;

import com.ulb.infof307.g12.server.model.User;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository("user_db")
public class UserDataAccessService implements UserDAO{

    List<User> userList = new ArrayList<>();

    public UserDataAccessService() {
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

    @Override
    public void createUser(User user) {
        userList.add(user);
    }

    /**
     * @see UserDAO#getUser(String, String)
     * @param username pseudo
     * @return l'utilisateur demandé
     */
    @Override
    public Optional<User> getUser(String username,String password) {
        return userList.stream().filter(user -> user.equals(username,password)).findFirst();
    }
}
