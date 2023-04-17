package ulb.infof307.g12.controller.listeners;

import ulb.infof307.g12.model.Utilisateur;

import java.util.Optional;

public interface ProfilVueListener {
    /**
     * Changement du mot de passe
     * @param password
     * @return le statut
     */
    String changePassword(Optional<String> password);

    /**
     * Deconnexion de l'utilisateur
     */
    void deconnexion();


}
