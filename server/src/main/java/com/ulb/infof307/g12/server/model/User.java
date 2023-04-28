package com.ulb.infof307.g12.server.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class User {
    private String username;
    private String password;

    public boolean equals(String username, String password){
        return this.username.equals(username) && this.password.equals(password);
    }
}
