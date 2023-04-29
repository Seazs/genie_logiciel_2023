package com.ulb.infof307.g12.server.api;


import com.ulb.infof307.g12.server.model.User;
import com.ulb.infof307.g12.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RequestMapping("api/v1/user")
@RestController
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Renvoie l'utilisateur correspondant à l'id
     * @param username le pseudo
     * @return l'utilisateur correspondant à l'id ou null s'il n'existe pas
     */
    @GetMapping(value="{username}")
    public ResponseEntity<String> getUser(@PathVariable String username){
        String result = userService.getPassword(username);
        return ResponseEntity.ok(result);
    }

    /**
     * Crée un nouvel utilisateur
     * @param user l'utilisateur
     */
    @PostMapping
    public void createUser(@RequestBody User user){
        userService.createUser(user);
    }
}
