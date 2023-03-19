package ulb.infof307.g12.controller.storage;

import ulb.infof307.g12.model.STATUS;
import ulb.infof307.g12.model.Utilisateur;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class GestionnaireUtilisateur {
    private static final List<Utilisateur> listeUtilisateur = new ArrayList<>();
    private final File userdatabase;
    public STATUS status;
    public static Utilisateur utilisateurConnected ;

    /**
     * Constructeur de GestionnaireUtilisateur, en paramètre c'est le fichier dont on doit charger les utilisateurs
     * @param fichier
     * @throws FileNotFoundException
     */
    public GestionnaireUtilisateur(File fichier) throws FileNotFoundException {
        userdatabase = fichier;
        load();
        status = STATUS.OK;
    }

    public GestionnaireUtilisateur() {
        userdatabase = new File("./stockage","stockUser.txt");
        try {
            if (!userdatabase.createNewFile()){
                load();
            }
            status = STATUS.OK;
        } catch (IOException e) {
            status = STATUS.FILE_NOT_LOADED;
        }
    }

    /**
     * Sauvegarde la liste des utilisateurs dans un fichier .txt
     * @throws IOException
     */
    public void save() throws IOException {
        FileWriter writer = new  FileWriter(userdatabase);
        BufferedWriter out = new BufferedWriter(writer);

        for(Utilisateur utilisateur : listeUtilisateur) {
            out.write(utilisateur.getPseudo() + "#" + utilisateur.getMdp());
            out.newLine();
        }
        out.close();
    }

    /**
     * Charge les utilisateurs à partir d'un fichier
     * @throws FileNotFoundException
     */
    public void load() throws FileNotFoundException {
        listeUtilisateur.clear();
        if (!userdatabase.exists()){
            throw new FileNotFoundException("Le fichier n'existe pas");
        }
        Scanner myReader = new Scanner(userdatabase);
        while (myReader.hasNextLine()){
            String data = myReader.nextLine();

            if (!data.isBlank()) {
                String[] listdata = data.split("#");
                if (listdata.length >= 2) {
                    listeUtilisateur.add(new Utilisateur(listdata[0].strip(),listdata[1].strip()));
                } else {
                    System.out.println("Erreur : la ligne ne contient pas les informations attendues.");
                }
            }
        }
        myReader.close();
    }

    public List<Utilisateur> getListeUtilisateur() {
        return listeUtilisateur;
    }

    /**
     * Vérifie si l'utilisateur à déjà un compte
     * @param username
     * @param password
     * @return
     * @throws FileNotFoundException
     */
    public boolean connect(String username, String password) throws FileNotFoundException {
        if (!estUtilisateurValide(username, password))
            return false;
        this.load();
        for (Utilisateur u : listeUtilisateur) {
            if (u.getPseudo().equals(username)) {
                if (u.getMdp().equals(password)) {
                    status = STATUS.OK;
                    System.out.println("AUTHORIZED ACCESS");
                    utilisateurConnected = u ;
                    return true;
                }
                status = STATUS.WRONG_PASSWORD;
                System.out.println("WRONG PASSWORD");
                return false;
            }
        }
        System.out.println("USERNAME DOES NOT EXIST");
        status = STATUS.USERNAME_DOES_NOT_EXIST;
        return false;
    }

    public STATUS getStatus() {
        return status;
    }

    /**
     * Permet de créer un nouveau compte d'utilisateur
     * @param username
     * @param password
     * @return
     * @throws IOException
     */
    public boolean register(String username, String password) throws IOException {
        if (!estUtilisateurValide(username, password))
            return false;
        Utilisateur new_user = new Utilisateur(username, password);
        for (Utilisateur u : listeUtilisateur) {
            if (u.getPseudo().equals(username)) {
                status = STATUS.USERNAME_DOES_ALREADY_EXIST;
                System.out.println("USERNAME DOES ALREADY EXIST");
                return false;
            }
        }
        status = STATUS.OK;
        System.out.println("NEW USER REGISTERED");
        listeUtilisateur.add(new_user);
        File f = new File("./stockage",username);
        f.mkdir();
        this.save();
        return true;
    }

    /**
     * Vérifie si le pseudo et le mot de passe sont valides
     * @param pseudo
     * @param mdp
     * @return
     */
    private boolean estUtilisateurValide(String pseudo, String mdp) {

        if (!estStringValide(pseudo)) {
            status = STATUS.USERNAME_IS_NOT_VALID;
            System.out.println("USERNAME_IS_NOT_VALID");
            return false;
        } else if (!estStringValide(mdp)) {
            status = STATUS.PASSWORD_IS_NOT_VALID;
            System.out.println("PASSWORD_IS_NOT_VALID");
            return false;
        }
        return true;
    }

    private boolean estStringValide(String string){
        return (!string.contains("#") &&
                !string.equals("") &&
                !string.contains(" "));
    }

    /**
     *
     * @param nomUtilisateur
     * @return
     */
    public Utilisateur trouverUtilisateur(String nomUtilisateur) {
        for (Utilisateur utilisateur : listeUtilisateur) {
            if (utilisateur.getPseudo().equals(nomUtilisateur)) {
                return utilisateur;
            }
        }
        return null;
    }


    /**
     * Modifie le mot de passe associé au pseudo
     * @param username
     * @param newPassword
     * @param oldPassword
     * @return
     * @throws IOException
     */
    public void modifierMotDePasse(String username, String newPassword, String oldPassword) throws IOException {
        if(!estStringValide(newPassword)) {
            status = STATUS.PASSWORD_IS_NOT_VALID;
            return;
        }

        Utilisateur utilisateur = trouverUtilisateur(username);

        if (utilisateur == null) {
            status = STATUS.USERNAME_DOES_NOT_EXIST;
            return;
        }

        if (!utilisateur.getMdp().equals(oldPassword)) {
            status = STATUS.WRONG_PASSWORD;
            return;
        }

        utilisateur.setMdp(newPassword);
        save();
        status = STATUS.OK;
    }

    /**
     * Supprime le dossier utilisateur
     * @param user
     * @throws FileNotFoundException
     */
    public void removeUser(Utilisateur user) throws FileNotFoundException {
        File f = new File("./stockage/" + user.getPseudo());
        try {
            if (f.exists()) {
                int index=0;
                for (Utilisateur util : listeUtilisateur){
                    if (Objects.equals(util.getPseudo(), user.getPseudo())){
                        listeUtilisateur.remove(index);
                        break;
                    }
                    index++;
                }
                this.save();
                f.delete();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public String getStatusMsg(){
        return this.status.getMsg();
    }

}
