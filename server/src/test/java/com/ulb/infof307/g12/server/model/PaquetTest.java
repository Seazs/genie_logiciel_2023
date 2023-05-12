package com.ulb.infof307.g12.server.model;

import org.junit.jupiter.api.BeforeAll;
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
        ArrayList<Carte> cartes = new ArrayList<>();
        cartes.add(new Carte(1, "Recto", "Verso", "QCM"));
        paquet = new Paquet(id, nom, categories, cartes);
    }
    @Test
    void ajouterCarte() {
        Carte carte = new Carte(2, "Recto", "Verso", "QCM");
        paquet.ajouterCarte(carte);
        assertTrue(paquet.getCartes().contains(carte));
    }
    @Test
    void ajouterDuplicateCarte() {
        Carte carte = new Carte(2, "Recto", "Verso", "QCM");
        paquet.ajouterCarte(carte);
        assertThrows(IllegalArgumentException.class, () -> paquet.ajouterCarte(carte));
    }
    @Test
    void testEquals() {
        assertEquals(paquet, paquet);
        assertFalse(paquet.equals(null));
    }
    @Test
    void ajouterMauvaisCategorie() {
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