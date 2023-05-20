package com.ulb.infof307.g12.server.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

public class CardSpec extends Card {

    @Getter
    @Setter
    @JsonProperty("language")
    private String language;


    /** Constructeur pour la désérialisation avec jackson
     * @param id id de la carte Spec
     * @param recto recto
     * @param verso verso
     * @param lang language qui est soit le latex soit le html
     */
    @JsonCreator
    public CardSpec(@JsonProperty("id") int id, @JsonProperty("recto") String recto, @JsonProperty("verso") String verso, @JsonProperty("language") String lang, @JsonProperty("connaissance") int connaissance) {
        super(id, recto, verso,"Spec");
        if (lang == null) throw new IllegalArgumentException("La langue ne peut pas être nulle");
        if (!checkLanguage(lang)) throw new IllegalArgumentException("La langue n'est pas valide");
        this.language = lang;
        this.connaissance=connaissance;
    }

    /**Verifie le type de language de la carte
     * @param language le format de la carte (HTML/LATEX)
     * @return true si le format est valide
     */
    public boolean checkLanguage(String language){
        return language.equals("html") || language.equals("latex");
    }
}
