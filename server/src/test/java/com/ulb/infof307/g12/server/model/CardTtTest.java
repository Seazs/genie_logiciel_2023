package com.ulb.infof307.g12.server.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CardTtTest {
    @Test
    void testConstructor() {
        CardTt carteTt = new CardTt(1, "Recto", "Verso", "begin", "end", "answer");
        assertEquals("Recto", carteTt.getRecto());
        assertEquals("Verso", carteTt.getVerso());
        assertEquals("TT", carteTt.getType());
        assertEquals("begin", carteTt.getBegin());
        assertEquals("end", carteTt.getEnd());
        assertEquals("answer", carteTt.getAnswer());
    }
}