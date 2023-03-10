package ulb.infof307.g12.model;

//Création de la classe utilisateur

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Utilisateur {

    @Getter
    private final String pseudo;

    @Getter
    private String mdp;
    @Getter
    private final List<Paquet> listPaquet = new ArrayList<Paquet>();

    /**
     * Constructeur de la classe utilisateur
     *
     * @param pseudo
     * @param mdp
     */
    public Utilisateur(String pseudo, String mdp) throws IllegalArgumentException {
        if (estValide(pseudo) && estValide(mdp))
        {
            this.pseudo = pseudo;
            this.mdp = mdp;
        }
        else {
            throw new IllegalArgumentException("Le pseudo ou le mot de passe contient des caractères interdits.");
        }

    }

    public void setMdp(String mdp) {
        if (estValide(mdp))
            this.mdp = mdp;
        else
            throw new IllegalArgumentException("Le mot de passe contient des caractères interdits.");

    }


    public void addPaquet(Paquet paquet) {
        listPaquet.add(paquet);
    }

    public void removePaquet(String nom) {
        listPaquet.removeIf(paquet -> Objects.equals(paquet.getNom(), nom));
    }


    private boolean estValide(String string) {
        return (!string.contains("#") &&
                !string.equals("") &&
                !string.contains(" "));
    }

}