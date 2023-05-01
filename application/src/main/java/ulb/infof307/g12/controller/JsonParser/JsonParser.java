package ulb.infof307.g12.controller.JsonParser;

import org.json.JSONArray;
import org.json.JSONObject;
import ulb.infof307.g12.model.Carte;
import ulb.infof307.g12.model.CarteQcm;
import ulb.infof307.g12.model.CarteTt;
import ulb.infof307.g12.model.Paquet;

import java.util.ArrayList;

public class JsonParser {

    public ArrayList<Paquet> jsonToListePaquets(JSONArray paquets) {
        ArrayList<Paquet> paquetsList = new ArrayList<>();
        String nom ;

        for (int i = 0; i < paquets.length(); i++) {
            JSONObject paquet = paquets.getJSONObject(i);
            nom = paquet.getString("nom");
            Paquet paquetToAdd = new Paquet(nom);

            JSONArray categories= paquet.getJSONArray("categories");
            AddCategoriesList(paquet, paquetToAdd, categories);

            JSONArray cartes = paquet.getJSONArray("cartes");

            AddCardsList(paquetToAdd, cartes);
            paquetsList.add(paquetToAdd);
            }

        return paquetsList;
    }

    private static void AddCategoriesList(JSONObject paquet, Paquet paquetToAdd, JSONArray categories) {
        for (int j = 0; j < paquet.getJSONArray("categories").length() ; j++) {
            paquetToAdd.addCategory(categories.get(j).toString());}
    }

    private static void AddCardsList(Paquet paquetToAdd, JSONArray cartes) {
        for (int k = 0; k < cartes.length(); k++) {
            JSONObject carte = cartes.getJSONObject(k);
            String type = carte.getString("type");

            switch (type) {
                case "QCM" -> {
                    String recto = carte.getString("recto");
                    String verso = carte.getString("verso");
                    CarteQcm carteToAdd = new CarteQcm(k, recto, verso);
                    paquetToAdd.addCard(carteToAdd);
                }
                case "Simple" -> {
                    String recto = carte.getString("recto");
                    String verso = carte.getString("verso");
                    Carte carteToAdd = new Carte(k, recto, verso);
                    paquetToAdd.addCard(carteToAdd);
                }
                case "TT" -> {
                    String recto = carte.getString("recto");
                    String verso = carte.getString("verso");
                    CarteTt carteToAdd = new CarteTt(k, recto, verso);
                    paquetToAdd.addCard(carteToAdd);
                }
            }
        }
    }

}
