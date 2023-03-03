package ulb.infof307.g12.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class PaquetTest {



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

    @Test
    public void testMemePaquet(){

        Paquet paquet = new Paquet("Maths","BA-1");
        Paquet paquet2 = new Paquet("Maths","BA-1");
        Assertions.assertEquals(paquet2,paquet);
    }

}