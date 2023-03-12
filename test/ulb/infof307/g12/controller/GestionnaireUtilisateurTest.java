package ulb.infof307.g12.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
    @BeforeEach
    void createTmpFile() throws IOException {
        tmp = File.createTempFile("tmp",".txt");
        FileWriter writer = new FileWriter(tmp);
        writer.write("alex#pomme\n");
        writer.write("wassim#orange");
        writer.close();
    }

    @Test
    void loadTest() throws IOException {
        GestionnaireUtilisateur gest = new GestionnaireUtilisateur(tmp);
        List<Utilisateur> list = gest.getListeUtilisateur();
        assertEquals("alex",list.get(0).getPseudo());
        assertEquals("wassim",list.get(1).getPseudo());
        assertEquals("pomme",list.get(0).getMdp());
        assertEquals("orange",list.get(1).getMdp());
        tmp.delete();
    }

    @Test
    void loadTestVide() throws IOException {
        File tmpVide = File.createTempFile("tmpVide",".txt");
        GestionnaireUtilisateur gest = new GestionnaireUtilisateur(tmpVide);
        List<Utilisateur> list = gest.getListeUtilisateur();
        assertEquals(0,list.size());
        tmpVide.delete();
        tmp.delete();
    }
    /*
    @Test
    void loadTestNoArgument() throws IOException {
        File tmp = File.createTempFile("stockUser",".txt");
        FileWriter writer = new FileWriter(tmp);
        writer.write("alex#pomme\n");
        writer.write("wassim#orange");
        writer.close();
        GestionnaireUtilisateur gest = new GestionnaireUtilisateur();
        List<Utilisateur> list = gest.getListeUtilisateur();
        assertEquals("alex",list.get(0).getPseudo());
        assertEquals("wassim",list.get(1).getPseudo());
        assertEquals("pomme",list.get(0).getMdp());
        assertEquals("orange",list.get(1).getMdp());
        if(tmp.delete()){throw new Error("Fichier temporaire non effacé");}
    }
     */
    @Test void saveTest() throws IOException {
        GestionnaireUtilisateur gest1 = new GestionnaireUtilisateur(tmp);
        FileWriter writer2 = new FileWriter(tmp);
        writer2.write("");
        writer2.close();
        gest1.save();
        GestionnaireUtilisateur gest = new GestionnaireUtilisateur(tmp);
        List<Utilisateur> list = gest.getListeUtilisateur();
        Utilisateur u1 = list.get(0);
        Utilisateur u2 = list.get(0);
        assertEquals("alex",list.get(0).getPseudo());
        assertEquals("wassim",list.get(1).getPseudo());
        assertEquals("pomme",list.get(0).getMdp());
        assertEquals("orange",list.get(1).getMdp());
        tmp.delete();
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
        tmp.delete();
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
        tmp.delete();
    }
    @Test
    void connectUtilisateurOK() throws IOException {
        GestionnaireUtilisateur gestionnaire = new GestionnaireUtilisateur(tmp);
        assertTrue(gestionnaire.connect("alex", "pomme"));
        assertEquals(STATUS.OK,
                gestionnaire.getStatus());
        tmp.delete();
    }
    @Test
    void connectUtilisateurMauvaisPseudo() throws FileNotFoundException {
        GestionnaireUtilisateur gestionnaire = new GestionnaireUtilisateur(tmp);
        assertFalse(gestionnaire.connect("alexxxx", "pomme"));
        assertEquals(STATUS.USERNAME_DOES_NOT_EXIST, gestionnaire.getStatus());
        tmp.delete();
    }
    @Test
    void connectUtilisateurMauvaisMdp() throws FileNotFoundException {
        GestionnaireUtilisateur gestionnaire = new GestionnaireUtilisateur(tmp);
        assertFalse(gestionnaire.connect("alex", "pommeeeeee"));
        assertEquals(STATUS.WRONG_PASSWORD, gestionnaire.getStatus());
        tmp.delete();
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
        tmp.delete();
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
        tmp.delete();
    }

    @Test
    void registerUtilisateurPseudoExistant() throws IOException {
        GestionnaireUtilisateur gestionnaire = new GestionnaireUtilisateur(tmp);
        assertFalse(gestionnaire.register("alex", "poire"));
        assertEquals(STATUS.USERNAME_DOES_ALREADY_EXIST, gestionnaire.getStatus());
        tmp.delete();
    }

    @Test
    void registerUtilisateurOK() throws IOException {
        GestionnaireUtilisateur gestionnaire = new GestionnaireUtilisateur(tmp);
        assertTrue(gestionnaire.register("brenno", "raisin"));
        assertEquals(STATUS.OK, gestionnaire.getStatus());
        tmp.delete();
    }

    @Test
    void registerAndConnect() throws IOException {
        GestionnaireUtilisateur gestionnaire = new GestionnaireUtilisateur(tmp);
        assertTrue(gestionnaire.register("ismail", "poire"));
        assertEquals(STATUS.OK, gestionnaire.getStatus());
        assertTrue(gestionnaire.connect("ismail", "poire"));
        assertEquals(STATUS.OK, gestionnaire.getStatus());
        tmp.delete();
    }
}