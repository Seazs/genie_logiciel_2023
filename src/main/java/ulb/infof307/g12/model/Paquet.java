package ulb.infof307.g12.model;

import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;

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
     * @param categorie
     */
    public Paquet(String nom, String... categorie){
        if(nom == null || nom.equals("") || categorie == null)
            throw new IllegalArgumentException("Le paquet doit posséder un nom ou une catégorie");

        this.nom = nom;
        this.categories.addAll(Arrays.asList(categorie));
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
            if(car.getId()==carte.getId()) {
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
        cartes.remove(carte);
        this.length--;
    }

    /**
     * @param categorie
     */
    public void ajouterCategorie(String categorie){
        if(!this.categories.contains(categorie)){
            this.categories.add(categorie);
        }
    }

    /**
     * Fonction qui supprime la catégorie de la liste des catégories (si elle existe)
     * @param categorie
     */
    public void supprimerCategorie(String categorie){
        if(this.categories.contains(categorie)){
            this.categories.remove(categorie);
        }

    }

}
