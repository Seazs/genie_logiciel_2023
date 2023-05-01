package ulb.infof307.g12.model;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

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
    public String postPaquet(Paquet paquet) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(paquet);
        System.out.println(json);

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(json, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(url+"paquet", entity, String.class);
        return response.getBody();
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
        try{
            ResponseEntity<String> response = restTemplate.postForEntity(url+"user/register", entity, String.class);
            return response.getBody();
        }catch (HttpClientErrorException e){
            throw new IllegalArgumentException("Server not responding");
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
