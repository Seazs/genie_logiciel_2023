package com.ulb.infof307.g12.server.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY
) //permet de faire la distinction entre les différents types de cartes lors de la désérialisation
@JsonSubTypes({
        @JsonSubTypes.Type(value = CarteQcm.class, name = "QCM"),
        @JsonSubTypes.Type(value = CarteSpec.class, name = "Spec"),
        @JsonSubTypes.Type(value = CarteTt.class, name = "TT"),
}) //Montre la structure des classes filles et comment les reconnaitre

public class Carte {
        @Getter
        @JsonProperty("connaissance")
        public int connaissance = 0;

        @Setter
        @Getter
        @JsonProperty("id")
        protected int id;

        @Getter
        @Setter
        @JsonProperty("recto")
        protected String recto;

        @Getter
        @Setter
        @JsonProperty("verso")
        protected String verso;

        @Setter
        @Getter
        @JsonProperty("type")
        protected String type;

        /**
         * Constructeur pour la désérialisation avec jackson
         * @param id id de la carte
         * @param recto recto
         * @param verso verso
         * @param type type de la carte (voir héritage)
         */
        @JsonCreator
        public Carte( @JsonProperty("id") int id, @JsonProperty("recto") String recto, @JsonProperty("verso") String verso, @JsonProperty("type") String type){
                validate(recto);
                validate(verso);
                validateType(type);
                this.id = id;
                this.recto = recto;
                this.verso = verso;
                this.type = type;
        }

        /**
         * Tester la validité du type de carte
         * @param type type de la carte
         */
        protected void validateType(String type) {
                if (!Objects.equals(type, "QCM") && !Objects.equals(type, "TT") && !Objects.equals(type, "Spec"))
                        throw new IllegalArgumentException("Le type de carte n'est pas valide");
        }

        /**
         * Fonction qui édite la variable "recto" de la classe carte
         * @param new_recto nouveau recto
         * @throws IllegalArgumentException si le recto est vide ou comporte le caractère #
         */
        public void editRecto(String new_recto) throws IllegalArgumentException{
                validate(new_recto);
                recto = new_recto;
        }

        /**
         * Fonction qui s'assure que le mot est valide
         * @param word mot à valider
         * renvoie une exception si le verso n'est pas valide
         */
        protected void validate(String word){
                if(word == null || word.equals("") || word.contains("#"))
                        throw new IllegalArgumentException("Le verso n'est pas valide");
        }
}
