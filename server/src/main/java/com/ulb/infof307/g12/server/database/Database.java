package com.ulb.infof307.g12.server.database;

import com.ulb.infof307.g12.server.model.STATUS;
import com.ulb.infof307.g12.server.model.User;
import lombok.Getter;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Database {
    @Getter
    private List<User> db_user = new ArrayList<>();
    private final File db_user_file;
    public STATUS status;
    private static Database instance;

    public Database() {
        this.db_user_file = new File("./stockage","stockUser.txt");
        try {
            if (!db_user_file.createNewFile()){
                load();
            }
            status = STATUS.OK;
        } catch (IOException e) {
            status = STATUS.FILE_NOT_LOADED;
        }
    }

    public static Database getInstance(){
        if (instance == null) {
            instance = new Database();
        }
        return instance;
    }

    /**
     * Charge les utilisateurs à partir d'un fichier
     * @throws FileNotFoundException
     */
    public void load() throws FileNotFoundException {
        db_user.clear();
        if (!db_user_file.exists()){
            throw new FileNotFoundException("Le fichier n'existe pas");
        }
        Scanner myReader = new Scanner(db_user_file);
        while (myReader.hasNextLine()){
            String data = myReader.nextLine();

            if (!data.isBlank()) {
                String[] listdata = data.split("#");
                if (listdata.length >= 2) {
                    db_user.add(new User(listdata[0].strip(),listdata[1].strip()));
                } else {
                    System.out.println("Erreur : la ligne ne contient pas les informations attendues.");
                }
            }
        }
        myReader.close();
    }

    /**
     * Sauvegarde la liste des utilisateurs dans un fichier .txt
     * @throws IOException
     */
    public void save() throws IOException {
        FileWriter writer = new  FileWriter(db_user_file);
        BufferedWriter out = new BufferedWriter(writer);

        for(User utilisateur : db_user) {
            out.write(utilisateur.getUsername() + "#" + utilisateur.getPassword());
            out.newLine();
        }
        out.close();
    }

}
