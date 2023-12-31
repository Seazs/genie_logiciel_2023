package ulb.infof307.g12.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import ulb.infof307.g12.view.dto.PaquetDTO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@JsonIgnoreProperties(
            ignoreUnknown = true
    )
public class Paquet {

    @Setter
    @Getter
    @JsonProperty("nom")
    private String nom;

    @Getter
    @JsonProperty("id")
    private UUID id;

    @Getter
    @Setter //On laisse setter mm si c'est final pour jackson (sauvegarde json)
    @JsonProperty("categories")
    private List<String> categories = new ArrayList<String>();

    @Getter
    @Setter
    @JsonProperty("cartes")
    public ArrayList<Card> cards = new ArrayList<Card>();

    /**
     * Crée un paquet dont le nom doit être unique.
     * @param nom nom du paquet
     * @param categorie catégorie du paquet
     * @throws IllegalArgumentException si le paquet n'a pas de catérorie ou de nom
     */
    public Paquet(String nom, String... categorie) throws IllegalArgumentException{
        if(nom == null || nom.equals("") || categorie == null)
            throw new IllegalArgumentException("Le paquet doit posséder un nom ou une catégorie");
        this.id = UUID.randomUUID();
        this.nom = nom;
        this.categories.addAll(Arrays.asList(categorie));
    }

    /**
     * Constructeur d'un paquet avec jackson
     * @param nom nom du paquet
     * @param categories catégories du paquet
     * @param cards cartes du paquet
     * @throws IllegalArgumentException si le paquet n'a pas de catérorie ou de nom
     */
    @JsonCreator
    public Paquet(@JsonProperty("id") UUID id, @JsonProperty("nom") String nom, @JsonProperty("categories") List<String> categories, @JsonProperty("cartes") List<Card> cards) throws IllegalArgumentException{
        if(nom == null || nom.equals("") || categories == null)
            throw new IllegalArgumentException("Le paquet doit posséder un nom ou une catégorie");
        this.id = id;
        this.nom = nom;
        this.categories.addAll(categories);
        this.cards.addAll(cards);
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
     * @param card carte
     * @throws IllegalArgumentException si la carte existe déjà
     */
    public void addCard(Card card) throws IllegalArgumentException{
        for(Card car: this.cards){
            if(car.getId()== card.getId()) {
                throw new IllegalArgumentException("La carte existe déjà");
            }
        }
        cards.add(card);
    }

    /**
     * Supprime une carte du paquet --> pas encore implémenté dans le code (test à rajouter)
     * @param card carte
     * @throws IllegalArgumentException si la carte n'existe pas
     */
    public void deleteCard(Card card) throws IllegalArgumentException{
        if(!(cards.contains(card))) {
            throw new IllegalArgumentException("La carte n'existe pas");
        }
        cards.remove(card);
    }

    /**
     * Ajoute une catégorie au paquet
     * @param categorie catégories enchaînées avec des ,
     */
    public void addCategory(String categorie) {
        if (categorie == null || categorie.isEmpty()) {
            throw new IllegalArgumentException("Le champ catégorie ne peut pas être vide");
        }
        if (categorie.contains("#")) {
            throw new IllegalArgumentException("Le nom de la catégorie ne peut pas contenir le caractère #");
        }
        if (!this.categories.contains(categorie)) {

            this.categories = Arrays.stream(categorie.split(",")).toList();
        }
    }

    /**
     * Transforme le paquet en DTO
     * @return une version DTO du paquet
     */
    @JsonIgnore
    public PaquetDTO getDTO() {
        return new PaquetDTO(this.id.toString(),this.nom, this.categories);
    }

    /**
     * Transforme les catégories enchaînées avec des , en liste de catégories
     * @return les catégories enchaînées avec des ,
     */
    public String categoriesToString() {
        StringBuilder categories = new StringBuilder();
        if (this.categories.isEmpty()) return "";
        categories.append(this.categories.get(0));
        for (int i=1; i < this.categories.size(); i++) {
            categories.append(", ").append(this.categories.get(i));
        }
        return categories.toString();
    }
}
