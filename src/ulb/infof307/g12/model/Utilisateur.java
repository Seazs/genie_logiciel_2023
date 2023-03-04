//Création de la classe utilisateur
package ulb.infof307.g12.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Utilisateur {
    private String pseudo;
    private String mdp;
    private List<Paquet> listPaquet= new ArrayList<Paquet>();

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
    public void addPaquet(Paquet npaquet) {
        listPaquet.add(npaquet);
    }
    public void rmPaquet(String nom){
        listPaquet.removeIf(paquet -> Objects.equals(paquet.getName(), nom));
    }

}