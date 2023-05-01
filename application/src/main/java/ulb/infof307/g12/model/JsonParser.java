package ulb.infof307.g12.model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class JsonParser {

    /**
     * Fonction qui parse un JSONArray de categories en ArrayList de categories
     * @param paquets JSONArray of paquets
     * @return ArrayList<Paquet> of paquets
     */
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

    /**
     * Fonction qui ajoute les catégories au paquet
     * @param paquet paquet
     * @param paquetToAdd paquet auquel ajouter les catégories
     * @param categories catégories à ajouter
     */
    private static void AddCategoriesList(JSONObject paquet, Paquet paquetToAdd, JSONArray categories) {
        for (int j = 0; j < paquet.getJSONArray("categories").length() ; j++) {
            paquetToAdd.addCategory(categories.get(j).toString());}
    }

    /**
     * Fonction qui ajoute les cartes au paquet
     * @param paquetToAdd paquet auquel ajouter les cartes
     * @param cartes     cartes à ajouter
     */
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
