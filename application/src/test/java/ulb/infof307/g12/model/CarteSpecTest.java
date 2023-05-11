package ulb.infof307.g12.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CarteSpecTest {

    @Test
    void testgetCarteInfo() {
        CarteSpec carteSpec = new CarteSpec(1, "recto", "verso", "html");
        String[] info = carteSpec.getCarteInfo();
        assertEquals("Spec", info[0]);
        assertEquals("recto", info[1]);
        assertEquals("verso", info[2]);
        assertEquals("html", info[3]);
    }

    @Test
    void testgetCarteInfo2() {
        CarteSpec carteSpec = new CarteSpec(1, "recto", "verso", "html", 5);
        String[] info = carteSpec.getCarteInfo();
        assertEquals("Spec", info[0]);
        assertEquals("recto", info[1]);
        assertEquals("verso", info[2]);
        assertEquals("html", info[3]);
        assertEquals(5, carteSpec.getConnaissance());
    }


    @Test
    void testCheckLanguage(){
        assertThrows(IllegalArgumentException.class, () -> {
            CarteSpec carteSpec = new CarteSpec(1, "recto", "verso", "falselanguage");
        });
        assertThrows(IllegalArgumentException.class, () -> {
            CarteSpec carteSpec = new CarteSpec(1, "recto", null, "html");
        });
        assertThrows(IllegalArgumentException.class, () -> {
            CarteSpec carteSpec = new CarteSpec(1, "recto", "null", null);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            CarteSpec carteSpec = new CarteSpec(1, "\\begin{dad", "null", "latex");
        });
    }
}