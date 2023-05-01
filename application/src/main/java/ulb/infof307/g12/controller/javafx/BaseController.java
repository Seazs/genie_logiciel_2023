package ulb.infof307.g12.controller.javafx;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;


public abstract class BaseController {

    protected Stage stage;
    protected Object controller;

    /**
     * Controller de base
     * @param stage stage
     * @param resource ressource
     * @param title titre
     * @throws IOException exception
     */
    public BaseController(Stage stage, URL resource, String title) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(resource);

        Scene scene = new Scene(fxmlLoader.load());

        stage.setTitle(title);
        stage.setScene(scene);

        controller = fxmlLoader.getController();

        this.stage = stage;
    }

    /**
     * Show
     */
    public void show(){
        stage.show();
    }

    /**
     * Hide
     */
    public void hide(){
        stage.hide();
    }

}
