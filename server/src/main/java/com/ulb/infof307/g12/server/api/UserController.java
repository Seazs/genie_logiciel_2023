package com.ulb.infof307.g12.server.api;


import com.ulb.infof307.g12.server.model.User;
import com.ulb.infof307.g12.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

//@RequestMapping("api/v1/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Renvoie l'utilisateur correspondant à l'id
     * @param username le pseudo
     * @param password le mot de passe
     * @return l'utilisateur correspondant à l'id ou null s'il n'existe pas
     */
    @RequestMapping(method = RequestMethod.GET,value="api/v1/user/{username}/{password}/login")
    public ResponseEntity<Boolean> getUser(@PathVariable String username, @PathVariable String password){
        System.out.println("Request for user: "+username+" Pass: "+password);
        Boolean result = userService.getUser(username,password).isPresent();
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
