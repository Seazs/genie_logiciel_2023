package ulb.infof307.g12.storage;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import ulb.infof307.g12.model.Paquet;

import java.io.File;
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
    private static File dossierTemporaire = new File("tests/paquets");

    @BeforeAll
    public static void creeDossierTemporaire(){
        dossierTemporaire.mkdirs();
    }

    @Test
    public void testSauvegardePaquet(){

        Paquet paquet = new Paquet("Maths","BA-1");

        assertDoesNotThrow(() -> GestionnairePaquet.save(paquet,dossierTemporaire));

        assertTrue(
                Arrays.stream(Objects.requireNonNull(dossierTemporaire.listFiles()))
                        .anyMatch(file -> file.getName().equals("Maths.ulb"))
        );

    }

    @Test
    public void testSupprimePaquet(){
        Paquet paquet = new Paquet("Maths","BA-1");

        assertDoesNotThrow(() -> GestionnairePaquet.save(paquet,dossierTemporaire));
        assertDoesNotThrow(() -> GestionnairePaquet.remove(paquet,dossierTemporaire));


        assertFalse(
                Arrays.stream(Objects.requireNonNull(dossierTemporaire.listFiles()))
                        .anyMatch(file -> file.getName().equals("Maths.ulb"))
        );

    }

    @Test
    public void testChargementPaquet(){
        Paquet paquet = new Paquet("Maths","BA-1");

        assertDoesNotThrow(() -> GestionnairePaquet.save(paquet,dossierTemporaire));
        Paquet paquet2 = GestionnairePaquet.load("Maths",dossierTemporaire);
        assertEquals(paquet, paquet2);
    }



}