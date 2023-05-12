package ulb.infof307.g12.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class UtilisateurTest {

    @Test
    void getPseudoTest() {
        Utilisateur pers1 = new Utilisateur("alex", "pomme");
        assertEquals("alex", pers1.getPseudo());
    }

    @Test
    void getMdpTest() {
        Utilisateur pers1 = new Utilisateur("alex", "pomme");
        assertEquals("pomme", pers1.getMdp());
    }

    @Test
    void setMdpTest() {
        Utilisateur pers1 = new Utilisateur("alex", "pomme");
        pers1.setMdp("poire");
        assertEquals("poire", pers1.getMdp());
    }

    @Test
    void addPaquetTest() {
        Utilisateur pers1 = new Utilisateur("alex", "pomme");
        Paquet paquet1 = new Paquet("Maths", "BA-1");
        pers1.addPaquet(paquet1);
        assertEquals(paquet1, pers1.getListPaquet().get(0));
    }

    @Test
    void rmPaquetTest() {
        Utilisateur pers1 = new Utilisateur("alex", "pomme");
        Paquet paquet1 = new Paquet("Maths", "BA-1");
        pers1.addPaquet(paquet1);
        pers1.removePaquet(paquet1.getId());
        assertEquals(0, pers1.getListPaquet().size());
    }

    @Test
    void validitePseudoTest() {
        assertThrows(IllegalArgumentException.class,
                () -> {
                    Utilisateur pers1 = new Utilisateur("Brenno#", "123");
                });
        assertThrows(IllegalArgumentException.class,
                () -> {
                    Utilisateur pers1 = new Utilisateur("Brenno Ferreira", "123");
                });
        assertThrows(IllegalArgumentException.class,
                () -> {
                    Utilisateur pers1 = new Utilisateur("", "123");
                });
    }
    @Test
    void validiteMdpTest() {
        assertThrows(IllegalArgumentException.class,
                () -> {
                    Utilisateur pers1 = new Utilisateur("Brenno", "123#");
                });
        assertThrows(IllegalArgumentException.class,
                () -> {
                    Utilisateur pers1 = new Utilisateur("Brenno", "123 456");
                });
        assertThrows(IllegalArgumentException.class,
                () -> {
                    Utilisateur pers1 = new Utilisateur("Brenno", "");
                });
    }

    @Test
    void setMdp() {
        Utilisateur pers1 = new Utilisateur("Brenno", "brebre");
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