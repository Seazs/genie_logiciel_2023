package ulb.infof307.g12.model;


import java.net.URL;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
/**
 * Class permettant de envoyer/recevoir les informations d'un serveur
 */
public class Server {
    private String url = "http://localhost:8080/api/v1/paquet";

    /**
     * 
     * @return
     */
    public String getData(){
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://example.com/api/getData";
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        String responseBody = response.getBody();
       return responseBody;
    }
}
