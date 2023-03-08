package ulb.infof307.g12.model;

//Création de la classe utilisateur
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Utilisateur {
    private String pseudo;
    private String mdp;
    /**
     * Constructeur de la classe utilisateur
     * @param npseudo
     * @param nmdp
     */
    public Utilisateur(String npseudo, String nmdp){
        pseudo = npseudo;
        mdp = nmdp;
    }
    public void setMdp(String nmdp){
        mdp = nmdp;
    }

    public String getPseudo(){
        return pseudo;
    }
    public String getMdp(){
        return mdp;
    }


}