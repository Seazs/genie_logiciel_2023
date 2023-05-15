package ulb.infof307.g12.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ulb.infof307.g12.view.dto.CardDTO;

import static org.junit.jupiter.api.Assertions.*;

class CardTest {


    @Test
    public void testCreateCard(){
        String recto = "Bonjour", verso = "Au revoir";

        Assertions.assertThrows(IllegalArgumentException.class,() -> {
            Card card = new Card(1, "", verso);
        });

        Assertions.assertDoesNotThrow(() -> {
            Card card = new Card(1,  recto, verso);
        });
        Assertions.assertThrows(IllegalArgumentException.class,() -> {
            Card card = new Card(2, recto, "");
        });

        Assertions.assertThrows(IllegalArgumentException.class,() -> {
            Card card = new Card(2, recto, null);
        });

        Assertions.assertThrows(IllegalArgumentException.class,() -> {
            Card card = new Card(2, recto, null);
        });

        Assertions.assertDoesNotThrow(() -> {
            Card card = new Card(2,  recto, verso);
        });

        Assertions.assertThrows(IllegalArgumentException.class,() -> {
            Card card = new Card(2, recto, null);
        });

        Assertions.assertThrows(IllegalArgumentException.class,() -> {
            Card card = new Card(2, "#", verso);
        });

        Assertions.assertThrows(IllegalArgumentException.class,() -> {
            Card card = new Card(2, recto, "#");
        });

    }

    @Test
    public void testEditebien() {
        String new_recto = "oloY";
        String new_verso = "tulaS";
        Card card = new Card(1, "Yolo", "Salut");
        card.editRecto(new_recto);
        card.editVerso(new_verso);
        assertEquals(new_recto, card.getRecto());
        assertEquals(new_verso, card.getVerso());

    }
    @Test
    public void testEditPasVide() {
        String verso = "Ça va ?", recto = "Oui bien";
        String recto_vide = "";
        String verso_vide= "";
        String recto_null = null;
        String verso_null= null;

        Card card1 = new Card(1, recto, verso);

        Assertions.assertThrows(IllegalArgumentException.class,() -> {
            card1.editRecto(recto_vide);
        });

        Assertions.assertThrows(IllegalArgumentException.class,() -> {
            card1.editVerso(verso_vide);;
        });

        Assertions.assertThrows(IllegalArgumentException.class,() -> {
            card1.editRecto(recto_null);
        });

        Assertions.assertThrows(IllegalArgumentException.class,() -> {
            card1.editVerso(verso_null);;
        });

        Assertions.assertThrows(IllegalArgumentException.class,() -> {
            card1.editRecto("#");;
        });

        Assertions.assertThrows(IllegalArgumentException.class,() -> {
            card1.editVerso("#");;
        });

    }

    @Test
    public void testSetConnaissence(){
        Card card = new Card(1, "recto", "verso");
        card.setKnowledge(2);
        assertEquals(2, card.getConnaissance());
        Assertions.assertThrows(IllegalArgumentException.class,() -> {
            card.setKnowledge(6);
        });
        Assertions.assertThrows(IllegalArgumentException.class,() -> {
            card.setKnowledge(-1);
        });
    }

    @Test
    public void testgetCarteInfo(){
        Card card = new Card(1, "recto", "verso");
        String[] infos = card.getCardInfo();
        assertEquals("Carte", infos[0]);
        assertEquals("recto", infos[1]);
        assertEquals("verso", infos[2]);
    }

    @Test
    public void testGetCarteDTO(){
        Card card = new Card(1, "recto", "verso");
        CardDTO carteDTO = new CardDTO("recto", "verso");
        assertEquals(carteDTO, card.getDTO());
    }
}

