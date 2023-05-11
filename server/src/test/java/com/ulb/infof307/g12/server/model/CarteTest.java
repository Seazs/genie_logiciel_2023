package com.ulb.infof307.g12.server.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CarteTest {
    @Test
    public void testCreateCard(){
        String recto = "Bonjour", verso = "Au revoir";
        Assertions.assertThrows(IllegalArgumentException.class,() -> {
            Carte carte = new Carte(1, "", verso,"QCM");
        });
        Assertions.assertThrows(IllegalArgumentException.class,() -> {
            Carte carte = new Carte(2, recto, "","QCM");
        });
        Assertions.assertThrows(IllegalArgumentException.class,() -> {
            Carte carte = new Carte(2, recto, null,"QCM");
        });
        Assertions.assertThrows(IllegalArgumentException.class,() -> {
            Carte carte = new Carte(2, null, verso,"QCM");
        });
        Assertions.assertThrows(IllegalArgumentException.class,() -> {
            Carte carte = new Carte(2, "#", verso,"QCM");
        });
        Assertions.assertThrows(IllegalArgumentException.class,() -> {
            Carte carte = new Carte(2, recto, "#","QCM");
        });
        Assertions.assertThrows(IllegalArgumentException.class,() -> {
            Carte carte = new Carte(2, recto, verso,"test");
        });
        Assertions.assertDoesNotThrow( ()-> {
            Carte carte = new Carte(2, recto, verso,"QCM");
        });
    }

    @Test
    public void testEditebien() {
        String new_recto = "oloY";
        Carte carte = new Carte(1, "Yolo", "Salut","QCM");
        carte.editRecto(new_recto);
        assertEquals(new_recto, carte.getRecto());
    }
    @Test
    public void testEditPasVide() {
        String verso = "Ça va ?", recto = "Oui bien";
        String recto_vide = "";
        String verso_vide= "";
        String recto_null = null;
        String verso_null= null;

        Carte carte1 = new Carte(1, recto, verso,"QCM");
        Assertions.assertThrows(IllegalArgumentException.class,() -> {
            carte1.editRecto(recto_vide);
        });
        Assertions.assertThrows(IllegalArgumentException.class,() -> {
            carte1.editRecto(recto_null);
        });
        Assertions.assertThrows(IllegalArgumentException.class,() -> {
            carte1.editRecto("#");;
        });
    }
}