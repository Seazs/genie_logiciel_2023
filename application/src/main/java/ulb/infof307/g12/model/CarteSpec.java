package ulb.infof307.g12.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

public class CarteSpec extends Carte{

    @Getter@Setter
    @JsonProperty("language")
    private String language;

    /**
     * Crée une carte dont ni le verso ni le recto ne peuvent être vides.
     * La carte se stocke dans le fichier de la façon suivante:
     * type#recto#verso
     * Selon le type le recto et le verso seront lus différemment
     *
     * @param id    id
     * @param recto recto
     * @param verso verso
     * @param lang  lang
     * @throws IllegalArgumentException si le recto ou le verso est vide ou comporte le caractère #
     */
    public CarteSpec(int id, String recto, String verso, String lang) {
        super(id, recto, verso);
        if (lang == null) throw new IllegalArgumentException("La langue ne peut pas être nulle");
        if (!checkLanguage(lang)) throw new IllegalArgumentException("La langue n'est pas valide");
        this.language = lang;
        this.type="Spec";
    }
    @JsonCreator
    public CarteSpec(@JsonProperty("id") int id,@JsonProperty("recto") String recto,@JsonProperty("verso") String verso,@JsonProperty("language") String lang,@JsonProperty("connaissance") int connaissance) {
        super(id, recto, verso);
        if (lang == null) throw new IllegalArgumentException("La langue ne peut pas être nulle");
        if (!checkLanguage(lang)) throw new IllegalArgumentException("La langue n'est pas valide");
        this.language = lang;
        this.type="Spec";
    }


    /**
     * @param language le format de la carte (HTML/LATEX)
     * @return true si le format est valide
     */
    public boolean checkLanguage(String language){
         return language.equals("html") || language.equals("latex");
    }

    /**
     * @see Carte#getCarteInfo()
     */
    @Override
    @JsonIgnore
    public String[] getCarteInfo(){
        String[] info = new String[4];
        info[0] = this.type;
        info[1] = this.recto;
        info[1] = info[1].replaceAll("\n", "\\n");
        info[2] = this.verso;
        info[2] = info[2].replaceAll("\n", "\\n");
        info[3] = this.language;
        return info;
    }
}
