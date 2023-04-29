package com.ulb.infof307.g12.server.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    public void testUser(){
        User user = new User("admin","test");
        assertEquals("admin",user.getUsername());
        assertEquals("test",user.getPassword());
    }

    @Test
    public void testUserConstructor(){
        User user = new User("admin#test");
        assertEquals("admin",user.getUsername());
        assertEquals("test",user.getPassword());

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            User user2 = new User("admin");
        });

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            User user3 = new User("admin#test#test2");
        });
    }

}