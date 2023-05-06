package ulb.infof307.g12;

import lombok.Getter;
import ulb.infof307.g12.controller.javafx.connexion.MenuPrincipal;

public class Main {

    @Getter
    private static String folderPath = "application/src/main/resources/stockage/";
    public static void main(String[] args) {
        MenuPrincipal.main(args);
    }

}
