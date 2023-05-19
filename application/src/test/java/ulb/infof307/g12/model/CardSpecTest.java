package ulb.infof307.g12.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CardSpecTest {

    @Test
    void testgetCarteInfo() {
        CardSpec carteSpec = new CardSpec(1, "recto", "verso", "html");
        String[] info = carteSpec.getCardInfo();
        assertEquals("Spec", info[0]);
        assertEquals("recto", info[1]);
        assertEquals("verso", info[2]);
        assertEquals("html", info[3]);
    }

    @Test
    void testgetCarteInfo2() {
        CardSpec carteSpec = new CardSpec(1, "recto", "verso", "html", 5);
        String[] info = carteSpec.getCardInfo();
        assertEquals("Spec", info[0]);
        assertEquals("recto", info[1]);
        assertEquals("verso", info[2]);
        assertEquals("html", info[3]);
        assertEquals(5, carteSpec.getConnaissance());
    }


    @Test
    void testCheckLanguage(){
        assertThrows(IllegalArgumentException.class, () -> {
            CardSpec carteSpec = new CardSpec(1, "recto", "verso", "falselanguage");
        });
        assertThrows(IllegalArgumentException.class, () -> {
            CardSpec carteSpec = new CardSpec(1, "recto", null, "html");
        });
        assertThrows(IllegalArgumentException.class, () -> {
            CardSpec carteSpec = new CardSpec(1, "recto", "null", null);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            CardSpec carteSpec = new CardSpec(1, "\\begin{dad", "null", "latex");
        });
    }
}