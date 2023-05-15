package ulb.infof307.g12.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ulb.infof307.g12.controller.storage.UserManager;
import ulb.infof307.g12.model.STATUS;
import ulb.infof307.g12.model.User;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GestionnaireUserTest {
    private File tmp;
    @BeforeEach
    void createTmpFile() throws IOException {
        tmp = File.createTempFile("tmp",".txt");
        FileWriter writer = new FileWriter(tmp);
        writer.write("alex#pomme\n");
        writer.write("wassim#orange");
        writer.close();
    }

    @AfterEach
    void deleteTmpFile(){
        tmp.delete();
    }

    @Test
    void loadTest() throws IOException {
        UserManager gest = new UserManager(tmp);
        List<User> list = gest.getListUser();
        assertEquals("alex",list.get(0).getPseudo());
        assertEquals("wassim",list.get(1).getPseudo());
        assertEquals("pomme",list.get(0).getMdp());
        assertEquals("orange",list.get(1).getMdp());
    }

    @Test
    void loadTestVide() throws IOException {
        File tmpVide = File.createTempFile("tmpVide",".txt");
        UserManager gest = new UserManager(tmpVide);
        List<User> list = gest.getListUser();
        assertEquals(0,list.size());
        tmpVide.delete();
    }

    @Test void saveTest() throws IOException {
        UserManager gest = new UserManager(tmp);
        List<User> list = gest.getListUser();
        User u1 = list.get(0);
        User u2 = list.get(1);
        assertEquals("alex",u1.getPseudo());
        assertEquals("wassim",u2.getPseudo());
        assertEquals("pomme",u1.getMdp());
        assertEquals("orange",u2.getMdp());
    }

    @Test
    void connectUtilisateurPseudoPasValide() throws IOException {
        UserManager gestionnaire = new UserManager(tmp);
        assertFalse(gestionnaire.connect("Brenno#", "123"));
        assertEquals(STATUS.USERNAME_IS_NOT_VALID,
                gestionnaire.getStatus());
        assertFalse(gestionnaire.connect("Brenno Ferreira", "123"));
        assertEquals(STATUS.USERNAME_IS_NOT_VALID,
                gestionnaire.getStatus());
        assertFalse(gestionnaire.connect("", "123"));
        assertEquals(STATUS.USERNAME_IS_NOT_VALID,
                gestionnaire.getStatus());
    }
    @Test
    void connectUtilisateurMdpPasValide() throws IOException {
        UserManager gestionnaire = new UserManager(tmp);
        assertFalse(gestionnaire.connect("Brenno", "123#"));
        assertEquals(STATUS.PASSWORD_IS_NOT_VALID,
                gestionnaire.getStatus());
        assertFalse(gestionnaire.connect("Brenno", "123 456"));
        assertEquals(STATUS.PASSWORD_IS_NOT_VALID,
                gestionnaire.getStatus());
        assertFalse(gestionnaire.connect("Brenno", ""));
        assertEquals(STATUS.PASSWORD_IS_NOT_VALID,
                gestionnaire.getStatus());
    }
    @Test
    void connectUtilisateurOK() throws IOException {
        UserManager gestionnaire = new UserManager(tmp);
        assertTrue(gestionnaire.connect("alex", "pomme"));
        assertEquals(STATUS.OK,
                gestionnaire.getStatus());
    }
    @Test
    void connectUtilisateurMauvaisPseudo() throws FileNotFoundException {
        UserManager gestionnaire = new UserManager(tmp);
        assertFalse(gestionnaire.connect("alexxxx", "pomme"));
        assertEquals(STATUS.USERNAME_DOES_NOT_EXIST, gestionnaire.getStatus());
    }
    @Test
    void connectUtilisateurMauvaisMdp() throws FileNotFoundException {
        UserManager gestionnaire = new UserManager(tmp);
        assertFalse(gestionnaire.connect("alex", "pommeeeeee"));
        assertEquals(STATUS.WRONG_PASSWORD, gestionnaire.getStatus());
    }

    @Test
    void registerUtilisateurPseudoPasValide() throws IOException {
        UserManager gestionnaire = new UserManager(tmp);
        assertFalse(gestionnaire.register("Brenno#", "123"));
        assertEquals(STATUS.USERNAME_IS_NOT_VALID,
                gestionnaire.getStatus());
        assertFalse(gestionnaire.register("Brenno Ferreira", "123"));
        assertEquals(STATUS.USERNAME_IS_NOT_VALID,
                gestionnaire.getStatus());
        assertFalse(gestionnaire.register("", "123"));
        assertEquals(STATUS.USERNAME_IS_NOT_VALID,
                gestionnaire.getStatus());
    }
    @Test
    void registerUtilisateurMdpPasValide() throws IOException {
        UserManager gestionnaire = new UserManager(tmp);
        assertFalse(gestionnaire.register("Brenno", "123#"));
        assertEquals(STATUS.PASSWORD_IS_NOT_VALID,
                gestionnaire.getStatus());
        assertFalse(gestionnaire.register("Brenno", "123 456"));
        assertEquals(STATUS.PASSWORD_IS_NOT_VALID,
                gestionnaire.getStatus());
        assertFalse(gestionnaire.register("Brenno", ""));
        assertEquals(STATUS.PASSWORD_IS_NOT_VALID,
                gestionnaire.getStatus());
    }

    @Test
    void registerUtilisateurPseudoExistant() throws IOException {
        UserManager gestionnaire = new UserManager(tmp);
        assertFalse(gestionnaire.register("alex", "poire"));
        assertEquals(STATUS.USERNAME_DOES_ALREADY_EXIST, gestionnaire.getStatus());
    }

    @Test
    void registerUtilisateurOK() throws IOException {
        UserManager gestionnaire = new UserManager(tmp);
        assertTrue(gestionnaire.register("brenno", "raisin"));
        assertEquals(STATUS.OK, gestionnaire.getStatus());
    }

    @Test
    void registerAndConnect() throws IOException {
        UserManager gestionnaire = new UserManager(tmp);
        assertTrue(gestionnaire.register("ismail", "poire"));
        assertEquals(STATUS.OK, gestionnaire.getStatus());
        assertTrue(gestionnaire.connect("ismail", "poire"));
        assertEquals(STATUS.OK, gestionnaire.getStatus());
    }

    @Test
    void removeUserTest() throws IOException{
        UserManager gestuser = new UserManager();
        User user1 = new User("felix","meilleuramipourlavie");
        gestuser.register(user1.getPseudo(), user1.getMdp());
        gestuser.removeUser(user1);
        //Test
        File f = new File("src/main/resources/stockage/"+user1.getPseudo());
        assertFalse(f.exists());
        assertFalse(gestuser.getListUser().contains(user1));
    }
}