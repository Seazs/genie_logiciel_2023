package ulb.infof307.g12.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class PaquetTest {



    @Test
    public void testCreationPaquet(){
        //nom, catégorie, cartes, utilisateur

        String nom = "Maths",
                categorie = "BA-1";

        Assertions.assertThrows(IllegalArgumentException.class,() -> {
            Paquet paquet = new Paquet("",categorie);
        });

        Assertions.assertThrows(IllegalArgumentException.class,()  -> {
            Paquet paquet = new Paquet(nom,null);
        });
    }

    @Test
    public void testMemePaquet(){
        Paquet paquet = new Paquet("Maths","BA-1");
        Paquet paquet2 = new Paquet("Maths","BA-1");
        Assertions.assertEquals(true,paquet.equals(paquet2));
    }

    @Test
    public void testAjoutCarte(){
        Paquet paquet = new Paquet("Maths","BA-1");
        Carte carte = new Carte(1, "r1", "v1");
        Carte carte1 = new Carte(4, "r2", "v2");
        paquet.addCard(carte);
        paquet.addCard(carte1);
        Assertions.assertEquals(carte, paquet.cartes.get(0));
        Assertions.assertEquals(carte1, paquet.cartes.get(1));

    }

    @Test
    public void testSupprimerCarte(){
        Paquet paquet = new Paquet("Maths","BA-1");
        Carte carte = new Carte(1, "r1", "v1");
        Carte carte1 = new Carte(4, "r2", "v2");
        paquet.addCard(carte);
        paquet.addCard(carte1);
        paquet.supprimerCarte(carte);
        Assertions.assertEquals(1, paquet.cartes.size());
    }
    @Test
    public void testAjouterCategorie(){
        Paquet paquet = new Paquet("Maths","BA1");
        paquet.addCategory("BA2");
        Paquet paquet2 = new Paquet("Maths", "BA1", "BA2") ;
        Assertions.assertEquals(paquet,paquet2) ;

    }

    @Test
    public void testSupprimerCategorie(){
        Paquet paquet = new Paquet("Maths","BA1", "BA2");
        paquet.supprimerCategorie("BA2");
        Paquet paquet2 = new Paquet("Maths", "BA1") ;
        Assertions.assertEquals(paquet,paquet2) ;
    }

}