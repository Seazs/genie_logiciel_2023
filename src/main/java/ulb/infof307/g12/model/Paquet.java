package ulb.infof307.g12.model;

import lombok.Getter;

import java.util.ArrayList;

public class Paquet {

    @Getter
    private String nom;

    @Getter
    private ArrayList<String> categories = new ArrayList<String>();

    public ArrayList<Carte> cartes = new ArrayList<Carte>();
    private int length;

    /**
     * Crée un paquet dont le nom doit être unique.
     * @param nom
     * @param categories
     */
    public Paquet(String nom, ArrayList<String> categories){
        if(nom == null || nom == "")
            throw new IllegalArgumentException("Le paquet doit posséder un nom");

        this.nom = nom;
        this.categories = categories;
        this.length=0;
    }

    /**
     * @param objects
     * @return True si l'objet est le meme paquet
     */
    @Override
    public boolean equals(Object objects) {
        if(!(objects instanceof Paquet)) {
            return false;
        }
        Paquet obj = (Paquet) objects;
        return (this.getNom().equals(obj.getNom()) && this.getCategories().equals(obj.categories));
    }

    /**
     * Fonction qui ajoute une carte au paquet
     * @param carte
     */
    public void ajouterCarte(Carte carte){
        for(Carte car: this.cartes){
            if(car.id==carte.id) {
                throw new IllegalArgumentException("La carte existe déjà");
            }
        }
        cartes.add(carte);
        this.length++;
    }
    public void supprimerCarte(Carte carte){
        if(!(cartes.contains(carte))) {
            throw new IllegalArgumentException("La carte n'existait pas");
        }
        cartes.remove(cartes.indexOf(carte));
        this.length--;
    }

    /**
     * @param categorie
     */
    public static void addCategorie(String categorie){
        if(this.categories.contains(categorie)){
            return;
        }
        this.categories.add(categorie);
    }

}
