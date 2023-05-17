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


    /**
     * Retour au menu principal
     */
    void returnMenuPaquet();

    /**
     * Affichage d'un message d'erreur
     * @param s message d'erreur
     */
    void showErrorPopup(String s);
}
