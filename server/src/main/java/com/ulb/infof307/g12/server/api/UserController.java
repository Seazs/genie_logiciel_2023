package com.ulb.infof307.g12.server.api;


import com.ulb.infof307.g12.server.model.STATUS;
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
    @GetMapping(value="login/{username}")
    public ResponseEntity<String> getUser(@PathVariable String username){
        String result = userService.getPassword(username);
        return ResponseEntity.ok(result);
    }

    /**
     * Crée un nouvel utilisateur
     * @param userString l'utilisateur sous forme de string
     */
    @PostMapping("register")
    public ResponseEntity<String> createUser(@RequestBody String userString){
        String result;
        try {
            User user = new User(userString);
            result = userService.createUser(user).getMsg();
        }catch (IllegalArgumentException e){
            result = e.getMessage();
        }
        return ResponseEntity.ok(result);
    }

    /**
     * Met à jour un utilisateur
     * @param user l'utilisateur
     */
    @PatchMapping("changepassword")
    public void updateUser(@RequestBody User user){
        userService.updateUser(user);
    }
}
