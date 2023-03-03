package ulb.infof307.g12.model;

import java.util.List;

public class Paquet {

    private String nom, categorie;

    /**
     * Crée un paquet dont le nom doit être unique.
     * @param nom
     * @param categorie
     */
    public Paquet(String nom, String categorie){
        if(nom == null || nom == "")
            throw new IllegalArgumentException("Le paquet doit posséder un nom");

        this.nom = nom;
        this.categorie = categorie;
    }

    /**
     *
     * @return le nom du paquet
     */
    public String getName(){
        return nom;
    }

    public String getCategorie(){
        return categorie;
    }


}
