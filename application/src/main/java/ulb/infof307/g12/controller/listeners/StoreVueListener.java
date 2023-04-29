package ulb.infof307.g12.controller.listeners;

import ulb.infof307.g12.model.Paquet;

import java.util.List;

public interface StoreVueListener {

    void uploadPaquet(Paquet paquet);

    void downloadPaquet(Paquet paquet);

    List<Paquet> getStorePaquets();
}
