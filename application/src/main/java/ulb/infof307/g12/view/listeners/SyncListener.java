package ulb.infof307.g12.view.listeners;

public interface SyncListener {
    /**
     * Upload local cards to remote
     */
    void uploadLocal();

    /**
     * Download remote cards to local
     */
    void downloadRemote();
}
