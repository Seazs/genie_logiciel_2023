package ulb.infof307.g12.model;


import lombok.Getter;
import lombok.Setter;
import ulb.infof307.g12.view.dto.CardDTO;


public class
Carte {
    /**
     * Connaissance est un int de 0 à 5, avec 1 qui est très mauvais et 5 très bon.
     * Si la connaissance est à 0, c’est que la carte n’a pas encore été vue/étudiée.
     */
    @Getter
    public int connaissance = 0;

    @Getter
    protected int id;

    @Getter
    @Setter
    protected String recto, verso;
    @Getter
    protected String type;

    /**
     * Crée une carte dont ni le verso ni le recto ne peuvent être vides.
     * La carte se stocke dans le fichier de la façon suivante:
     * type#recto#verso
     * Selon le type le recto et le verso seront lus différemment
     * @param id id
     * @param recto recto
     * @param verso verso
     * @throws IllegalArgumentException si le recto ou le verso est vide ou comporte le caractère #
     */
    public Carte(int id, String recto, String verso){

        if (recto == null || recto.equals(""))
            throw new IllegalArgumentException("La carte doit posséder un recto");
        else if (recto.contains("#"))
            throw new IllegalArgumentException("Le recto ne peut pas avoir le caractère \"#\"");
        if (verso == null || verso.equals(""))
            throw new IllegalArgumentException("La carte doit posséder un verso");
        else if (verso.contains("#"))
            throw new IllegalArgumentException("Le verso ne peut pas avoir le caractère \"#\"");
        this.recto = recto;
        this.verso = verso;
        this.id = id;
        this.type="Simple";
    }

    /**
     * Fonction qui édite la variable "recto" de la classe carte
     * @param new_recto nouveau recto
     * @throws IllegalArgumentException si le recto est vide ou comporte le caractère #
     */
    public void editRecto(String new_recto){
        if (new_recto == null || new_recto.equals(""))
            throw new IllegalArgumentException("La carte doit posséder un recto");
        else if (new_recto.contains("#"))
            throw new IllegalArgumentException("Le recto ne peut pas avoir le caractère \"#\"");
        recto = new_recto;
    }


    /**
     * Fonction qui édite la variable "verso" de la classe carte
     * @param new_verso nouveau verso
     * @throws IllegalArgumentException si le verso est vide ou comporte le caractère #
     */
    public void editVerso(String new_verso){
        if (new_verso == null || new_verso.equals(""))
            throw new IllegalArgumentException("La carte doit posséder un verso");
        else if (new_verso.contains("#"))
            throw new IllegalArgumentException("Le recto ne peut pas avoir le caractère \"#\"");
        verso = new_verso;
    }

    /**
     * Fonction pour set la connaissance. Prends en entrée un int entre 0 et 5.
     * @param knowledge connaissance de la carte
     * @throws IllegalArgumentException si l'argument n'est pas entre 0 et 5
     */
    public void setConnaissance(int knowledge){
        if (knowledge > 5 || knowledge < 0)
            throw new IllegalArgumentException("L’argument connaissance dois être un int entre 0 et 5");
        this.connaissance = knowledge;
    }

    /**
     * Fonction qui retourne les infos de la carte
     * @return les infos de la cartes
     */
    public String[] getCarteInfo(){
        String[] info = new String[3];
        info[0] = this.type;
        info[1] = this.recto;
        info[2] = this.verso;
        return info;
    }

    public CardDTO getDTO() {
        return new CardDTO(this.recto, this.verso);
    }

}
