package ulb.infof307.g12.model;

import lombok.Getter;

import java.util.ArrayList;

public class Paquet {

    @Getter
    private String nom, categorie;
    public ArrayList<Carte> cartes = new ArrayList<Carte>();
    private int length;

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

    /**
     * Fonction qui ajoute une carte au paquet
     * @param carte
     */
    public void ajouterCarte(Carte carte){
        cartes.add(carte);
    }


}
