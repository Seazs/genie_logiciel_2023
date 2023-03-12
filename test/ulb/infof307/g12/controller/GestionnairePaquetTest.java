package ulb.infof307.g12.controller;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import ulb.infof307.g12.model.Carte;
import ulb.infof307.g12.model.Paquet;
import ulb.infof307.g12.model.Utilisateur;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.*;

class GestionnairePaquetTest {

    /*
        - dossier "stockage"
            -Fichier stockUser
            - <utilisateur>
                - fichier <paquet1>
                - fichier <paquet2>
         */

    @TempDir
    private static File dossierTemporaire;

    @BeforeAll
    public static void creeDossierTemporaire(){
        try {
            dossierTemporaire = Files.createTempDirectory("Paquets").toFile();
            System.out.println(dossierTemporaire.getPath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @AfterAll
    public static void supprimeDossierTemporaire(){
        dossierTemporaire.delete();
    }

    @Test
    public void testSauvegardePaquet() throws IOException{
        Utilisateur utilisateur1 = new Utilisateur("alex","pomme");
        Paquet paquet1 = new Paquet("Maths","BA-1");
        Carte carte1 = new Carte(1, "divergence = rotationnel ?", "Non");
        Carte carte2 = new Carte(2, "Anne delandsheer ?", "Oui");
        paquet1.ajouterCarte(carte1);
        paquet1.ajouterCarte(carte2);
        Paquet paquet2 = new Paquet("Chimie","BA-1");
        Carte carte3 = new Carte(3, "redox", "Non");
        Carte carte4 = new Carte(4, "structure quantique de l'atome", "Oui");
        paquet2.ajouterCarte(carte3);
        paquet2.ajouterCarte(carte4);
        utilisateur1.addPaquet(paquet1);
        utilisateur1.addPaquet(paquet2);
        GestionnaireUtilisateur gestuser = new GestionnaireUtilisateur();
        gestuser.register(utilisateur1.getPseudo(),utilisateur1.getMdp());
        GestionnairePaquet gestPaquet = new GestionnairePaquet();
        gestPaquet.save(utilisateur1);
        //Test d'assertion
        File f = new File("./stockage/"+utilisateur1.getPseudo()+"/"+"Maths");
        assertTrue(f.exists());

    }

    @Test
    public void testremove() throws IOException{
        Utilisateur utilisateur1 = new Utilisateur("alex","pomme");
        Paquet paquet1 = new Paquet("Maths","BA-1");
        Carte carte1 = new Carte(1, "divergence = rotationnel ?", "Non");
        Carte carte2 = new Carte(2, "Anne delandsheer ?", "Oui");
        paquet1.ajouterCarte(carte1);
        paquet1.ajouterCarte(carte2);
        Paquet paquet2 = new Paquet("Chimie","BA-1");
        Carte carte3 = new Carte(3, "redox", "Non");
        Carte carte4 = new Carte(4, "structure quantique de l'atome", "Oui");
        paquet2.ajouterCarte(carte3);
        paquet2.ajouterCarte(carte4);
        utilisateur1.addPaquet(paquet1);
        utilisateur1.addPaquet(paquet2);
        GestionnaireUtilisateur gestuser = new GestionnaireUtilisateur();
        gestuser.register(utilisateur1.getPseudo(),utilisateur1.getMdp());
        GestionnairePaquet gestPaquet = new GestionnairePaquet();
        gestPaquet.save(utilisateur1);
        //Test de suppression
        gestPaquet.remove(utilisateur1,paquet1);
        File f = new File("./stockage/"+utilisateur1.getPseudo()+"/"+paquet1.getNom());
        assertFalse(f.exists());
    }

    @Test
    public void testLoadPaquet() throws IOException {
        Utilisateur utilisateur1 = new Utilisateur("alex","pomme");
        Paquet paquet1 = new Paquet("Maths","BA-1");
        Carte carte1 = new Carte(1, "divergence = rotationnel ?", "Non");
        Carte carte2 = new Carte(2, "Anne delandsheer ?", "Oui");
        paquet1.ajouterCarte(carte1);
        paquet1.ajouterCarte(carte2);
        Paquet paquet2 = new Paquet("Chimie","BA-1");
        Carte carte3 = new Carte(3, "redox", "Non");
        Carte carte4 = new Carte(4, "structure quantique de l'atome", "Oui");
        paquet2.ajouterCarte(carte3);
        paquet2.ajouterCarte(carte4);
        utilisateur1.addPaquet(paquet1);
        utilisateur1.addPaquet(paquet2);
        GestionnairePaquet gestPaquet = new GestionnairePaquet();
        GestionnaireUtilisateur gestuser = new GestionnaireUtilisateur();
        gestuser.register(utilisateur1.getPseudo(),utilisateur1.getMdp());
        gestPaquet.save(utilisateur1);
        //Création de l'utilisateur 2 qui va charger l'utilisateur 1
        Utilisateur utilisateur2 = new Utilisateur("alex","pomme");
        utilisateur2.setListPaquet(gestPaquet.load(utilisateur2));
        assertEquals(utilisateur1.getListPaquet().get(0).getCategorie(),utilisateur2.getListPaquet().get(0).getCategorie());
        assertEquals(utilisateur1.getListPaquet().get(1).getCategorie(),utilisateur2.getListPaquet().get(1).getCategorie());
    }


}