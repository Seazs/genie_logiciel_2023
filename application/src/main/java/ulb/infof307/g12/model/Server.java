package ulb.infof307.g12.model;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

import java.io.IOException;
import java.util.UUID;
import java.util.List;

/**
 * Class permettant d'envoyer/recevoir les informations d'un serveur
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
        return new JSONArray(responseBody);
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
    @DeleteMapping("/paquet")
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
            return STATUS.SERVER_CONNEXION_ERROR;
        }
    }

    /**
     * Envoie une requête POST au serveur pour créer un utilisateur.
     * @param username le pseudo de l'utilisateur
     * @param password le mot de passe de l'utilisateur
     * @return le statut de la requête
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
                return STATUS.SERVER_CREATION_ERROR.toString();
            }

        }catch (HttpClientErrorException | ResourceAccessException e){
            return STATUS.SERVER_CONNEXION_ERROR.toString();
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

    public String deleteUser(String username) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_PLAIN);
        HttpEntity<String> entity = new HttpEntity<>(username, headers);
        restTemplate.delete(url+"user/delete/"+username);
        return entity.getBody();
    }
    /**
     * Envoie une requète POST au serveur pour stocker les paquets d'un utilisateur.
     * @param paquets  liste des paquets à envoyer
     * @param username nom de l'utilisateur
     * @throws IOException exception
     */
    public void sendPaquetUser(List<Paquet> paquets, String username) throws IOException {
        Map<String, Object> map = new HashMap<>();
        map.put("paquets", paquets);
        map.put("username", username);
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(map);
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(json, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(url+"paquet/sync", entity, String.class);
        String result = response.getBody();
        STATUS.valueOf(result);
    }

    /**
     * Envoie une requête GET au serveur pour récupérer les paquets de l'utilisateur.
     * @param username nom de l'utilisateur
     * @return liste des paquets de l'utilisateur
     * @throws IOException s'il y a une erreur de connexion
     */
    public List<Paquet> getPaquetsUser(String username) throws IOException {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity(url+"paquet/sync/"+username, String.class);
        String responseBody = response.getBody();
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(responseBody, new TypeReference<>() {
        });
    }
}
