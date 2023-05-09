package com.ulb.infof307.g12.server.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Getter;
import lombok.Setter;

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
        /**
         * Connaissance est un int de 0 à 5, avec 1 qui est très mauvais et 5 très bon.
         * Si la connaissance est à 0, c’est que la carte n’a pas encore été vue/étudiée.
         */
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
         * Constructeur pour la deserialisation
         * @param id id de la carte
         * @param recto recto
         * @param verso verso
         * @param type type de la carte (voir héritage)
         */
        @JsonCreator
        public Carte( @JsonProperty("id") int id, @JsonProperty("recto") String recto, @JsonProperty("verso") String verso, @JsonProperty("type") String type){
                this.id = id;
                this.recto = recto;
                this.verso = verso;
                this.type = type;
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
}
