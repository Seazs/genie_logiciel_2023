package ulb.infof307.g12.view.listeners;


import java.util.Optional;

public interface ProfileViewListener {
    /**
     * Changement du mot de passe
     * @param password nouveau mot de passe
     * @return le statut
     */
    String changePassword(Optional<String> password);

    /**
     * Deconnexion de l'utilisateur
     */
    void disconnect();


}
