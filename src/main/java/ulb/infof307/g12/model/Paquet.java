package ulb.infof307.g12.model;

import lombok.Getter;

public class Paquet {

    @Getter
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

    @Override
    public boolean equals(Object objects) {
        if(!(objects instanceof Paquet)) {
            return false;
        }
        Paquet obj = (Paquet) objects;
        return (this.getNom().equals(obj.getNom()) && this.getCategorie().equals(obj.categorie));
    }
    public String getNom(Paquet paquet){
        return this.nom;
    }
    public String getCategorie(Paquet paquet){
        return this.categorie;
    }
}
