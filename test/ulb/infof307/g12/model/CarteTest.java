package ulb.infof307.g12.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CarteTest {

    @Test
    public void testRectoPasVide(){
        Carte carte = new Carte(1, "", "");
        assertNotNull(carte.recto);
    }
    @Test
    public void testRiendeVide(){
        String recto = "Bonjour", verso = "Au revoir";

        Assertions.assertThrows(IllegalArgumentException.class,() -> {
            Carte carte = new Carte(1, "", verso);
        });

        Assertions.assertDoesNotThrow(() -> {
            Carte carte = new Carte(1,  recto, verso);
        });
        Assertions.assertThrows(IllegalArgumentException.class,() -> {
            Carte carte = new Carte(2, recto, "");
        });

        Assertions.assertDoesNotThrow(() -> {
            Carte carte = new Carte(2,  recto, verso);
        });
    }

    @Test
    public void testEditebien() {
        String new_recto = "oloY";
        String new_verso = "tulaS";
        Carte carte = new Carte(1, "Yolo", "Salut");
        carte.editRecto(new_recto);
        carte.editVerso(new_verso);
        assertEquals(new_recto, carte.recto);
        assertEquals(new_verso, carte.verso);
        String verso = "oloY", recto = "tulaS";
        Assertions.assertThrows(IllegalArgumentException.class,() -> {
            Carte carte1 = new Carte(1, "", verso);
        });

        Assertions.assertDoesNotThrow(() -> {
            Carte carte1 = new Carte(1,  recto, verso);
        });
        Assertions.assertThrows(IllegalArgumentException.class,() -> {
            Carte carte1 = new Carte(2, recto, "");
        });
        Assertions.assertDoesNotThrow(() -> {
            Carte carte1 = new Carte(2,  recto, verso);
        });
    }
}