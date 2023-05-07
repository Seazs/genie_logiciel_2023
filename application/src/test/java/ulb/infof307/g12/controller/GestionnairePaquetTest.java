package ulb.infof307.g12.controller;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import ulb.infof307.g12.Main;
import ulb.infof307.g12.controller.storage.GestionnairePaquet;
import ulb.infof307.g12.controller.storage.GestionnaireUtilisateur;
import ulb.infof307.g12.model.Carte;
import ulb.infof307.g12.model.CarteQcm;
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
    private static File dossierTemporaire;
    private static File fichierUtilisateur;

    @BeforeAll
    public static void creeDossierTemporaire(){
        dossierTemporaire = new File("./stockageTest");
        dossierTemporaire.mkdir();
        fichierUtilisateur = new File(dossierTemporaire, "stockUser.txt");
    }
    @AfterAll
    public static void supprimeDossierTemporaire(){
        deleteFolder(dossierTemporaire);
    }

    /**
     * Supprime de manière récurssive un dossier et son contenu
     * @param folder folder to delete
     */
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

    @Test
    public void testSauvegardePaquet() throws IOException{
        Utilisateur utilisateur1 = new Utilisateur("alex","pomme");
        Paquet paquet1 = new Paquet("Maths","BA1");
        Carte carte1 = new Carte(1, "divergence = rotationnel ?", "Non");
        Carte carte2 = new Carte(2, "Anne delandsheer ?", "Oui");
        CarteQcm carte3 = new CarteQcm(3, "redox§rep1§rep2§rep3", "Non");
        paquet1.addCard(carte1);
        paquet1.addCard(carte2);
        paquet1.addCard(carte3);
        Paquet paquet2 = new Paquet("Chimie","BA1");
        Carte carte4 = new Carte(3, "redox", "Non");
        Carte carte5 = new Carte(4, "structure quantique de l'atome", "Oui");
        paquet2.addCard(carte4);
        paquet2.addCard(carte5);
        utilisateur1.addPaquet(paquet1);
        utilisateur1.addPaquet(paquet2);
        GestionnaireUtilisateur gestuser = new GestionnaireUtilisateur(fichierUtilisateur);
        gestuser.register(utilisateur1.getPseudo(),utilisateur1.getMdp());
        GestionnairePaquet gestPaquet = new GestionnairePaquet(dossierTemporaire.getPath());
        gestPaquet.save(utilisateur1);
        //Test d'assertion
        File f = new File(dossierTemporaire.getPath()+utilisateur1.getPseudo()+"/Maths.json");
        assertTrue(f.exists());

    }

    @Test
    public void testremove() throws IOException{
        Utilisateur utilisateur1 = new Utilisateur("alex","pomme");
        Paquet paquet1 = new Paquet("Maths","BA1");
        Carte carte1 = new Carte(1, "divergence = rotationnel ?", "Non");
        Carte carte2 = new Carte(2, "Anne delandsheer ?", "Oui");
        paquet1.addCard(carte1);
        paquet1.addCard(carte2);
        Paquet paquet2 = new Paquet("Chimie","BA1");
        Carte carte3 = new Carte(3, "redox", "Non");
        Carte carte4 = new Carte(4, "structure quantique de l'atome", "Oui");
        paquet2.addCard(carte3);
        paquet2.addCard(carte4);
        utilisateur1.addPaquet(paquet1);
        utilisateur1.addPaquet(paquet2);
        GestionnaireUtilisateur gestuser = new GestionnaireUtilisateur();
        gestuser.register(utilisateur1.getPseudo(),utilisateur1.getMdp());
        GestionnairePaquet gestPaquet = new GestionnairePaquet();
        gestPaquet.save(utilisateur1);
        //Test de suppression
        gestPaquet.remove(utilisateur1,paquet1);
        File f = new File("src/main/resources/stockage/"+utilisateur1.getPseudo()+"/"+paquet1.getNom());
        assertFalse(f.exists());
    }

    @Test
    public void testLoadPaquet() throws IOException {
        Utilisateur utilisateur1 = new Utilisateur("alex","pomme");
        Paquet paquet1 = new Paquet("Maths","BA1");
        Carte carte1 = new Carte(1, "divergence = rotationnel ?", "Non");
        Carte carte2 = new Carte(2, "Anne delandsheer ?", "Oui");
        paquet1.addCard(carte1);
        paquet1.addCard(carte2);
        Paquet paquet2 = new Paquet("Chimie","BA1");
        Carte carte3 = new Carte(3, "redox", "Non");
        Carte carte4 = new Carte(4, "structure quantique de l'atome", "Oui");
        paquet2.addCard(carte3);
        paquet2.addCard(carte4);
        utilisateur1.addPaquet(paquet1);
        utilisateur1.addPaquet(paquet2);
        GestionnairePaquet gestPaquet = new GestionnairePaquet();
        GestionnaireUtilisateur gestuser = new GestionnaireUtilisateur();
        gestuser.register(utilisateur1.getPseudo(),utilisateur1.getMdp());
        gestPaquet.save(utilisateur1);
        //Création de l'utilisateur 2 qui va charger l'utilisateur 1
        Utilisateur utilisateur2 = new Utilisateur("alex","pomme");
        utilisateur2.setListPaquet(gestPaquet.load(utilisateur2));
        assertEquals(utilisateur1.getListPaquet().get(0).getCategories(),utilisateur2.getListPaquet().get(0).getCategories());
        assertEquals(utilisateur1.getListPaquet().get(1).getCategories(),utilisateur2.getListPaquet().get(1).getCategories());

    }


}