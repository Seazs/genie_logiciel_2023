package ulb.infof307.g12.model;


import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collections;

public class
Carte {
    /**
     * Connaissance est un int de 0 à 5, avec 1 qui est très mauvais et 5 très bon.
     * Si la connaissance est à 0, c’est que la carte n’a pas encore été vue/étudiée.
     */
    @Getter
    public int connaissance = 0;

    @Getter
    private int id;

    @Getter@Setter
    private String recto, verso, type;

    /**
     * Crée une carte dont ni le verso ni le recto ne peuvent être vides.
     * La carte se stocke dans le fichier de la façon suivante:
     * type#recto#verso
     * Selon le type le recto et le verso seront lus différemment
     * @param id
     * @param recto
     * @param verso
     * @param type Peut-être "rv" "tt" "qcm" ou ""
     */
    public Carte(int id, String recto, String verso, String type){
        //type = "rv"/"qcm"/"tt"
        if (type.equals("qcm")|| type.equals("rv")|| type.equals("tt")|| type.equals("")){
            if (recto == null || recto.equals(""))
                throw new IllegalArgumentException("La carte doit posséder un recto");

            if (verso == null || verso.equals(""))
                throw new IllegalArgumentException("La carte doit posséder un verso");
            this.recto = recto;
            this.verso = verso;
            this.type = type;
            this.id = id;
        }
        else{
            throw new IllegalArgumentException("La carte doit avoir un type valide (rv, tt, qcm, \"\") ");
        }
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
     * Prend une carte de type QCM en paramètre et décode le recto et le verso
     * le recto de base est de forme Question§reponse1§reponse2§reponse3
     * le verso est de forme bonne reponse
     *
     * Prend également une carte de type TT en paramètre et décode le recto et le verso
     * le recto est de forme débutdephrase§findephrase
     * le verso est de forme trou
     *
     * @return ArrayList<String> avec Question, réponse 1, réponse 2, réponse 3, bonne réponse
     * ou ArrayList<String> avec debutdephrase, findephrase, trou
     */
    public ArrayList<String> getQCMOrTTInfo(){
        if (this.getType().equals("qcm")|| this.getType().equals("tt")){
            ArrayList<String> infos = new ArrayList<String>();
            String[] listinfos = this.getRecto().split("§");
            Collections.addAll(infos, listinfos);
            infos.add(this.getVerso());
            return infos;
        }
        else{
            throw new IllegalArgumentException("La carte doit être de type qcm ou tt");
        }
    }


    /**
     * Sauvegarde dans recto et verso les questions et réponses des QCM
     * @param question
     * @param rep1
     * @param rep2
     * @param rep3
     * @param good_rep
     */
    public void setQCMInfo(String question, String rep1, String rep2, String rep3, String good_rep){
        if (this.getType().equals("qcm")){
            this.recto = question + "§" + rep1 + "§" + rep2 + "§" + rep3;
            this.verso = good_rep;
        }
        else{
            throw new IllegalArgumentException("La carte doit être de type QCM");
        }
    }

    /**
     * Sauvegarde dans recto et verso le texte à trou et sa réponse pour une carte TT
     * @param begin
     * @param end
     * @param gap
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
