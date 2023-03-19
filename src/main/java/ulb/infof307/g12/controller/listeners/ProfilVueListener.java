package ulb.infof307.g12.controller.listeners;

import java.util.Optional;

public interface ProfilVueListener {
    void parentReturn();

    String changePassword(Optional<String> password);
}
