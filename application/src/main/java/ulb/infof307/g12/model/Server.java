package ulb.infof307.g12.model;


import java.net.URL;

import org.springframework.http.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
/**
 * Class permettant de envoyer/recevoir les informations d'un serveur
 */
public class Server {
    private String url = "http://localhost:8080/api/v1/";

    /**
     * Envoie une requète GET au serveur et renvoie les données
     * @return les données du serveur
     */
    public String getPaquets(){
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity(url+"paquet", String.class);
        String responseBody = response.getBody();
       return responseBody;
    }

    /**
     * Envoie un paquet (avec juste le nom) au serveur
     * @param nom nom du paquet
     * @return l'état de la demande ("200 OK" si tout s'est bien passé)
     */
    public String postPaquet(String nom){
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_PLAIN);
        HttpEntity<String> entity = new HttpEntity<>(nom, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(url+"paquet", entity, String.class);
        return response.getBody();
    }

    /**
     * Envoie une requète GET au serveur pour authentifier un utilisateur.
     * @param username
     * @return
     */
    public STATUS getLogin(String username,String password){
        RestTemplate restTemplate = new RestTemplate();
        try {
            ResponseEntity<String> response = restTemplate.getForEntity(url + "user/" + username, String.class);
            String result = response.getBody();
            if(result.equals(password))
                return STATUS.OK;
            else
                return STATUS.WRONG_PASSWORD;
        }catch (HttpClientErrorException e){
            return STATUS.USERNAME_DOES_NOT_EXIST;
        }
    }

}
