package ulb.infof307.g12.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ulb.infof307.g12.view.dto.CardDTO;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class CarteTest {


    @Test
    public void testCreateCard(){
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

        Assertions.assertThrows(IllegalArgumentException.class,() -> {
            Carte carte = new Carte(2, recto, null);
        });

        Assertions.assertThrows(IllegalArgumentException.class,() -> {
            Carte carte = new Carte(2, recto, null);
        });

        Assertions.assertDoesNotThrow(() -> {
            Carte carte = new Carte(2,  recto, verso);
        });

        Assertions.assertThrows(IllegalArgumentException.class,() -> {
            Carte carte = new Carte(2, recto, null);
        });

        Assertions.assertThrows(IllegalArgumentException.class,() -> {
            Carte carte = new Carte(2, "#", verso);
        });

        Assertions.assertThrows(IllegalArgumentException.class,() -> {
            Carte carte = new Carte(2, recto, "#");
        });

    }

    @Test
    public void testEditebien() {
        String new_recto = "oloY";
        String new_verso = "tulaS";
        Carte carte = new Carte(1, "Yolo", "Salut");
        carte.editRecto(new_recto);
        carte.editVerso(new_verso);
        assertEquals(new_recto, carte.getRecto());
        assertEquals(new_verso, carte.getVerso());

    }
    @Test
    public void testEditPasVide() {
        String verso = "Ça va ?", recto = "Oui bien";
        String recto_vide = "";
        String verso_vide= "";
        String recto_null = null;
        String verso_null= null;

        Carte carte1 = new Carte(1, recto, verso);

        Assertions.assertThrows(IllegalArgumentException.class,() -> {
            carte1.editRecto(recto_vide);
        });

        Assertions.assertThrows(IllegalArgumentException.class,() -> {
            carte1.editVerso(verso_vide);;
        });

        Assertions.assertThrows(IllegalArgumentException.class,() -> {
            carte1.editRecto(recto_null);
        });

        Assertions.assertThrows(IllegalArgumentException.class,() -> {
            carte1.editVerso(verso_null);;
        });

        Assertions.assertThrows(IllegalArgumentException.class,() -> {
            carte1.editRecto("#");;
        });

        Assertions.assertThrows(IllegalArgumentException.class,() -> {
            carte1.editVerso("#");;
        });

    }

    @Test
    public void testSetConnaissence(){
        Carte carte = new Carte(1, "recto", "verso");
        carte.setConnaissance(2);
        assertEquals(2, carte.getConnaissance());
        Assertions.assertThrows(IllegalArgumentException.class,() -> {
            carte.setConnaissance(6);
        });
        Assertions.assertThrows(IllegalArgumentException.class,() -> {
            carte.setConnaissance(-1);
        });
    }

    @Test
    public void testgetCarteInfo(){
        Carte carte = new Carte(1, "recto", "verso");
        String[] infos = carte.getCarteInfo();
        assertEquals("Carte", infos[0]);
        assertEquals("recto", infos[1]);
        assertEquals("verso", infos[2]);
    }

    @Test
    public void testGetCarteDTO(){
        Carte carte = new Carte(1, "recto", "verso");
        CardDTO carteDTO = new CardDTO("recto", "verso");
        assertEquals(carteDTO, carte.getDTO());
    }
}

