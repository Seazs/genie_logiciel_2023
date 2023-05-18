package com.ulb.infof307.g12.server.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CardTest {
    @Test
    public void testCreateCard(){
        String recto = "Bonjour", verso = "Au revoir";
        Assertions.assertThrows(IllegalArgumentException.class,() -> {
            Card card = new Card(1, "", verso,"QCM");
        });
        Assertions.assertThrows(IllegalArgumentException.class,() -> {
            Card card = new Card(2, recto, "","QCM");
        });
        Assertions.assertThrows(IllegalArgumentException.class,() -> {
            Card card = new Card(2, recto, null,"QCM");
        });
        Assertions.assertThrows(IllegalArgumentException.class,() -> {
            Card card = new Card(2, null, verso,"QCM");
        });
        Assertions.assertThrows(IllegalArgumentException.class,() -> {
            Card card = new Card(2, "#", verso,"QCM");
        });
        Assertions.assertThrows(IllegalArgumentException.class,() -> {
            Card card = new Card(2, recto, "#","QCM");
        });
        Assertions.assertThrows(IllegalArgumentException.class,() -> {
            Card card = new Card(2, recto, verso,"test");
        });
        Assertions.assertDoesNotThrow( ()-> {
            Card card = new Card(2, recto, verso,"QCM");
        });
    }

    @Test
    public void testEditCorrect() {
        String new_recto = "oloY";
        Card card = new Card(1, "Yolo", "Salut","QCM");
        card.editRecto(new_recto);
        assertEquals(new_recto, card.getRecto());
    }
    @Test
    public void testEditNotEmpty() {
        String verso = "Ça va ?", recto = "Oui bien";

        Card card1 = new Card(1, recto, verso,"QCM");
        Assertions.assertThrows(IllegalArgumentException.class,() -> {
            card1.editRecto("");
        });
        Assertions.assertThrows(IllegalArgumentException.class,() -> {
            card1.editRecto(null);
        });
        Assertions.assertThrows(IllegalArgumentException.class,() -> {
            card1.editRecto("#");;
        });
    }
}