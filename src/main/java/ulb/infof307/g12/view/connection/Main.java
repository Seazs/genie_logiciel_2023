package ulb.infof307.g12.view.connection;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ulb.infof307.g12.view.paquets.MenuPaquetController;

import java.io.File;
import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("connexion-menu-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Application Title");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        File stockage = new File("./stockage");
        if (!stockage.exists()){
            stockage.mkdir();
        }
        launch();
    }

}
