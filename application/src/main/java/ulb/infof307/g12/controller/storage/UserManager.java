package ulb.infof307.g12.controller.storage;

import ulb.infof307.g12.Main;
import ulb.infof307.g12.controller.javafx.connexion.MenuPrincipal;
import ulb.infof307.g12.model.STATUS;
import ulb.infof307.g12.model.User;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class UserManager {
    private static final List<User> LISTE_USER = new ArrayList<>();
    private final File userDatabase;
    public STATUS status;
    public static User userConnected;
    private String stock_folder = Main.getStockageFolderPath();

    /**
     * Constructeur de GestionnaireUtilisateur, en paramètre, c'est le fichier dont on doit charger les utilisateurs
     * @param fichier fichier contenant les utilisateurs
     * @throws FileNotFoundException
     */
    public UserManager(File fichier) throws FileNotFoundException {
        userDatabase = fichier;
        load();
        status = STATUS.OK;
    }

    /**
     * Autre constructeur du gestionnaire utilisateur
     */
    public UserManager() {
        userDatabase = new File(stock_folder,"stockUser.txt");
        try {
            if (!userDatabase.createNewFile()){
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
        FileWriter writer = new  FileWriter(userDatabase);
        BufferedWriter out = new BufferedWriter(writer);

        for(User user : LISTE_USER) {
            out.write(user.getPseudo() + "#" + user.getMdp());
            out.newLine();
        }
        out.close();
    }

    /**
     * Charge les utilisateurs à partir d'un fichier
     * @throws FileNotFoundException
     */
    public void load() throws FileNotFoundException {
        LISTE_USER.clear();
        if (!userDatabase.exists()){
            throw new FileNotFoundException("Le fichier n'existe pas");
        }
        Scanner myReader = new Scanner(userDatabase);
        while (myReader.hasNextLine()){
            String data = myReader.nextLine();

            if (!data.isBlank()) {
                String[] listdata = data.split("#");
                if (listdata.length >= 2) {
                    LISTE_USER.add(new User(listdata[0].strip(),listdata[1].strip()));
                } else {
                    System.out.println("Erreur : la ligne ne contient pas les informations attendues.");
                }
            }
        }
        myReader.close();
    }

    /**
     * Renvoie la liste des utilisateurs
     * @return le status de la dernière opération
     */
    public List<User> getListUser() {
        return LISTE_USER;
    }

    /**
     * Vérifie si l'utilisateur à déjà un compte et le connecte
     * @param username
     * @param password
     * @return
     * @throws FileNotFoundException
     */
    public boolean connect(String username, String password) throws FileNotFoundException {
        if (!UserIsValid(username, password))
            return false;
        this.load();
        for (User u : LISTE_USER) {
            if (u.getPseudo().equals(username)) {
                if (u.getMdp().equals(password)) {
                    status = STATUS.OK;
                    System.out.println("AUTHORIZED ACCESS");
                    userConnected = u ;
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

    /**
     * vérifie que l'utilisateur est déconnecté
     * @return True si l'utilisateur est déconnecté
     */
    public boolean disconnect(){
        if (userConnected !=null){
            userConnected = null;
            return true;
        }
        return false;

    }

    /**
     * Retourne le statut
     * @return statut
     */
    public STATUS getStatus() {
        return status;
    }

    /**
     * Permet de créer un nouveau compte d'utilisateur
     * @param username pseudo de l'utilisateur
     * @param password mot de passe de l'utilisateur
     * @return
     * @throws IOException
     */
    public boolean register(String username, String password) throws IOException {
        if (!UserIsValid(username, password))
            return false;
        User new_user = new User(username, password);
        for (User u : LISTE_USER) {
            if (u.getPseudo().equals(username)) {
                status = STATUS.USERNAME_DOES_ALREADY_EXIST;
                System.out.println("USERNAME DOES ALREADY EXIST");
                return false;
            }
        }
        status = STATUS.OK;
        System.out.println("NEW USER REGISTERED");
        LISTE_USER.add(new_user);
        File f = new File(stock_folder,username);
        f.mkdirs();
        this.save();
        return true;
    }

    /**
     * Vérifie si le pseudo et le mot de passe sont valides
     * @param pseudo pseudo de l'utilisateur
     * @param mdp mot de passe de l'utilisateur
     * @return
     */
    private boolean UserIsValid(String pseudo, String mdp) {

        if (!isStringValid(pseudo)) {
            status = STATUS.USERNAME_IS_NOT_VALID;
            System.out.println("USERNAME_IS_NOT_VALID");
            return false;
        } else if (!isStringValid(mdp)) {
            status = STATUS.PASSWORD_IS_NOT_VALID;
            System.out.println("PASSWORD_IS_NOT_VALID");
            return false;
        }
        return true;
    }

    /**
     * Vérifie que le string est valide ou non
     * @param string string à vérifier
     * @return True si valide
     */
    private boolean isStringValid(String string){
        return (!string.contains("#") &&
                !string.equals("") &&
                !string.contains(" "));
    }

    /**
     * Trouver le nom d'utilisateur dans la liste des utilisateurs
     * @param nomUtilisateur nom de l'utilisateur
     * @return l'utilisateur ou null s'il n'est pas trouvé
     */
    public User findUser(String nomUtilisateur) {
        for (User user : LISTE_USER) {
            if (user.getPseudo().equals(nomUtilisateur)) {
                return user;
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
    public void changePassword(String username, String newPassword, String oldPassword) throws IOException {
        if(!isStringValid(newPassword)) {
            status = STATUS.PASSWORD_IS_NOT_VALID;
            return;
        }

        User user = findUser(username);

        if (user == null) {
            status = STATUS.USERNAME_DOES_NOT_EXIST;
            return;
        }

        if (!user.getMdp().equals(oldPassword)) {
            status = STATUS.WRONG_PASSWORD;
            return;
        }

        user.setMdp(newPassword);
        save();
        status = STATUS.OK;
    }

    /**
     * Supprime le dossier utilisateur
     * @param user
     */
    public void removeUser(User user) {
        File f = new File(stock_folder + user.getPseudo());
        try {
            if (f.exists()) {
                int index=0;
                for (User util : LISTE_USER){
                    if (Objects.equals(util.getPseudo(), user.getPseudo())){
                        LISTE_USER.remove(index);
                        break;
                    }
                    index++;
                }
                this.save();
                f.delete();
            }
        } catch (Exception e) {
            MenuPrincipal.getINSTANCE().showErrorPopup("Impossible de supprimer l'utilisateur !");
        }
    }

    /**
     * @return Le statut du message
     */
    public String getStatusMsg(){
        return this.status.getMsg();
    }

}
