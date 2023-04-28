package ulb.infof307.g12.model;


import lombok.Getter;
import lombok.Setter;

import java.util.List;


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
     */
    public Carte(int id, String recto, String verso){

        if (recto == null || recto.equals(""))
            throw new IllegalArgumentException("La carte doit posséder un recto");

        if (verso == null || verso.equals(""))
            throw new IllegalArgumentException("La carte doit posséder un verso");
        this.recto = recto;
        this.verso = verso;
        this.id = id;
        this.type="Simple";
    }

    /**
     * Fonction qui édite la variable "recto" de la classe carte
     * @param new_recto nouveau recto
     */
    public void editRecto(String new_recto){
        if (new_recto == null || new_recto.equals(""))
            throw new IllegalArgumentException("La carte doit posséder un recto");
        recto = new_recto;
    }


    /**
     * Fonction qui édite la variable "verso" de la classe carte
     * @param new_verso nouveau verso
     */
    public void editVerso(String new_verso){
        if (new_verso == null || new_verso.equals(""))
            throw new IllegalArgumentException("La carte doit posséder un verso");
        verso = new_verso;
    }

    /**
     * Sauvegarde dans recto et verso le texte à trou et sa réponse pour une carte TT
     * @param begin begin of the sentence
     * @param end end of the sentence
     * @param gap solution
     */
    public void setTTInfo(String begin, String end, String gap){
        if (this.getType().equals("tt")){
            this.recto = begin + "§" + end;
            this.verso = gap;
        }
        else{
            throw new IllegalArgumentException("La carte doit être de type TT");
        }
    }

    /**
     * Fonction pour set la connaissance. Prends en entrée un int entre 0 et 5.
     * @param connaissance
     */
    public void setConnaissance(int connaissance){
        if (connaissance > 5 || connaissance < 0)
            throw new IllegalArgumentException("L’argument connaissance dois être un int entre 0 et 5");

        this.connaissance = connaissance;
    }
}
