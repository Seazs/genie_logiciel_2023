package com.ulb.infof307.g12.server.api;


import com.ulb.infof307.g12.server.model.STATUS;
import com.ulb.infof307.g12.server.model.User;
import com.ulb.infof307.g12.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


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
     * Renvoie la liste des utilisateurs
     * @return le status de la dernière opération
     */
    @GetMapping
    public List<User> getAllUsers(){
        //!! ATTENTION: RENVOI LA LISTE DES UTILISATEURS AVEC SES NOMS
        // D'UTILISATEURS ET LEURS MOTS DE PASSES.
        return userService.getAllUsers();
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
     * Change le mot de passe d'un utilisateur
     * @param password le nouveau mot de passe
     * @param username le pseudo de l'utilisateur
     * @return le statut de la requête
     */
    @PostMapping("changepassword/{username}")
    public ResponseEntity<String> updateUser(@RequestBody String password, @PathVariable String username){
        User user = new User(username, password);
        STATUS result = userService.updateUser(user);
        return ResponseEntity.ok(result.getMsg());
    }
    @DeleteMapping("delete/{username}")
    public ResponseEntity<String> deleteUser(@PathVariable String username){
        STATUS result = userService.deleteUser(username);
        return ResponseEntity.ok(result.getMsg());
    }

}
