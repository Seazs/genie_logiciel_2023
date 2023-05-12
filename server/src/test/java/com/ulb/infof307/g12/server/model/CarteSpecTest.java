package com.ulb.infof307.g12.server.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CarteSpecTest {

    @Test
    void testConstructor() {
        CarteSpec carteSpec = new CarteSpec(1, "Recto", "Verso", "html", 0);
        assertEquals("Recto", carteSpec.getRecto());
        assertEquals("Verso", carteSpec.getVerso());
        assertEquals("Spec", carteSpec.getType());
        assertEquals("html", carteSpec.getLanguage());
    }
    @Test
    void testConstructorLanguage() {
        assertThrows(IllegalArgumentException.class, () -> {
            CarteSpec carteSpec = new CarteSpec(1, "Recto", "Verso", null, 0);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            CarteSpec carteSpec = new CarteSpec(1, "Recto", "Verso", "231", 0);
        });
        assertDoesNotThrow( () -> {
            CarteSpec carteSpec = new CarteSpec(1, "Recto", "Verso", "html", 0);
        });
        assertDoesNotThrow( () -> {
            CarteSpec carteSpec = new CarteSpec(1, "Recto", "Verso", "latex", 0);
        });
    }
}