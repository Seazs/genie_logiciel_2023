package com.ulb.infof307.g12.server.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class PaquetTest {
    private static Paquet paquet;
    @BeforeEach
    void setUp() {
        UUID id = UUID.randomUUID();
        String nom = "Paquet";
        ArrayList<String> categories = new ArrayList<>();
        categories.add("Catégorie");
        ArrayList<Card> cards = new ArrayList<>();
        cards.add(new Card(1, "Recto", "Verso", "QCM"));
        paquet = new Paquet(id, nom, categories, cards);
    }
    @Test
    void testAddCard() {
        Card card = new Card(2, "Recto", "Verso", "QCM");
        paquet.addCarte(card);
        assertTrue(paquet.getCards().contains(card));
    }
    @Test
    void testAddDuplicateCard() {
        Card card = new Card(2, "Recto", "Verso", "QCM");
        paquet.addCarte(card);
        assertThrows(IllegalArgumentException.class, () -> paquet.addCarte(card));
    }
    @Test
    void testEquals() {
        assertNotEquals(null, paquet);
    }
    @Test
    void testAddWrongCategory() {
        assertThrows(IllegalArgumentException.class, () -> {
            Paquet paquet2 = new Paquet(UUID.randomUUID(), "Paquet", null, new ArrayList<>());
        });
        assertThrows(IllegalArgumentException.class, () -> {
            Paquet paquet2 = new Paquet(UUID.randomUUID(), null, new ArrayList<>(), new ArrayList<>());
        });
        assertThrows(IllegalArgumentException.class, () -> {
            Paquet paquet2 = new Paquet(UUID.randomUUID(), "", new ArrayList<>(), new ArrayList<>());
        });
    }
}