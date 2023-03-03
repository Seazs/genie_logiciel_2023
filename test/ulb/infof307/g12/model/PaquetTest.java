package ulb.infof307.g12.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class PaquetTest {

    /*
        - dossier de stockage
            - <utilisateur>
                - fichier login
                - fichier <decks>


                branch -> travail -> commits
                travail fini: merge {main sur ta branche} -> résoud les conflits -> merge{ ta branche sur main}
         */

    @Test
    public void testCreationPaquet(){
        //nom, catégorie, cartes, utilisateur

        String nom = "Maths",
                categorie = "BA-1";

        Assertions.assertThrows(IllegalArgumentException.class,() -> {
            Paquet paquet = new Paquet("",categorie);
        });

        Assertions.assertDoesNotThrow(() -> {
            Paquet paquet = new Paquet(nom,null);
        });
    }

    @Test
    public void testCreationPacketDouble(){

        String nom = "Maths";

        Paquet paquet1 = new Paquet(nom,"BA-1"),
                paquet2 = new Paquet(nom,"BA-1"),
                paquet3 = new Paquet(nom,"BA-2");

        Assertions.assertEquals(nom+" (1)",paquet2.getName());
        Assertions.assertEquals(nom+" (2)",paquet3.getName());
    }


}