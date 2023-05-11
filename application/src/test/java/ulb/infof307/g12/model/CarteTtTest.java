package ulb.infof307.g12.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CarteTtTest {

    @Test
    void testgetCarteInfo() {
        CarteTt carteTt = new CarteTt(1, "begin§end", "reponse");
        String[] info = carteTt.getCarteInfo();
        assertEquals("begin", info[0]);
        assertEquals("end", info[1]);
        assertEquals("reponse", info[2]);
    }

    @Test
    void testgetCarteInfo2() {
        CarteTt carteTt = new CarteTt(1, "recto", "verso","begin", "end", "reponse");
        String[] info = carteTt.getCarteInfo();
        assertEquals("begin", info[0]);
        assertEquals("end", info[1]);
        assertEquals("reponse", info[2]);
    }
}