package ulb.infof307.g12.controller.listeners;

public interface UserCredentialsListener {
    String onRegister(String username, String password);

    String onLogin(String username, String password);
}
