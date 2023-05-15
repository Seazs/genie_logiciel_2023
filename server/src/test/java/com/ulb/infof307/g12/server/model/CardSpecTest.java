package com.ulb.infof307.g12.server.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CardSpecTest {

    @Test
    void testConstructor() {
        CardSpec carteSpec = new CardSpec(1, "Recto", "Verso", "html", 0);
        assertEquals("Recto", carteSpec.getRecto());
        assertEquals("Verso", carteSpec.getVerso());
        assertEquals("Spec", carteSpec.getType());
        assertEquals("html", carteSpec.getLanguage());
    }
    @Test
    void testConstructorLanguage() {
        assertThrows(IllegalArgumentException.class, () -> {
            CardSpec carteSpec = new CardSpec(1, "Recto", "Verso", null, 0);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            CardSpec carteSpec = new CardSpec(1, "Recto", "Verso", "231", 0);
        });
        assertDoesNotThrow( () -> {
            CardSpec carteSpec = new CardSpec(1, "Recto", "Verso", "html", 0);
        });
        assertDoesNotThrow( () -> {
            CardSpec carteSpec = new CardSpec(1, "Recto", "Verso", "latex", 0);
        });
    }
}