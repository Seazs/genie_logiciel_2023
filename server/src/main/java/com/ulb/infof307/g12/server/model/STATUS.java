package com.ulb.infof307.g12.server.model;

import lombok.Getter;

public enum STATUS {
    OK("OK"),
    USERNAME_DOES_NOT_EXIST("L'utilisateur n'existe pas !"),
    USERNAME_DOES_ALREADY_EXIST("Le pseudo existe déjà!"),
    USERNAME_IS_NOT_VALID("Le pseudo contient des caractères interdits."),
    USER_COULD_NOT_BE_DELETED("L'utilisateur n'a pas pu être supprimé !"),
    PASSWORD_IS_NOT_VALID("Le mot de passe contient des caractères interdits."),
    WRONG_PASSWORD("Le mot de passe est incorrect !"),
    FILE_NOT_LOADED("Le fichier n'existe pas !"),
    DB_COULD_NOT_BE_SAVED("La base de données n'a pas pu être sauvegardée !"),
    DB_COULD_NOT_BE_LOADED("La base de données n'a pas pu être chargée !"),
    DUPLICATE("Le paquet est déjà sur le store !"),
    COULD_NOT_SYNC("Le paquet n'a pas pu être synchronisé !");

    @Getter
    private String msg;

    STATUS(String msg){
        this.msg = msg;
    }

}
