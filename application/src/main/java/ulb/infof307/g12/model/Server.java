package ulb.infof307.g12.model;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import ulb.infof307.g12.controller.javafx.connexion.MenuPrincipal;
import ulb.infof307.g12.view.dto.PaquetDTO;

import java.io.IOException;
import java.util.UUID;

/**
 * Class permettant de envoyer/recevoir les informations d'un serveur
 */
public class Server {
    private String url = "http://localhost:8080/api/v1/";

    /**
     * Envoie une requète GET au serveur et renvoie les données
     * @return les données du serveur
     */
    public JSONArray getPaquets(){
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity(url+"paquet", String.class);
        String responseBody = response.getBody();
        System.out.println(responseBody);
        JSONArray jsonArrayListPaquet = new JSONArray(responseBody);
       return jsonArrayListPaquet;
    }

    /**
     * Envoie un paquet (avec juste le nom) au serveur
     * @param paquet paquet à envoyer
     * @return l'état de la demande ("200 OK" si tout s'est bien passé)
     */

    @PostMapping("/paquet")
    public STATUS postPaquet(Paquet paquet) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(paquet);
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(json, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(url+"paquet", entity, String.class);
        String result = response.getBody();
        return STATUS.valueOf(result);
    }

    /**
     * Envoie une requète DELETE au serveur pour supprimer un paquet
     * @param id l'id du paquet à supprimer
     */
    @DeleteMapping("/paquet/{id}")
    public void deletePaquet(@PathVariable UUID id){
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.delete(url + "paquet/" + id);
    }


    /**
     * Envoie une requète GET au serveur pour authentifier un utilisateur.
     * @param username le pseudo de l'utilisateur
     * @return le statut de la requète
     */
    public STATUS getLogin(String username,String password){
        RestTemplate restTemplate = new RestTemplate();
        try {
            ResponseEntity<String> response = restTemplate.getForEntity(url + "user/login/" + username, String.class);
            String result = response.getBody();
            if(result.equals(password))
                return STATUS.OK;
            else
                return STATUS.WRONG_PASSWORD;
        }catch (HttpClientErrorException | NullPointerException e){
            return STATUS.USERNAME_DOES_NOT_EXIST;
        }catch (ResourceAccessException e){
            MenuPrincipal.getINSTANCE().showErrorPopup("Erreur lors de la connexion au serveur");
            return STATUS.SERVER_ERROR;
        }
    }

    /**
     * Envoie une requète POST au serveur pour créer un utilisateur.
     * @param username le pseudo de l'utilisateur
     * @param password le mot de passe de l'utilisateur
     * @return le statut de la requète
     */
    public String createUser(String username, String password){
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_PLAIN);

        HttpEntity<String> entity = new HttpEntity<>(username+"#"+password, headers);
        try {
            ResponseEntity<String> response = restTemplate.postForEntity(url+"user/register", entity, String.class);
            if (response.getStatusCode()== HttpStatusCode.valueOf(200)){
                return response.getBody();
            }
            else{
                MenuPrincipal.getINSTANCE().showErrorPopup("Erreur lors de la création de l'utilisateur");
                return "error";

            }

        }catch (HttpClientErrorException | ResourceAccessException e){
            MenuPrincipal.getINSTANCE().showErrorPopup("Erreur lors de la création de l'utilisateur");
            return "Erreur lors de la création de l'utilisateur";
        }



    }

    /**
     * Envoie une requète POST au serveur pour changer le mot de passe d'un utilisateur.
     * @param username le pseudo de l'utilisateur
     * @param newPassword le nouveau mot de passe
     * @return le statut de la requète
     */
    public String changeUserPassword(String username, String newPassword){
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_PLAIN);

        HttpEntity<String> entity = new HttpEntity<>(newPassword, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(url+"user/changepassword/"+username, entity, String.class);
        return response.getBody();
    }

}
