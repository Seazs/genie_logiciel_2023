package com.ulb.infof307.g12.server.dao;

import com.ulb.infof307.g12.server.model.STATUS;
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
     * @param user l'utilisateur
     * @return
     * @see UserDAO#createUser(User)
     */
    @Override
    public STATUS createUser(User user) {
        System.out.println(user.getUsername()+" "+user.getPassword());
        if(userList.stream().anyMatch(u -> u.getUsername().equals(user.getUsername()))){
            return STATUS.USERNAME_DOES_ALREADY_EXIST;
        }
        userList.add(user);
        return STATUS.OK;
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

    @Override
    public STATUS updateUser(User user) {
        Optional<User> userToUpdate = userList.stream()
                .filter(u -> u.getUsername().equals(user.getUsername()))
                .findFirst();

        if(userToUpdate.isEmpty()){
            return STATUS.USERNAME_DOES_NOT_EXIST;
        }

        userToUpdate.get().setPassword(user.getPassword());
        return STATUS.OK;
    }
}
