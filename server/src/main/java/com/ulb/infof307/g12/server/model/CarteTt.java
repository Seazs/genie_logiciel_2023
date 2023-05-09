package com.ulb.infof307.g12.server.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

public class CarteTt extends Carte{
    @Getter
    @JsonProperty("begin")
    private String begin;

    @Getter
    @JsonProperty("end")
    private String end;

    @Getter
    @JsonProperty("answer")
    private String answer;

    /**
     * Constructeur pour la désérialisation avec jackson
     * @param id id de la carte
     * @param recto recto de la carte
     * @param verso verso de la carte
     * @param begin début de la phrase
     * @param end fin de la phrase
     * @param answer réponse
     */
    @JsonCreator
    public CarteTt(@JsonProperty("id") int id,@JsonProperty("recto") String recto,@JsonProperty("verso") String verso,@JsonProperty("begin") String begin,@JsonProperty("end") String end,@JsonProperty("answer") String answer) {
        super(id, recto, verso,"TT");
        editRecto(recto);
        this.verso = verso;
        this.begin = begin;
        this.end = end;
        this.answer = answer;
    }
}
