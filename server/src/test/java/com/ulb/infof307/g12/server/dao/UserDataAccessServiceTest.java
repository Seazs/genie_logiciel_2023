package com.ulb.infof307.g12.server.dao;

import com.ulb.infof307.g12.server.model.STATUS;
import com.ulb.infof307.g12.server.model.User;
import org.junit.jupiter.api.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserDataAccessServiceTest {
    private File tmp;
    @BeforeEach
    void createTmpFile() throws IOException {
        tmp = new File("server/src/main/resources/stockage","stockUser.txt");
        tmp.getParentFile().mkdirs();
        tmp.createNewFile();
        FileWriter writer = new FileWriter(tmp);
        writer.write("t#t");
        writer.close();
    }

    @AfterEach
    void deleteTmpFile(){
        tmp.delete();
    }

    @Test
    void updateGetUser() {
        User user = new User("test","test");
        User user2 = new User("test2","test2");
        User user3 = new User("test3","test3");
        UserDataAccessService userDataAccessService = new UserDataAccessService();
        userDataAccessService.createUser(user);
        userDataAccessService.createUser(user2);
        userDataAccessService.createUser(user3);
        UserDataAccessService userDataAccessService2 = new UserDataAccessService();
        assertEquals("test",userDataAccessService2.getPassword("test"));
        assertEquals("test2",userDataAccessService2.getPassword("test2"));
        assertEquals("test3",userDataAccessService2.getPassword("test3"));
    }
    @Test
    void testCreateUser() {
        User user = new User("test","test");
        UserDataAccessService userDataAccessService = new UserDataAccessService();
        assertEquals(STATUS.OK,userDataAccessService.createUser(user));
        assertEquals("test",userDataAccessService.getPassword("test"));
    }

    @Test
    void updateUserTest() {
        User user = new User("123","123");
        User user2 = new User("123","456");
        UserDataAccessService userDataAccessService = new UserDataAccessService();
        userDataAccessService.createUser(user);
        userDataAccessService.updateUser(user2);
        assertEquals("456",userDataAccessService.getPassword("123"));
    }

    @Test
    void memeUser(){
        User user = new User("123","123");
        User user2 = new User("123","456");
        UserDataAccessService userDataAccessService = new UserDataAccessService();
        userDataAccessService.createUser(user);
        assertEquals(STATUS.USERNAME_DOES_ALREADY_EXIST,userDataAccessService.createUser(user2));
    }

    @Test
    void updateUserExistePas(){
        User user = new User("123","123");
        UserDataAccessService userDataAccessService = new UserDataAccessService();
        assertEquals(STATUS.USERNAME_DOES_NOT_EXIST,userDataAccessService.updateUser(user));
    }


    @Test
    void loadFichierErronee() throws IOException {
        File tmp = new File("server/src/main/resources/stockage","stockUser.txt");
        FileWriter myWriter = new FileWriter(tmp);
        myWriter.write("test");
        myWriter.close();
        UserDataAccessService userDataAccessService = new UserDataAccessService();
        assertEquals(STATUS.DB_COULD_NOT_BE_LOADED,userDataAccessService.status);
        tmp.delete();
    }
    /*
    @Test
    void loadTest() throws IOException {
        UserDataAccessService userDataAccessService = new UserDataAccessService();
        List<User> list = userDataAccessService.getAllUsers();
        assertEquals("alex",list.get(0).getPseudo());
        assertEquals("wassim",list.get(1).getPseudo());
        assertEquals("pomme",list.get(0).getMdp());
        assertEquals("orange",list.get(1).getMdp());
    }

    @Test
    void loadTestVide() throws IOException {
        File tmpVide = File.createTempFile("tmpVide",".txt");
        GestionnaireUtilisateur gest = new GestionnaireUtilisateur(tmpVide);
        List<Utilisateur> list = gest.getListeUtilisateur();
        assertEquals(0,list.size());
        tmpVide.delete();
    }

    @Test void saveTest() throws IOException {
        GestionnaireUtilisateur gest = new GestionnaireUtilisateur(tmp);
        List<Utilisateur> list = gest.getListeUtilisateur();
        Utilisateur u1 = list.get(0);
        Utilisateur u2 = list.get(1);
        assertEquals("alex",u1.getPseudo());
        assertEquals("wassim",u2.getPseudo());
        assertEquals("pomme",u1.getMdp());
        assertEquals("orange",u2.getMdp());
    }

    @Test
    void connectUtilisateurPseudoPasValide() throws IOException {
        GestionnaireUtilisateur gestionnaire = new GestionnaireUtilisateur(tmp);
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
        GestionnaireUtilisateur gestionnaire = new GestionnaireUtilisateur(tmp);
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
        GestionnaireUtilisateur gestionnaire = new GestionnaireUtilisateur(tmp);
        assertTrue(gestionnaire.connect("alex", "pomme"));
        assertEquals(STATUS.OK,
                gestionnaire.getStatus());
    }
    @Test
    void connectUtilisateurMauvaisPseudo() throws FileNotFoundException {
        GestionnaireUtilisateur gestionnaire = new GestionnaireUtilisateur(tmp);
        assertFalse(gestionnaire.connect("alexxxx", "pomme"));
        assertEquals(STATUS.USERNAME_DOES_NOT_EXIST, gestionnaire.getStatus());
    }
    @Test
    void connectUtilisateurMauvaisMdp() throws FileNotFoundException {
        GestionnaireUtilisateur gestionnaire = new GestionnaireUtilisateur(tmp);
        assertFalse(gestionnaire.connect("alex", "pommeeeeee"));
        assertEquals(STATUS.WRONG_PASSWORD, gestionnaire.getStatus());
    }

    @Test
    void registerUtilisateurPseudoPasValide() throws IOException {
        GestionnaireUtilisateur gestionnaire = new GestionnaireUtilisateur(tmp);
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
        GestionnaireUtilisateur gestionnaire = new GestionnaireUtilisateur(tmp);
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
        GestionnaireUtilisateur gestionnaire = new GestionnaireUtilisateur(tmp);
        assertFalse(gestionnaire.register("alex", "poire"));
        assertEquals(STATUS.USERNAME_DOES_ALREADY_EXIST, gestionnaire.getStatus());
    }

    @Test
    void registerUtilisateurOK() throws IOException {
        GestionnaireUtilisateur gestionnaire = new GestionnaireUtilisateur(tmp);
        assertTrue(gestionnaire.register("brenno", "raisin"));
        assertEquals(STATUS.OK, gestionnaire.getStatus());
    }

    @Test
    void registerAndConnect() throws IOException {
        GestionnaireUtilisateur gestionnaire = new GestionnaireUtilisateur(tmp);
        assertTrue(gestionnaire.register("ismail", "poire"));
        assertEquals(STATUS.OK, gestionnaire.getStatus());
        assertTrue(gestionnaire.connect("ismail", "poire"));
        assertEquals(STATUS.OK, gestionnaire.getStatus());
    }

    @Test
    void removeUserTest() throws IOException{
        GestionnaireUtilisateur gestuser = new GestionnaireUtilisateur();
        Utilisateur user1 = new Utilisateur("felix","meilleuramipourlavie");
        gestuser.register(user1.getPseudo(), user1.getMdp());
        gestuser.removeUser(user1);
        //Test
        File f = new File("src/main/resources/stockage/"+user1.getPseudo());
        assertFalse(f.exists());
        assertFalse(gestuser.getListeUtilisateur().contains(user1));
    }

     */
}