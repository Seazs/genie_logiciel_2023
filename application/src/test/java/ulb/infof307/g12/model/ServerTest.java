package ulb.infof307.g12.model;

import com.ulb.infof307.g12.server.ServerApplication;
import com.ulb.infof307.g12.server.model.User;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.File;
import java.io.IOException;

class ServerTest {

    private static ConfigurableApplicationContext context;
    private final Server server = new Server();
    @BeforeEach
    public void startServer() {
        context = SpringApplication.run(ServerApplication.class);

    }


    @Test
    public void testCreateUser() {
        //ServerApplication.main(new String[]{});
        User user = new User("test","test");
        User user2 = new User("#","#");
        String reponse = server.createUser(user.getUsername(), user.getPassword());
        String reponse2 = server.createUser(user2.getUsername(), user2.getPassword());
        System.out.println(reponse);
        //assertEquals(reponse2, "PAS OK");

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