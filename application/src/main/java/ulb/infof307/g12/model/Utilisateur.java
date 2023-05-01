package ulb.infof307.g12.model;

//Création de la classe utilisateur

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Utilisateur {

    @Getter
    private final String pseudo;

    @Getter
    private String mdp;
    @Getter
    @Setter
    private List<Paquet> listPaquet = new ArrayList<Paquet>();

    /**
     * Constructeur de la classe utilisateur
     *
     * @param pseudo pseudo de l'utilisateur
     * @param mdp mot de passe
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

    /**
     * Change le mot de passe de l'utilisateur
     * @param mdp
     * @throws IllegalArgumentException
     */
    public void setMdp(String mdp) throws IllegalArgumentException{
        if (estValide(mdp))
            this.mdp = mdp;
        else
            throw new IllegalArgumentException("Le mot de passe contient des caractères interdits.");

    }


    /**
     * Ajoute le paquet à la liste des paquets
     * @param paquet
     */
    public void addPaquet(Paquet paquet) {
        listPaquet.add(paquet);
    }

    /**
     * Enlève le paquet de la liste de paquets
     * @param nom
     */
    public void removePaquet(String nom) {
        listPaquet.removeIf(paquet -> Objects.equals(paquet.getNom(), nom));
    }


    /** Vérifie que le string est valide
     * @param string
     * @return True s'il est valide
     */
    private boolean estValide(String string) {
        return (!string.contains("#") &&
                !string.equals("") &&
                !string.contains(" "));
    }

}