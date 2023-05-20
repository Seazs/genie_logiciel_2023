package ulb.infof307.g12.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class UserTest {

    @Test
    void getPseudoTest() {
        User pers1 = new User("alex", "pomme");
        assertEquals("alex", pers1.getPseudo());
    }

    @Test
    void getMdpTest() {
        User pers1 = new User("alex", "pomme");
        assertEquals("pomme", pers1.getMdp());
    }

    @Test
    void setMdpTest() {
        User pers1 = new User("alex", "pomme");
        pers1.setMdp("poire");
        assertEquals("poire", pers1.getMdp());
    }

    @Test
    void addPaquetTest() {
        User pers1 = new User("alex", "pomme");
        Paquet paquet1 = new Paquet("Maths", "BA-1");
        pers1.addPaquet(paquet1);
        assertEquals(paquet1, pers1.getListPaquet().get(0));
    }

    @Test
    void rmPaquetTest() {
        User pers1 = new User("alex", "pomme");
        Paquet paquet1 = new Paquet("Maths", "BA-1");
        pers1.addPaquet(paquet1);
        pers1.removePaquet(paquet1.getId());
        assertEquals(0, pers1.getListPaquet().size());
    }

    @Test
    void validityPseudoTest() {
        assertThrows(IllegalArgumentException.class,
                () -> {
                    User pers1 = new User("Brenno#", "123");
                });
        assertThrows(IllegalArgumentException.class,
                () -> {
                    User pers1 = new User("Brenno Ferreira", "123");
                });
        assertThrows(IllegalArgumentException.class,
                () -> {
                    User pers1 = new User("", "123");
                });
    }
    @Test
    void validityMdpTest() {
        assertThrows(IllegalArgumentException.class,
                () -> {
                    User pers1 = new User("Brenno", "123#");
                });
        assertThrows(IllegalArgumentException.class,
                () -> {
                    User pers1 = new User("Brenno", "123 456");
                });
        assertThrows(IllegalArgumentException.class,
                () -> {
                    User pers1 = new User("Brenno", "");
                });
    }

    @Test
    void setMdp() {
        User pers1 = new User("Brenno", "brebre");
        assertThrows(IllegalArgumentException.class,
                () -> {
                   pers1.setMdp("bre#bre");
                });
        assertThrows(IllegalArgumentException.class,
                () -> {
                    pers1.setMdp("bre bre");
                });
        assertThrows(IllegalArgumentException.class,
                () -> {
                    pers1.setMdp("");
                });
    }
}