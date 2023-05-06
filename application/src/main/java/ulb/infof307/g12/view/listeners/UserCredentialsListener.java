package ulb.infof307.g12.view.listeners;

public interface UserCredentialsListener {
    /**
     *  Est appelé lorsqu'un utilisateur essaye de s'enregistrer pour la première fois
     * @param username nom d'utilisateur
     * @param password mot de passe
     * @return le statut
     */
    String onRegister(String username, String password, boolean isOnline);

    /**
     * Est appelé lorsqu'un utilisateur essaye de se login
     * @param username nom d'utilisateur
     * @param password mot de passe
     * @return le statut
     */
    String onLogin(String username, String password, boolean isOnline);
}
