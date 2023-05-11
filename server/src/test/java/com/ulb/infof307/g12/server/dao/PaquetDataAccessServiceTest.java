package com.ulb.infof307.g12.server.dao;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ulb.infof307.g12.server.model.Carte;
import com.ulb.infof307.g12.server.model.Paquet;
import com.ulb.infof307.g12.server.model.STATUS;
import org.junit.jupiter.api.*;
import org.junit.runners.Suite;

import java.io.File;
import java.io.FileWriter;
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
        dossierTemporaire = new File("./stockageTest");
        dossierTemporaire.mkdir();
        dossierTemporairePaquet = new File("./stockageTest/paquet");
        dossierTemporaire.mkdir();
        UUID id = UUID.randomUUID();
        ArrayList<String> categories = new ArrayList<>();
        categories.add("test");
        ArrayList<Carte> cartes = new ArrayList<>();
        cartes.add(new Carte(1, "test", "test", "test"));
        paquet = new Paquet(id, "nomtest", categories, cartes);
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
    void createDuplicatePaquet() {
        PaquetDataAccessService paquetDataAccessService = new PaquetDataAccessService();
        paquetDataAccessService.createPaquet(paquet.toString());
        assertEquals(STATUS.DUPLICATE,paquetDataAccessService.createPaquet(paquet.toString()));
    }

    @Test
    void getPaquet() {
    }

    @Test
    void deletePaquet() {
    }
}