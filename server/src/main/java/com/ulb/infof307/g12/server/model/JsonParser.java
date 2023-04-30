package com.ulb.infof307.g12.server.model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class JsonParser {

    public static ArrayList<String> parseJsonArray(JSONArray categories) {
        ArrayList<String> categoriesList = new ArrayList<>();
        for (int i = 0; i < categories.length(); i++) {
            categoriesList.add(categories.getString(i));
        }
        return categoriesList;
    }
    public static ArrayList<Carte> parseJsonArrayCarte(JSONArray cartes) {
        ArrayList<Carte> cartesList = new ArrayList<>();
        for (int i = 0; i < cartes.length(); i++) {
            JSONObject carte = cartes.getJSONObject(i);
            Carte carteToAdd = new Carte();
            carteToAdd.setId(carte.getInt("id"));
            carteToAdd.setRecto(carte.getString("recto"));
            carteToAdd.setVerso(carte.getString("verso"));
            carteToAdd.setType(carte.getString("type"));
            cartesList.add(carteToAdd);
        }
        return cartesList;
    }

}
