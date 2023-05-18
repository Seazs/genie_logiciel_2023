package com.ulb.infof307.g12.server.dao;

import com.ulb.infof307.g12.server.model.STATUS;
import com.ulb.infof307.g12.server.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserDataAccessServiceTest {
    private File tmp;
    @BeforeEach
    void createTmpFile() throws IOException {
        tmp = new File("server/src/main/resources/stockage","stockUser.txt");
        tmp.getParentFile().mkdirs();
        tmp.createNewFile();
        FileWriter writer = new FileWriter(tmp);
        writer.write("t#t");
        writer.close();
    }

    @AfterEach
    void deleteTmpFile(){
        tmp.delete();
    }

    @Test
    void testUpdateGetUser() {
        User user = new User("test","test");
        User user2 = new User("test2","test2");
        User user3 = new User("test3","test3");
        UserDataAccessService userDataAccessService = new UserDataAccessService();
        userDataAccessService.createUser(user);
        userDataAccessService.createUser(user2);
        userDataAccessService.createUser(user3);
        UserDataAccessService userDataAccessService2 = new UserDataAccessService();
        assertEquals("test",userDataAccessService2.getPassword("test"));
        assertEquals("test2",userDataAccessService2.getPassword("test2"));
        assertEquals("test3",userDataAccessService2.getPassword("test3"));
    }
    @Test
    void testCreateUser() {
        User user = new User("test","test");
        UserDataAccessService userDataAccessService = new UserDataAccessService();
        assertEquals(STATUS.OK,userDataAccessService.createUser(user));
        assertEquals("test",userDataAccessService.getPassword("test"));
    }

    @Test
    void testUpdateUser() {
        User user = new User("123","123");
        User user2 = new User("123","456");
        UserDataAccessService userDataAccessService = new UserDataAccessService();
        userDataAccessService.createUser(user);
        userDataAccessService.updateUser(user2);
        assertEquals("456",userDataAccessService.getPassword("123"));
    }

    @Test
    void testSameUser(){
        User user = new User("123","123");
        User user2 = new User("123","456");
        UserDataAccessService userDataAccessService = new UserDataAccessService();
        userDataAccessService.createUser(user);
        assertEquals(STATUS.USERNAME_DOES_ALREADY_EXIST,userDataAccessService.createUser(user2));
    }

    @Test
    void testUpdateNotExistingUser(){
        User user = new User("123","123");
        UserDataAccessService userDataAccessService = new UserDataAccessService();
        assertEquals(STATUS.USERNAME_DOES_NOT_EXIST,userDataAccessService.updateUser(user));
    }


    @Test
    void testLoadFichierErronee() throws IOException {
        File tmp = new File("server/src/main/resources/stockage","stockUser.txt");
        FileWriter myWriter = new FileWriter(tmp);
        myWriter.write("test");
        myWriter.close();
        UserDataAccessService userDataAccessService = new UserDataAccessService();
        assertEquals(STATUS.DB_COULD_NOT_BE_LOADED,userDataAccessService.status);
        tmp.delete();
    }
}