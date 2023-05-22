package ulb.infof307.g12.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ulb.infof307.g12.controller.storage.UserManager;
import ulb.infof307.g12.model.STATUS;
import ulb.infof307.g12.model.User;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserManagerTest {
    private File tmp;
    @BeforeEach
    void createTmpFile() throws IOException {
        tmp = File.createTempFile("tmp",".txt");
        FileWriter writer = new FileWriter(tmp);
        writer.write("alex#pomme\n");
        writer.write("wassim#orange");
        writer.close();
    }

    @AfterEach
    void deleteTmpFile(){
        tmp.delete();
    }

    @Test
    void testLoad(){
        assertDoesNotThrow(() -> {
            UserManager manager = new UserManager(tmp);
            List<User> list = manager.getListUser();
            assertEquals("alex",list.get(0).getPseudo());
            assertEquals("wassim",list.get(1).getPseudo());
            assertEquals("pomme",list.get(0).getMdp());
            assertEquals("orange",list.get(1).getMdp());
        });
    }

    @Test
    void testEmptyLoad(){
        assertDoesNotThrow(() -> {
            File tmpVide = File.createTempFile("tmpVide",".txt");
            UserManager manager = new UserManager(tmpVide);
            List<User> list = manager.getListUser();
            assertEquals(0,list.size());
            assertTrue( tmpVide.delete());
        });
    }

    @Test void testSave(){
        assertDoesNotThrow(() ->{
            UserManager gest = new UserManager(tmp);
            List<User> list = gest.getListUser();
            User u1 = list.get(0);
            User u2 = list.get(1);
            assertEquals("alex",u1.getPseudo());
            assertEquals("wassim",u2.getPseudo());
            assertEquals("pomme",u1.getMdp());
            assertEquals("orange",u2.getMdp());
        });
    }

    @Test
    void testConnectUserNoValidUsername(){
        assertDoesNotThrow(() -> {
            UserManager manager = new UserManager(tmp);
            assertFalse(manager.connect("Brenno#", "123"));
            assertEquals(STATUS.USERNAME_IS_NOT_VALID,
                    manager.getStatus());
            assertFalse(manager.connect("Brenno Ferreira", "123"));
            assertEquals(STATUS.USERNAME_IS_NOT_VALID,
                    manager.getStatus());
            assertFalse(manager.connect("", "123"));
            assertEquals(STATUS.USERNAME_IS_NOT_VALID,
                    manager.getStatus());
        });
    }
    @Test
    void testConnectUserPasswordNotValid(){
        assertDoesNotThrow(() -> {
            UserManager manager = new UserManager(tmp);
            assertFalse(manager.connect("Brenno", "123#"));
            assertEquals(STATUS.PASSWORD_IS_NOT_VALID,
                    manager.getStatus());
            assertFalse(manager.connect("Brenno", "123 456"));
            assertEquals(STATUS.PASSWORD_IS_NOT_VALID,
                    manager.getStatus());
            assertFalse(manager.connect("Brenno", ""));
            assertEquals(STATUS.PASSWORD_IS_NOT_VALID,
                    manager.getStatus());
        });
    }
    @Test
    void testConnectUserOK(){
        assertDoesNotThrow(() ->{
            UserManager manager = new UserManager(tmp);
            assertTrue(manager.connect("alex", "pomme"));
            assertEquals(STATUS.OK,
                    manager.getStatus());
        });
    }
    @Test
    void testConnectUserNoPseudo(){
        assertDoesNotThrow(() -> {
            UserManager manager = new UserManager(tmp);
            assertFalse(manager.connect("alexxxx", "pomme"));
            assertEquals(STATUS.USERNAME_DOES_NOT_EXIST, manager.getStatus());
        });
    }
    @Test
    void testConnectUserWrongPassword(){
        assertDoesNotThrow(()-> {
            UserManager manager = new UserManager(tmp);
            assertFalse(manager.connect("alex", "pommeeeeee"));
            assertEquals(STATUS.WRONG_PASSWORD, manager.getStatus());
        });
    }

    @Test
    void testRegisterUserPseudoNotValid(){
        assertDoesNotThrow(() -> {
            UserManager manger = new UserManager(tmp);
            assertFalse(manger.register("Brenno#", "123"));
            assertEquals(STATUS.USERNAME_IS_NOT_VALID,
                    manger.getStatus());
            assertFalse(manger.register("Brenno Ferreira", "123"));
            assertEquals(STATUS.USERNAME_IS_NOT_VALID,
                    manger.getStatus());
            assertFalse(manger.register("", "123"));
            assertEquals(STATUS.USERNAME_IS_NOT_VALID,
                    manger.getStatus());
        });
    }
    @Test
    void testRegisterUserPasswordNotValid(){
        assertDoesNotThrow(() -> {
            UserManager manager = new UserManager(tmp);
            assertFalse(manager.register("Brenno", "123#"));
            assertEquals(STATUS.PASSWORD_IS_NOT_VALID,
                    manager.getStatus());
            assertFalse(manager.register("Brenno", "123 456"));
            assertEquals(STATUS.PASSWORD_IS_NOT_VALID,
                    manager.getStatus());
            assertFalse(manager.register("Brenno", ""));
            assertEquals(STATUS.PASSWORD_IS_NOT_VALID,
                    manager.getStatus());
        });
    }

    @Test
    void testRegisterUserExistingUsername(){
        assertDoesNotThrow(() -> {
            UserManager manager = new UserManager(tmp);
            assertFalse(manager.register("alex", "poire"));
            assertEquals(STATUS.USERNAME_DOES_ALREADY_EXIST, manager.getStatus());
        });
    }

    @Test
    void testRegisterUserOK() {
        assertDoesNotThrow(() -> {
            UserManager manager = new UserManager(tmp);
            assertTrue(manager.register("brenno", "raisin"));
            assertEquals(STATUS.OK, manager.getStatus());
        });
    }

    @Test
    void testRegisterAndConnect(){
        assertDoesNotThrow(() -> {
            UserManager manager = new UserManager(tmp);
            assertTrue(manager.register("brenno", "raisin"));
            assertEquals(STATUS.OK, manager.getStatus());
            assertTrue(manager.connect("brenno", "raisin"));
            assertEquals(STATUS.OK, manager.getStatus());
        });
    }

    @Test
    void testRemoveUser(){
        assertDoesNotThrow(() -> {
            UserManager userManager = new UserManager(tmp);
            User user1 = new User("felix","meilleuramipourlavie");
            userManager.register(user1.getPseudo(), user1.getMdp());
            userManager.removeUser(user1);
            //Test
            File f = new File("src/main/resources/stockage/"+user1.getPseudo());
            assertFalse(f.exists());
            assertFalse(userManager.getListUser().contains(user1));
        });
    }
    @Test
    void testDisconnect(){
        assertDoesNotThrow(() -> {
            UserManager userManager = new UserManager(tmp);
            userManager.connect("alex", "pomme");
            assertEquals("alex",UserManager.userConnected.getPseudo());
            userManager.disconnect();
            assertNull(UserManager.userConnected);
        });
    }
    @Test
    void testFindUser(){
        assertDoesNotThrow(() -> {
            UserManager userManager = new UserManager(tmp);
            User user1 = new User("felix","meilleuramipourlavie");
            userManager.register(user1.getPseudo(), user1.getMdp());
            assertEquals(user1.getPseudo(),userManager.findUser(user1.getPseudo()).getPseudo());
            assertEquals(user1.getMdp(),userManager.findUser(user1.getPseudo()).getMdp());
            assertNull(userManager.findUser("1"));
        });
    }
    @Test
    void testchangePassword(){
        assertDoesNotThrow(() -> {
            UserManager userManager = new UserManager(tmp);
            User user1 = new User("felix","meilleuramipourlavie");
            userManager.register(user1.getPseudo(), user1.getMdp());
            userManager.changePassword(user1.getPseudo(),"nouveau","meilleuramipourlavie");
            assertEquals("nouveau",userManager.findUser(user1.getPseudo()).getMdp());
            userManager.changePassword("1","nouveau","meilleuramipourlavie");
            assertEquals(STATUS.USERNAME_DOES_NOT_EXIST,userManager.getStatus());
            userManager.changePassword(user1.getPseudo(),"#","meilleuramipourlavie");
            assertEquals(STATUS.PASSWORD_IS_NOT_VALID,userManager.getStatus());
            userManager.changePassword(user1.getPseudo(),"nouveau","123");
            assertEquals(STATUS.WRONG_PASSWORD,userManager.getStatus());
        });
    }
}