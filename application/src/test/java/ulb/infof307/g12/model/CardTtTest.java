package ulb.infof307.g12.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CardTtTest {

    @Test
    void testgetCarteInfo() {
        CardTt carteTt = new CardTt(1, "begin§end", "reponse");
        String[] info = carteTt.getCardInfo();
        assertEquals("begin", info[0]);
        assertEquals("end", info[1]);
        assertEquals("reponse", info[2]);
    }

    @Test
    void testgetCarteInfo2() {
        CardTt carteTt = new CardTt(1, "recto", "verso","begin", "end", "reponse");
        String[] info = carteTt.getCardInfo();
        assertEquals("begin", info[0]);
        assertEquals("end", info[1]);
        assertEquals("reponse", info[2]);
    }
}