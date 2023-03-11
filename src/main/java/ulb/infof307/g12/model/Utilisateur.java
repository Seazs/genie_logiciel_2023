package ulb.infof307.g12.model;

//Création de la classe utilisateur
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Utilisateur {

    @Getter
    private String pseudo;

    @Getter
    @Setter
    private String mdp;

    @Getter
    private List<Paquet> listPaquet= new ArrayList<Paquet>();

    /**
     * Constructeur de la classe utilisateur
     * @param pseudo
     * @param mdp
     */
    public Utilisateur(String pseudo, String mdp){
        this.pseudo = pseudo;
        this.mdp = mdp;
    }

    public void addPaquet(Paquet paquet) {
        listPaquet.add(paquet);
    }
    public void removePaquet(String nom){
        listPaquet.removeIf(paquet -> Objects.equals(paquet.getNom(), nom));
    }

    public List<Paquet> getPaquet(Utilisateur utilisateur){
        return utilisateur.listPaquet;
    }

}