package ulb.infof307.g12.model;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CarteQcmTest {
    @Test
    public void testCreatQCM1(){
        String recto = "question§reponse1§reponse2§reponse3", verso = "la reponse D";
        CarteQcm carte = new CarteQcm(1, recto, verso);
        String[] infos_carte = carte.getCarteInfo();
        assertEquals("question", infos_carte[0]);
        assertEquals("reponse1", infos_carte[1]);
        assertEquals("reponse2", infos_carte[2]);
        assertEquals("reponse3", infos_carte[3]);
        assertEquals("la reponse D", infos_carte[4]);;
    }

    @Test
    public void testCreatQCM2(){
        String recto = "question", verso = "la reponse D";
        String[] reponses = {"reponse1", "reponse2", "reponse3"};
        CarteQcm carte = new CarteQcm(1, recto, verso, reponses);
        String[] infos_carte = carte.getCarteInfo();
        assertEquals("question", infos_carte[0]);
        assertEquals("reponse1", infos_carte[1]);
        assertEquals("reponse2", infos_carte[2]);
        assertEquals("reponse3", infos_carte[3]);
        assertEquals("la reponse D", infos_carte[4]);;
    }
}