package ulb.infof307.g12.storage;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import ulb.infof307.g12.controller.storage.GestionnairePaquet;
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

        String path = dossierTemporaire.getPath() + File.separator + paquet.getNom();
        File fichier = File.createTempFile(path,".ulb");

        assertDoesNotThrow(() -> GestionnairePaquet.save(paquet,fichier));
        Paquet paquet2 = GestionnairePaquet.load(fichier);
        assertEquals(paquet.getCategorie(), paquet2.getCategorie());
    }



}