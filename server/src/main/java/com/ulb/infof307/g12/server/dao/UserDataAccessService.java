package com.ulb.infof307.g12.server.dao;

import com.ulb.infof307.g12.server.database.Database;
import com.ulb.infof307.g12.server.model.STATUS;
import com.ulb.infof307.g12.server.model.User;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.*;

@Repository("users")
public class UserDataAccessService implements UserDAO{

    private Database db = Database.getInstance();

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
        // Vérification de nom d'utilisateur unique
        List<User> userList = db.getDb_user();
        if(userList.stream().anyMatch(u -> u.getUsername().equals(user.getUsername()))){
            return STATUS.USERNAME_DOES_ALREADY_EXIST;
        }
        userList.add(user);
        // Si nom d'utilisateur unique, ajouter à la db
        try {
            db.save();
        } catch (IOException e) {
            return STATUS.DB_COULD_NOT_BE_SAVED;
        }
        return STATUS.OK;
    }

    /**
     * @see UserDAO#getPassword(String)
     * @param username pseudo
     * @return le mot de passe de l'utilisateur
     */
    @Override
    public String getPassword(String username) {
        List<User> userList = db.getDb_user();
        return userList.stream()
                .filter(user -> user.getUsername().equals(username))
                .findFirst()
                .map(User::getPassword)
                .orElse(null);
    }

    @Override
    public STATUS updateUser(User user) {
        // Charger la liste courant d'utilisateur de la db
        List<User> userList = db.getDb_user();
        // Trouver le bon utilisateur
        Optional<User> userToUpdate = userList.stream()
                .filter(u -> u.getUsername().equals(user.getUsername()))
                .findFirst();

        if(userToUpdate.isEmpty()){
            return STATUS.USERNAME_DOES_NOT_EXIST;
        }

        userToUpdate.get().setPassword(user.getPassword());
        // Sauvegarder la db
        try {
            db.save();
        }catch (IOException exception){
            return STATUS.DB_COULD_NOT_BE_SAVED;
        }

        return STATUS.OK;
    }

    @Override
    public List<User> getAllUsers() {
        return db.getDb_user();
    }

    public STATUS deleteUser(String username){
        List<User> listUser =  db.getDb_user();
        listUser.removeIf(person -> person.getUsername().equals(username));
        try {
            db.save();
        }catch (IOException exception){
            return STATUS.DB_COULD_NOT_BE_SAVED;
        }

        return STATUS.OK;
    }
}
