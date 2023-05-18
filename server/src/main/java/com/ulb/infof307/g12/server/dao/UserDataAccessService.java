package com.ulb.infof307.g12.server.dao;

import com.ulb.infof307.g12.server.ServerApplication;
import com.ulb.infof307.g12.server.model.STATUS;
import com.ulb.infof307.g12.server.model.User;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.*;

@Repository("users")
public class UserDataAccessService implements UserDAO{
    private final File db_user_file;
    public STATUS status;
    private List<User> db_user;

    public UserDataAccessService() {
        db_user_file = new File(ServerApplication.getStockageFolderPath(),"stockUser.txt");
        try {
            db_user = this.load();
            status = STATUS.OK;
        }catch (IOException exception){
            status = STATUS.DB_COULD_NOT_BE_LOADED;
        }
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
     * @return le status de la création
     * @see UserDAO#createUser(User)
     */
    @Override
    public STATUS createUser(User user) {
        // Vérification de nom d'utilisateur unique
        if(db_user.stream().anyMatch(u -> u.getUsername().equals(user.getUsername()))){
            return STATUS.USERNAME_DOES_ALREADY_EXIST;
        }
        db_user.add(user);
        // Si nom d'utilisateur unique, ajouter à la db
        try {
            this.save();
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
        return db_user.stream()
                .filter(user -> user.getUsername().equals(username))
                .findFirst()
                .map(User::getPassword)
                .orElse(null);
    }

    /**
     * @see UserDAO#updateUser(User)
     */
    @Override
    public STATUS updateUser(User user) {
        // Trouver le bon utilisateur
        Optional<User> userToUpdate = db_user.stream()
                .filter(u -> u.getUsername().equals(user.getUsername()))
                .findFirst();

        if(userToUpdate.isEmpty()){
            return STATUS.USERNAME_DOES_NOT_EXIST;
        }

        // Modifier mot de passe
        userToUpdate.get().setPassword(user.getPassword());

        // Sauvegarder la db
        try {
            this.save();
        }catch (IOException exception){
            return STATUS.DB_COULD_NOT_BE_SAVED;
        }

        return STATUS.OK;
    }

    @Override
    public List<User> getAllUsers() {
        return db_user;
    }

    /**
     * Sauvegarde la liste des utilisateurs dans un fichier .txt
     * @throws IOException si le fichier n'existe pas
     */
    public void save() throws IOException {
        fileExists();

        FileWriter writer = new  FileWriter(db_user_file);
        BufferedWriter out = new BufferedWriter(writer);

        for(User utilisateur : db_user) {
            out.write(utilisateur.getUsername() + "#" + utilisateur.getPassword());
            out.newLine();
        }
        out.close();
    }

    /**
     * Charge les utilisateurs à partir d'un fichier
     * @throws FileNotFoundException si le fichier n'existe pas
     */
    public List<User> load() throws IOException {
        ArrayList<User> new_db_user = new ArrayList<>();
        fileExists();
        try {
            Scanner myReader = new Scanner(db_user_file);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();

                if (!data.isBlank()) {
                    String[] listdata = data.split("#");
                    if (listdata.length >= 2) {
                        new_db_user.add(new User(listdata[0].strip(), listdata[1].strip()));
                    } else {
                        System.out.println("Erreur : la ligne ne contient pas les informations attendues.");
                        throw new IOException("Erreur dans la lecture du fichier.");
                    }
                }
            }
            myReader.close();
        }catch (IOException exception){
            throw new IOException("Erreur dans la lecture du fichier.");
        }
        return new_db_user;
    }

    /**
     * Fonction qui vérifie si le fichier existe
     * @throws IOException si le fichier n'existe pas
     */
    private void fileExists() throws IOException {
        db_user_file.getParentFile().mkdirs();
        db_user_file.createNewFile();
    }

    /**
     * @see UserDAO#deleteUser(String)
     */
    public STATUS deleteUser(String username){
        try {
            db_user.removeIf(user -> user.getUsername().equals(username));
        }catch (Exception exception){
            return STATUS.USER_COULD_NOT_BE_DELETED;
        }
        try {
            this.save();
            return STATUS.OK;
        }catch (IOException exception){
            return STATUS.DB_COULD_NOT_BE_SAVED;
        }
    }

}
