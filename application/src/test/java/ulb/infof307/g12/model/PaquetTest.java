package ulb.infof307.g12.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ulb.infof307.g12.view.dto.PaquetDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
    public void testCreationPaquet2(){
        //nom, catégorie, cartes, utilisateur

        String nom = "Maths";
        List<String> categorie = new ArrayList<>();
        categorie.add("BA-1");
        List<Card> cards = new ArrayList<>();
        cards.add(new Card(1,"r1","v1"));
        UUID id = UUID.randomUUID();
        Assertions.assertThrows(IllegalArgumentException.class,() -> {
            Paquet paquet = new Paquet(id,"",categorie, cards);
        });

        Assertions.assertThrows(IllegalArgumentException.class,()  -> {
            Paquet paquet = new Paquet(id,null,categorie, cards);
        });
        Assertions.assertThrows(IllegalArgumentException.class,()  -> {
            Paquet paquet = new Paquet(id,nom,null, cards);
        });

    }

    public static List<Arguments> creatPaquetComparaison(){
        Paquet paquet = new Paquet("Maths","BA-1");
        Paquet paquet2 = new Paquet("Maths","BA-1");
        Paquet paquet3 = new Paquet("test","BA-1");
        String paquet4 = "test";
        List<Arguments> arguments = new ArrayList<>();
        arguments.add(Arguments.of(paquet,paquet2,true));
        arguments.add(Arguments.of(paquet,paquet3,false));
        arguments.add(Arguments.of(paquet,paquet4,false));
        return arguments;
    }

    @ParameterizedTest
    @MethodSource("creatPaquetComparaison")
    public void testMemePaquet(Paquet paquet, Object paquet2, boolean expected){
        Assertions.assertEquals(expected,paquet.equals(paquet2));
    }

    @Test
    public void testAjoutCarte(){
        Paquet paquet = new Paquet("Maths","BA-1");
        Card card = new Card(1, "r1", "v1");
        Card card1 = new Card(4, "r2", "v2");
        paquet.addCard(card);
        paquet.addCard(card1);
        Assertions.assertEquals(card, paquet.cards.get(0));
        Assertions.assertEquals(card1, paquet.cards.get(1));
    }

    @Test
    public void testAjoutMemeCarte(){
        Paquet paquet = new Paquet("Maths","BA-1");
        Card card = new Card(1, "r1", "v1");
        paquet.addCard(card);
        Assertions.assertThrows(IllegalArgumentException.class,()  -> {
            paquet.addCard(card);
        });
    }
    public static List<Arguments> creatPaquet(){
        Paquet paquet = new Paquet("Maths","BA-1");
        Card card = new Card(1, "r1", "v1");
        Card card1 = new Card(4, "r2", "v2");
        paquet.addCard(card);
        paquet.addCard(card1);
        List<Arguments> arguments = new ArrayList<>();
        arguments.add(Arguments.of(paquet, card));
        arguments.add(Arguments.of(paquet, card1));
        arguments.add(Arguments.of(paquet,new Card(5, "r3", "v3")));
        return arguments;
    }
    @ParameterizedTest
    @MethodSource("creatPaquet")
    public void testSupprimeCarte(Paquet paquet, Card card){
        if (!paquet.cards.contains(card)){
            Assertions.assertThrows(IllegalArgumentException.class,()  -> {
                paquet.deleteCard(card);
            });
        }
        else {
            paquet.deleteCard(card);
            Assertions.assertEquals(false,paquet.cards.contains(card));
        }
    }
    @Test
    public void testAjouterCategorie(){
        Paquet paquet = new Paquet("Maths","BA1");
        paquet.addCategory("BA2");
        Paquet paquet2 = new Paquet("Maths", "BA1", "BA2") ;
        Assertions.assertEquals(paquet,paquet2) ;
        Assertions.assertThrows(IllegalArgumentException.class,()  -> {
            paquet.addCategory("#");
        });
    }

    @Test
    public void DTOTest(){
        UUID id = UUID.randomUUID();
        List<String> categories = new ArrayList<>();
        categories.add("BA-1");
        List<Card> cards = new ArrayList<>();
        cards.add(new Card(1,"r1","v1"));
        Paquet paquet = new Paquet(id,"nom",categories, cards);
        PaquetDTO paquetDTO = new PaquetDTO(id.toString(),"nom",categories);
        Assertions.assertEquals(paquetDTO,paquet.getDTO());
    }


}