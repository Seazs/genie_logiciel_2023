package ulb.infof307.g12.storage;

import ulb.infof307.g12.model.Utilisateur;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GestionnaireUtilisateur {
    private List<Utilisateur> listeUtilisateur = new ArrayList<Utilisateur>();
    private File userdatabase;

    /**
     * Constructeur de GestionnaireUtilisateur, en paramètre c'est le fichier dont on doit charger les utilisateurs
     * @param fichier
     * @throws FileNotFoundException
     */
    public GestionnaireUtilisateur(File fichier) throws FileNotFoundException {
        userdatabase = fichier;
        load();
    }
    public GestionnaireUtilisateur() throws IOException {
        userdatabase = new File("stockUser.txt");
        if (!userdatabase.createNewFile()){load();}
    }

    /**
     * Sauvegarde la liste des utilisateurs dans un fichier .txt
     * @throws IOException
     */
    public void save() throws IOException {
        FileWriter out = new  FileWriter(userdatabase);
        for(Utilisateur u : listeUtilisateur) {
            out.write(u.getPseudo() + "#" + u.getMdp() + "\n");
        }
        out.close();
    }

    /**
     * Charge les utilisateurs à partir d'un fichier
     * @throws FileNotFoundException
     */
    public void load() throws FileNotFoundException {
        if (!userdatabase.exists()){
            throw new FileNotFoundException("Le fichier n'existe pas");
        }
        Scanner myReader = new Scanner(userdatabase);
        while (myReader.hasNextLine()){
            String data = myReader.nextLine();
            String[] listdata = data.split("#");
            listeUtilisateur.add(new Utilisateur(listdata[0],listdata[1]));
        }
        myReader.close();
    }
    public List<Utilisateur> getListeUtilisateur() {return listeUtilisateur;}
}
