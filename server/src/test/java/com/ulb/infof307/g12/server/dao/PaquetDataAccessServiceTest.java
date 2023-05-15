package com.ulb.infof307.g12.server.dao;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ulb.infof307.g12.server.model.Card;
import com.ulb.infof307.g12.server.model.Paquet;
import com.ulb.infof307.g12.server.model.STATUS;
import org.junit.jupiter.api.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class PaquetDataAccessServiceTest {
    private static File dossierTemporaire, dossierTemporairePaquet;

    private static Paquet paquet;
    @BeforeAll
    public static void creeDossierTemporaire() throws IOException {
        dossierTemporaire = new File("./stockage");
        dossierTemporaire.mkdirs();
        dossierTemporairePaquet = new File("./stockage/paquet");
        dossierTemporaire.mkdirs();
        UUID id = UUID.randomUUID();
        ArrayList<String> categories = new ArrayList<>();
        categories.add("test");
        ArrayList<Card> cards = new ArrayList<>();
        cards.add(new Card(1, "test", "test", "QCM"));
        paquet = new Paquet(id, "nomtest", categories, cards);
    }
    @AfterAll
    public static void supprimeDossierTemporaire(){
        dossierTemporaire.delete();
        dossierTemporairePaquet.delete();
    }
    @Test
    void saveLoadTest() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        PaquetDataAccessService paquetDataAccessService = new PaquetDataAccessService();
        paquetDataAccessService.createPaquet(objectMapper.writeValueAsString(paquet));
        paquetDataAccessService.save();
        File f = new File("server/src/main/resources/stockage/paquet/" + paquet.getId().toString() + ".json");
        assertTrue(f.exists());
        PaquetDataAccessService paquetDataAccessService2 = new PaquetDataAccessService();
        List<Paquet> paquets = paquetDataAccessService2.getAllPaquets();
        assertEquals(true,paquets.contains(paquet));
        f.delete();
    }

    @Test
    void createDuplicatePaquet() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        PaquetDataAccessService paquetDataAccessService = new PaquetDataAccessService();
        paquetDataAccessService.createPaquet(objectMapper.writeValueAsString(paquet));
        assertEquals(STATUS.DUPLICATE,paquetDataAccessService.createPaquet(objectMapper.writeValueAsString(paquet)));
    }

    @Test
    void getPaquet() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        PaquetDataAccessService paquetDataAccessService = new PaquetDataAccessService();
        paquetDataAccessService.createPaquet(objectMapper.writeValueAsString(paquet));
        assertEquals(paquet,paquetDataAccessService.getPaquet(paquet.getId()));
    }

    @Test
    void deletePaquet() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        PaquetDataAccessService paquetDataAccessService = new PaquetDataAccessService();
        paquetDataAccessService.createPaquet(objectMapper.writeValueAsString(paquet));
        paquetDataAccessService.deletePaquet(paquet.getId());
        assertEquals(null,paquetDataAccessService.getPaquet(paquet.getId()));
    }
}