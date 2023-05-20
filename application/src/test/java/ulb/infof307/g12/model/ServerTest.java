package ulb.infof307.g12.model;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.ulb.infof307.g12.server.ServerApplication;
import com.ulb.infof307.g12.server.model.User;
import org.apache.commons.io.FileUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

class ServerTest {

    private static ConfigurableApplicationContext context;
    private final Server server = new Server();
    @BeforeEach
    public void startServer() {
        context = SpringApplication.run(ServerApplication.class);
    }


    @Test
    public void testCreateUser() {
        server.deleteUser("champimignon");
        User user = new User("champimignon","oui");
        String reponse = server.createUser(user.getUsername(), user.getPassword());
        assertEquals(STATUS.OK.getMsg(),reponse);

        String reponse2 = server.createUser(user.getUsername(), user.getPassword());
        assertEquals(STATUS.USERNAME_DOES_ALREADY_EXIST.getMsg(),reponse2);


        User user2 = new User("#","#");
        String reponse3 = server.createUser(user2.getUsername(), user2.getPassword());
        assertEquals("Nombre d'arguments détectés invalide !",reponse3);

        User user3 = new User("  ","");
        String reponse4 = server.createUser(user3.getUsername(), user3.getPassword());
        assertEquals("Nombre d'arguments détectés invalide !",reponse4);
    }

    @Test
    public void testGetLogin(){
        User user = new User("test","test");
        server.createUser(user.getUsername(), user.getPassword());
        STATUS reponse = server.getLogin(user.getUsername(), user.getPassword());
        assertEquals(STATUS.OK,reponse);
        STATUS reponse2 = server.getLogin(user.getUsername(), "test2");
        assertEquals(STATUS.WRONG_PASSWORD,reponse2);
        STATUS reponse3 = server.getLogin("test2", user.getPassword());
        assertEquals(STATUS.USERNAME_DOES_NOT_EXIST,reponse3);
    }

    @Test
    public void testPostPaquet() {
        Paquet paquet = new Paquet("test","test");
        paquet.addCard(new Card(1,"test", "test"));
        paquet.addCard(new CardQcm(2,"test", "test"));
        try {
            STATUS reponse = server.postPaquet(paquet);
            assertEquals(STATUS.OK,reponse);
            STATUS reponse2 = server.postPaquet(paquet);
            assertEquals("Le paquet est déjà sur le store !", reponse2.getMsg());
        }catch (Exception e){
            System.out.println("Erreur lors de l'envoi du paquet");
        }
    }


    @Test
    public void testGetPaquet() {
        ObjectMapper objectMapper = new ObjectMapper();
        Paquet paquet = new Paquet("test","test");
        paquet.addCard(new Card(1,"test", "test"));
        paquet.addCard(new CardQcm(2,"test", "test"));

        Paquet paquet2 = new Paquet("test2","test2");
        paquet2.addCard(new Card(1,"test2", "test2"));
        paquet2.addCard(new CardQcm(2,"test2", "test2"));
        ArrayList<Paquet> sentPaquets = new ArrayList<>();
        ArrayList<Paquet> receivedPaquets = new ArrayList<>();
        try {
            server.postPaquet(paquet);
            server.postPaquet(paquet2);
            JSONArray reponse = server.getPaquets();
            for (int i = 0; i < reponse.length(); i++) {
                JSONObject paquetRetour = reponse.getJSONObject(i);
                Paquet newPaquet = objectMapper.readValue(paquet.toString(), Paquet.class);
                receivedPaquets.add(newPaquet);
            }
            assertEquals(sentPaquets,receivedPaquets);
        }catch (Exception e){
            System.out.println("Erreur lors de l'envoi du paquet");
        }
    }

    @AfterEach
    public void stopServer() {
        context.stop();
        //supprime le dossier de stockage
        try {
            FileUtils.deleteDirectory(new File("server"));
        } catch (IOException e) {
            System.out.println("Erreur lors de la suppression du dossier de stockage");
        }
    }
}