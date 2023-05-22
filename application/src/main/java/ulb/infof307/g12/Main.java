package ulb.infof307.g12;

import lombok.Getter;
import ulb.infof307.g12.controller.javafx.connexion.MenuPrincipal;

import java.io.File;

public class Main {

    @Getter
    private static String storageFolderPath = "/application/src/main/resources/stockage/";
    @Getter
    private static String rootPath;
    public static void main(String[] args) {
        // Ce code permet de prendre le chemin du dossier root de l'application
        // Prendre la localisation de la classe Main → qui sera dans root/target/classe
        // Après, il faut revenir deux fichiers en arrière.
        String mainClassFolderTarget = Main.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        File tmp = new File(mainClassFolderTarget).getParentFile().getParentFile();
        rootPath = tmp.getPath();
        MenuPrincipal.main(args);
    }

    public static String getStockageFolderPath(){
        return rootPath + storageFolderPath;
    }

}
