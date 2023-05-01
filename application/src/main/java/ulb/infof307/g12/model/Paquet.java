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
    private final ArrayList<String> categories = new ArrayList<String>();
    @Getter
    public ArrayList<Carte> cartes = new ArrayList<Carte>();

    /**
     * Crée un paquet dont le nom doit être unique.
     * @param nom nom du paquet
     * @param categorie catégorie du paquet
     * @throws IllegalArgumentException si le paquet n'a pas de catérorie ou de nom
     */
    public Paquet(String nom, String... categorie) throws IllegalArgumentException{
        if(nom == null || nom.equals("") || categorie == null)
            throw new IllegalArgumentException("Le paquet doit posséder un nom ou une catégorie");

        this.nom = nom;
        this.categories.addAll(Arrays.asList(categorie));
    }

    /**
     * @param objects objets
     * @return True si l'objet est le meme paquet
     */
    @Override
    public boolean equals(Object objects) {
        if(!(objects instanceof Paquet obj)) {
            return false;
        }
        return (this.getNom().equals(obj.getNom()) && this.getCategories().equals(obj.categories));
    }

    /**
     * Fonction qui ajoute une carte au paquet
     * @param carte carte
     * @throws IllegalArgumentException si la carte existe déjà
     */
    public void addCard(Carte carte) throws IllegalArgumentException{
        for(Carte car: this.cartes){
            if(car.getId()==carte.getId()) {
                throw new IllegalArgumentException("La carte existe déjà");
            }
        }
        cartes.add(carte);
    }

    /**
     * Supprime une carte du paquet --> pas encore implémenté dans le code (test à rajouter)
     * @param carte carte
     * @throws IllegalArgumentException si la carte n'existe pas
     */
    public void supprimerCarte(Carte carte) throws IllegalArgumentException{
        if(!(cartes.contains(carte))) {
            throw new IllegalArgumentException("La carte n'existe pas");
        }
        cartes.remove(carte);
    }

    /**
     * Ajoute une catégorie au paquet
     * @param categorie catégorie
     */
    public void addCategory(String categorie){
        if (categorie.contains("#")){
            throw new IllegalArgumentException("Le nom de la catégorie ne peut pas contenir le caractère #");
        }
        if(!this.categories.contains(categorie)){
            this.categories.add(categorie);
        }
    }

}
