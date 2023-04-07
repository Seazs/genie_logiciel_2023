package ulb.infof307.g12.model;


import lombok.Getter;
import lombok.Setter;

public class
Carte {
    public int id;


    /**
     * Connaissance est un int de 0 à 5, avec 1 qui est très mauvais et 5 très bon.
     * Si la connaissance est à 0, c’est que la carte n’a pas encore été vue/étudiée.
     */
    @Getter
    public int connaissance = 0;

    @Getter
    public String recto, verso;

    /**
     * Crée une carte dont ni le verso ni le recto ne peuvent être vides.
     * @param id
     * @param recto
     * @param verso
     */
    public Carte(int id, String recto, String verso){
        if(recto == null || recto.equals(""))
            throw new IllegalArgumentException("La carte doit posséder un recto");

        if(verso == null || verso.equals(""))
            throw new IllegalArgumentException("La carte doit posséder un verso");

        this.id = id;
        this.recto = recto;
        this.verso = verso;
    }

    /**
     * Fonction qui édite la variable "recto" de la classe carte
     * @param new_recto
     */
    public void editRecto(String new_recto){
        if (new_recto == null || new_recto.equals(""))
            throw new IllegalArgumentException("La carte doit posséder un recto");
        recto = new_recto;
    }

    /**
     * Fonction qui édite la variable "verso" de la classe carte
     * @param new_verso
     */
    public void editVerso(String new_verso){
        if (new_verso == null || new_verso.equals(""))
            throw new IllegalArgumentException("La carte doit posséder un verso");
        verso = new_verso;
    }

    /**
     * Fonctio pour set la connaissance. prends en entrée un int entre 0 et 5.
     * @param connaissance
     */
    public void setConnaissance(int connaissance){
        if (connaissance > 5 || connaissance < 0)
            throw new IllegalArgumentException("L’argument connaissance dois être un int entre 0 et 5");

        this.connaissance = connaissance;
    }
}
