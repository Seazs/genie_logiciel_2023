package ulb.infof307.g12.model;

import lombok.Getter;

public enum STATUS {
    OK(""),
    USERNAME_DOES_NOT_EXIST("L'utilisateur n'existe pas !"),
    USERNAME_DOES_ALREADY_EXIST("Le pseudo existe déjà!"),
    USERNAME_IS_NOT_VALID("Le pseudo contient des caractères interdits."),
    PASSWORD_IS_NOT_VALID("Le mot de passe contient des caractères interdits."),
    WRONG_PASSWORD("Le mot de passe est incorrect !"),
    FILE_NOT_LOADED("Le fichier n'existe pas !")
    ;

    @Getter
    private String msg;

    STATUS(String msg){
        this.msg = msg;
    }

}
