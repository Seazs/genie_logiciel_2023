package ulb.infof307.g12.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UtilisateurTest {

    @Test
    void getPseudoTest() {
        Utilisateur pers1 = new Utilisateur("alex","pomme");
        assertEquals("alex",pers1.getPseudo());
    }

    @Test
    void getMdpTest() {
        Utilisateur pers1 = new Utilisateur("alex","pomme");
        assertEquals("pomme",pers1.getMdp());
    }
    @Test
    void setMdpTest() {
        Utilisateur pers1 = new Utilisateur("alex","pomme");
        pers1.setMdp("poire");
        assertEquals("poire",pers1.getMdp());
    }
    @Test
    void addPaquetTest() {

    }
    @Test
    void rmPaquetTest(){

    }

}