package ulb.infof307.g12.controller;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ulb.infof307.g12.Main;
import ulb.infof307.g12.controller.storage.PaquetManager;
import ulb.infof307.g12.controller.storage.UserManager;
import ulb.infof307.g12.model.*;
import ulb.infof307.g12.model.Card;
import ulb.infof307.g12.model.CardQcm;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class PaquetManagerTest {

    private static File dossierTemporaire;
    private static File fichierUtilisateur;

    @BeforeAll
    public static void creeDossierTemporaire() throws IOException {
        dossierTemporaire = new File("./stockageTest");
        dossierTemporaire.mkdir();
        fichierUtilisateur = new File(dossierTemporaire, "stockUser.txt");
        fichierUtilisateur.createNewFile();
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
    public void testSavePaquet() throws IOException{
        User user1 = new User("testUtilisateur","test");
        Paquet paquet1 = new Paquet("Maths","BA1");
        Card card1 = new Card(1, "divergence = rotationnel ?", "Non");
        Card card2 = new Card(2, "Anne delandsheer ?", "Oui");
        CardQcm carte3 = new CardQcm(3, "redox§rep1§rep2§rep3", "Non");
        paquet1.addCard(card1);
        paquet1.addCard(card2);
        paquet1.addCard(carte3);
        Paquet paquet2 = new Paquet("Chimie","BA1");
        Card card4 = new Card(3, "redox", "Non");
        Card card5 = new Card(4, "structure quantique de l'atome", "Oui");
        paquet2.addCard(card4);
        paquet2.addCard(card5);
        user1.addPaquet(paquet1);
        user1.addPaquet(paquet2);
        UserManager gestuser = new UserManager(fichierUtilisateur, dossierTemporaire.getPath()+ "/");
        gestuser.register(user1.getPseudo(), user1.getMdp());
        PaquetManager gestPaquet = new PaquetManager(dossierTemporaire.getPath()+ "/");
        gestPaquet.savePaquet(user1,paquet1);
        gestPaquet.savePaquet(user1,paquet2);
        File f = new File(dossierTemporaire + "/" + user1.getPseudo()+ "/"+ paquet1.getId()+".json");
        File f2 = new File(dossierTemporaire + "/" + user1.getPseudo()+ "/"+ paquet2.getId()+".json");

        assertTrue(f.exists());
        assertTrue(f2.exists());
        f.delete();
        f2.delete();
    }

    @Test
    public void testRemove() throws IOException{
        User user1 = new User("alex","pomme");
        Paquet paquet1 = new Paquet("Maths","BA1");
        Card card1 = new Card(1, "divergence = rotationnel ?", "Non");
        Card card2 = new Card(2, "Anne delandsheer ?", "Oui");
        paquet1.addCard(card1);
        paquet1.addCard(card2);
        Paquet paquet2 = new Paquet("Chimie","BA1");
        Card card3 = new Card(3, "redox", "Non");
        Card card4 = new Card(4, "structure quantique de l'atome", "Oui");
        paquet2.addCard(card3);
        paquet2.addCard(card4);
        user1.addPaquet(paquet1);
        user1.addPaquet(paquet2);
        UserManager gestuser = new UserManager(fichierUtilisateur, dossierTemporaire.getPath()+ "/");
        gestuser.register(user1.getPseudo(), user1.getMdp());
        PaquetManager gestPaquet = new PaquetManager(dossierTemporaire.getPath() + "/");
        gestPaquet.save(user1);
        //Test de suppression
        gestPaquet.remove(user1,paquet1);
        File f = new File(dossierTemporaire + "/" + user1.getPseudo()+"/"+paquet1.getNom());
        assertFalse(f.exists());
    }

    @Test
    public void testLoadPaquet() throws IOException {
        User user1 = new User("alex","pomme");
        Paquet paquet1 = new Paquet("Maths","BA1");
        Card card1 = new Card(1, "divergence = rotationnel ?", "Non");
        Card card2 = new Card(2, "Anne delandsheer ?", "Oui");
        paquet1.addCard(card1);
        paquet1.addCard(card2);
        Paquet paquet2 = new Paquet("Chimie","BA1");
        Card card3 = new Card(3, "redox", "Non");
        Card card4 = new Card(4, "structure quantique de l'atome", "Oui");
        paquet2.addCard(card3);
        paquet2.addCard(card4);
        user1.addPaquet(paquet1);
        user1.addPaquet(paquet2);
        PaquetManager gestPaquet = new PaquetManager(dossierTemporaire.getPath()+ "/");
        UserManager gestuser = new UserManager(fichierUtilisateur, dossierTemporaire.getPath()+ "/");
        gestuser.register(user1.getPseudo(), user1.getMdp());
        gestPaquet.save(user1);
        //Création de l'utilisateur 2 qui va charger l'utilisateur 1
        User user2 = new User("alex","pomme");
        user2.setListPaquet(gestPaquet.load(user2));
        assertEquals(user1.getListPaquet().get(0).getCategories(), user2.getListPaquet().get(0).getCategories());
        assertEquals(user1.getListPaquet().get(1).getCategories(), user2.getListPaquet().get(1).getCategories());

    }

}