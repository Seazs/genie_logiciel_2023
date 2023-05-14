package ulb.infof307.g12.model;

import lombok.Getter;

public enum STATUS {
    OK("OK"),
    USERNAME_DOES_NOT_EXIST("L'utilisateur n'existe pas !"),
    USERNAME_DOES_ALREADY_EXIST("Le pseudo existe déjà!"),
    USERNAME_IS_NOT_VALID("Le pseudo contient des caractères interdits."),
    PASSWORD_IS_NOT_VALID("Le mot de passe contient des caractères interdits."),
    WRONG_PASSWORD("Le mot de passe est incorrect !"),
    FILE_NOT_LOADED("Le fichier n'existe pas !"),
    SERVER_CONNEXION_ERROR("Erreur lors de la connexion au serveur."),
    SERVER_CREATION_ERROR("Erreur lors de la création d'un utilisateur sur le serveur."),
    DUPLICATE("Le paquet est déjà sur le store !"),
    SERVER_CANNOT_CHANGE_PASSWORD("Erreur lors de la modification du mot de passe sur le serveur."),
    CANNOT_CHANGE_PASSWORD_OFFLINE("Il n'est pas possible de changer son mot de passe localement. Veuillez vous connecter au serveur.")
    ;

    @Getter
    private String msg;

    STATUS(String msg){
        this.msg = msg;
    }

}
