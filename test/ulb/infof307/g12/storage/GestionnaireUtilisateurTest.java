package ulb.infof307.g12.storage;

import org.junit.jupiter.api.Test;
import ulb.infof307.g12.model.Utilisateur;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GestionnaireUtilisateurTest {

    @Test
    void loadTest() throws IOException {
        File tmp = File.createTempFile("tmp",".txt");
        FileWriter writer = new FileWriter(tmp);
        writer.write("alex#pomme\n");
        writer.write("wassim#orange");
        writer.close();
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
        File tmp = File.createTempFile("tmp",".txt");
        GestionnaireUtilisateur gest = new GestionnaireUtilisateur(tmp);
        List<Utilisateur> list = gest.getListeUtilisateur();
        assertEquals(0,list.size());
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
        File tmp = File.createTempFile("tmp",".txt");
        FileWriter writer = new FileWriter(tmp);
        writer.write("alex#pomme\n");
        writer.write("wassim#orange");
        writer.close();
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
}