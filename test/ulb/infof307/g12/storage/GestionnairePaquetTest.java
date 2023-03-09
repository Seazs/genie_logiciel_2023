package ulb.infof307.g12.storage;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import ulb.infof307.g12.controller.storage.GestionnairePaquet;
import ulb.infof307.g12.model.Carte;
import ulb.infof307.g12.model.Paquet;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class GestionnairePaquetTest {

    /*
        - dossier de stockage
            - <utilisateur>
                - fichier login
                - fichier <decks>


                branch -> travail -> commits
                travail fini: merge {main sur ta branche} -> résoud les conflits -> merge{ ta branche sur main}
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

        Paquet paquet = new Paquet("Maths","BA-1");
        Carte carte1 = new Carte(1, "1", "1");
        Carte carte2 = new Carte(2, "2", "2");
        paquet.ajouterCarte(carte1);
        paquet.ajouterCarte(carte2);

        String path = dossierTemporaire.getPath() + File.separator + paquet.getNom();
        File fichier = File.createTempFile(path,".ulb");

        assertDoesNotThrow(() -> GestionnairePaquet.save(paquet,fichier));


        System.out.println(dossierTemporaire.listFiles().toString());
        assertTrue(
                fichier.exists()
        );
        assertTrue(fichier.getName().contains("Maths"));

    }

    @Test
    public void testSupprimePaquet() throws IOException{
        Paquet paquet = new Paquet("Maths","BA-1");

        String path = dossierTemporaire.getPath() + File.separator + paquet.getNom();
        File fichier = File.createTempFile(path,".ulb");

        assertDoesNotThrow(() -> GestionnairePaquet.save(paquet,fichier));
        assertDoesNotThrow(() -> GestionnairePaquet.remove(paquet,fichier));


        assertFalse(
                Arrays.stream(Objects.requireNonNull(dossierTemporaire.listFiles()))
                        .anyMatch(file -> file.getName().equals("Maths.ulb"))
        );

    }

    @Test
    public void testChargementPaquet() throws IOException {
        Paquet paquet = new Paquet("Maths","BA-1");
        Carte carte1 = new Carte (1, "1r", "1v");
        Carte carte2 = new Carte (2, "2r", "2v");

        paquet.ajouterCarte(carte1);
        paquet.ajouterCarte(carte2);

        String path = dossierTemporaire.getPath() + File.separator + paquet.getNom();
        File fichier = File.createTempFile(path,".ulb");

        assertDoesNotThrow(() -> GestionnairePaquet.save(paquet,fichier)); // Sauvegarde le paquet
        Paquet paquet2 = GestionnairePaquet.load(fichier); // Charge le paquet dans un nouvel objet
        assertEquals(paquet.getCategorie(), paquet2.getCategorie());
        assertEquals(paquet.cartes.get(0).recto, paquet2.cartes.get(0).recto);
        assertEquals(paquet.cartes.get(0).verso, paquet2.cartes.get(0).verso);
        assertEquals(paquet.cartes.get(1).recto, paquet2.cartes.get(1).recto);
        assertEquals(paquet.cartes.get(1).verso, paquet2.cartes.get(1).verso);

    }


}