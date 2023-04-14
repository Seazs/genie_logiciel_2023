package ulb.infof307.g12.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Arrays;

public class Paquet {

    @Setter
    @Getter
    private String nom;

    @Getter
    private ArrayList<String> categories = new ArrayList<String>();
    @Getter
    public ArrayList<Carte> cartes = new ArrayList<Carte>();
    private int length;

    /**
     * Crée un paquet dont le nom doit être unique.
     * @param nom
     * @param categorie
     * @throws IllegalArgumentException
     */
    public Paquet(String nom, String... categorie) throws IllegalArgumentException{
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
     * @throws IllegalArgumentException
     */
    public void ajouterCarte(Carte carte) throws IllegalArgumentException{
        for(Carte car: this.cartes){
            if(car.getId()==carte.getId()) {
                throw new IllegalArgumentException("La carte existe déjà");
            }
        }
        cartes.add(carte);
        this.length++;
    }

    /**
     * Supprime une carte du paquet
     * @param carte
     * @throws IllegalArgumentException
     */
    public void supprimerCarte(Carte carte) throws IllegalArgumentException{
        if(!(cartes.contains(carte))) {
            throw new IllegalArgumentException("La carte n'existe pas");
        }
        cartes.remove(carte);
        this.length--;
    }

    /**
     * Ajoute une catégorie au paquet
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
