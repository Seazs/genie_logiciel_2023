package ulb.infof307.g12.controller.listeners;

public interface UserCredentialsListener {
    /**
     *  Est appelé lorsqu'un utilisateur essaye de s'enregistrer pour la première fois
     * @param username
     * @param password
     * @return
     */
    String onRegister(String username, String password);

    /**
     * Est appelé lorsqu'un utilisateur essaye de se login
     * @param username
     * @param password
     * @return
     */
    String onLogin(String username, String password, boolean isOnline);
}
