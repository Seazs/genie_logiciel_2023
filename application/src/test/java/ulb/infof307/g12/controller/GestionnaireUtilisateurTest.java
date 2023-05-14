package ulb.infof307.g12.controller;

import org.junit.jupiter.api.*;
import ulb.infof307.g12.controller.storage.GestionnaireUtilisateur;
import ulb.infof307.g12.model.STATUS;
import ulb.infof307.g12.model.Utilisateur;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GestionnaireUtilisateurTest {
    private File tmp;
    private GestionnaireUtilisateur gest;

    private static File dossierTemporaire;


    private static void deleteFolder(File folder) {
        File[] files = folder.listFiles();
        if(files!=null) {
            for (File f: files) {
                if(f.isDirectory()) {
                    deleteFolder(f);
                } else {
                    f.delete();
                }
            }
        }
        folder.delete();
    }

    @BeforeAll
public static void creeDossierTemporaire() throws IOException {
        dossierTemporaire = new File("./stockageTest");
        dossierTemporaire.mkdir();
    }

    @AfterAll
    public static void supprimeDossierTemporaire(){
        deleteFolder(dossierTemporaire);
    }

    @BeforeEach
    void createTmpFile() throws IOException {
        tmp = File.createTempFile("tmp",".txt");
        FileWriter writer = new FileWriter(tmp);
        writer.write("alex#pomme\n");
        writer.write("wassim#orange");
        writer.close();
        gest = new GestionnaireUtilisateur(dossierTemporaire.getPath(), tmp);

    }

    @AfterEach
    void deleteTmpFile(){

        tmp.delete();
    }

    @Test
    void loadTest() throws IOException {
        List<Utilisateur> list = gest.getListeUtilisateur();
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
        assertFalse(gest.connect("Brenno#", "123"));
        assertEquals(STATUS.USERNAME_IS_NOT_VALID,
                gest.getStatus());
        assertFalse(gest.connect("Brenno Ferreira", "123"));
        assertEquals(STATUS.USERNAME_IS_NOT_VALID,
                gest.getStatus());
        assertFalse(gest.connect("", "123"));
        assertEquals(STATUS.USERNAME_IS_NOT_VALID,
                gest.getStatus());
    }
    @Test
    void connectUtilisateurMdpPasValide() throws IOException {
        assertFalse(gest.connect("Brenno", "123#"));
        assertEquals(STATUS.PASSWORD_IS_NOT_VALID,
                gest.getStatus());
        assertFalse(gest.connect("Brenno", "123 456"));
        assertEquals(STATUS.PASSWORD_IS_NOT_VALID,
                gest.getStatus());
        assertFalse(gest.connect("Brenno", ""));
        assertEquals(STATUS.PASSWORD_IS_NOT_VALID,
                gest.getStatus());
    }
    @Test
    void connectUtilisateurOK() throws IOException {
        assertTrue(gest.connect("alex", "pomme"));
        assertEquals(STATUS.OK,
                gest.getStatus());
    }
    @Test
    void connectUtilisateurMauvaisPseudo() throws FileNotFoundException {
        assertFalse(gest.connect("alexxxx", "pomme"));
        assertEquals(STATUS.USERNAME_DOES_NOT_EXIST, gest.getStatus());
    }
    @Test
    void connectUtilisateurMauvaisMdp() throws FileNotFoundException {
        assertFalse(gest.connect("alex", "pommeeeeee"));
        assertEquals(STATUS.WRONG_PASSWORD, gest.getStatus());
    }

    @Test
    void registerUtilisateurPseudoPasValide() throws IOException {
        assertFalse(gest.register("Brenno#", "123"));
        assertEquals(STATUS.USERNAME_IS_NOT_VALID,
                gest.getStatus());
        assertFalse(gest.register("Brenno Ferreira", "123"));
        assertEquals(STATUS.USERNAME_IS_NOT_VALID,
                gest.getStatus());
        assertFalse(gest.register("", "123"));
        assertEquals(STATUS.USERNAME_IS_NOT_VALID,
                gest.getStatus());
    }
    @Test
    void registerUtilisateurMdpPasValide() throws IOException {
        assertFalse(gest.register("Brenno", "123#"));
        assertEquals(STATUS.PASSWORD_IS_NOT_VALID,
                gest.getStatus());
        assertFalse(gest.register("Brenno", "123 456"));
        assertEquals(STATUS.PASSWORD_IS_NOT_VALID,
                gest.getStatus());
        assertFalse(gest.register("Brenno", ""));
        assertEquals(STATUS.PASSWORD_IS_NOT_VALID,
                gest.getStatus());
    }

    @Test
    void registerUtilisateurPseudoExistant() throws IOException {
        assertFalse(gest.register("alex", "poire"));
        assertEquals(STATUS.USERNAME_DOES_ALREADY_EXIST, gest.getStatus());
    }

    @Test
    void registerUtilisateurOK() throws IOException {
        assertTrue(gest.register("brenno", "raisin"));
        assertEquals(STATUS.OK, gest.getStatus());
    }

    @Test
    void registerAndConnect() throws IOException {
        assertTrue(gest.register("ismail", "poire"));
        assertEquals(STATUS.OK, gest.getStatus());
        assertTrue(gest.connect("ismail", "poire"));
        assertEquals(STATUS.OK, gest.getStatus());
    }
}