package com.ulb.infof307.g12.server.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CardQcmTest {
    @Test
    void testConstructor() {
        CardQcm carteQcm = new CardQcm(1, "Recto", "Verso", new String[]{"Proposition 1", "Proposition 2", "Proposition 3"});
        assertEquals("Recto", carteQcm.getRecto());
        assertEquals("Verso", carteQcm.getVerso());
        assertEquals("QCM", carteQcm.getType());
        assertEquals("Recto", carteQcm.getQuestion());
        assertEquals("Verso", carteQcm.getAnswer());
        assertEquals("Proposition 1", carteQcm.getPropositions()[0]);
        assertEquals("Proposition 2", carteQcm.getPropositions()[1]);
        assertEquals("Proposition 3", carteQcm.getPropositions()[2]);
    }

}