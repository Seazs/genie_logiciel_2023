package ulb.infof307.g12.model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class JsonParser {

    public ArrayList<Paquet> jsonToListePaquets(JSONArray paquets) {
        ArrayList<Paquet> paquetsList = new ArrayList<Paquet>();
        String nom ;
        ArrayList<String> listeCategories ;

        for (int i = 0; i < paquets.length(); i++) {
            JSONObject paquet = paquets.getJSONObject(i);
            nom = paquet.getString("nom");
            Paquet paquetToAdd = new Paquet(nom);

            JSONArray categories= paquet.getJSONArray("categories");
            for (int j=0 ; j < paquet.getJSONArray("categories").length() ; j++) {
                paquetToAdd.ajouterCategorie(categories.get(j).toString());}

            JSONArray cartes = paquet.getJSONArray("cartes");

            for (int k = 0; k < cartes.length(); k++) {
                JSONObject carte = cartes.getJSONObject(k);
                String type = carte.getString("type");

                 if (type.equals("QCM")) {
                     String recto = carte.getString("recto");
                     String verso = carte.getString("verso");
                     CarteQcm carteToAdd = new CarteQcm(k,recto, verso);
                     paquetToAdd.ajouterCarte(carteToAdd);
            }
                 else if (type.equals("Simple")) {
                     String recto = carte.getString("recto");
                     String verso = carte.getString("verso");
                     Carte carteToAdd = new Carte(k,recto, verso);
                     paquetToAdd.ajouterCarte(carteToAdd);
                 }
                 else if (type.equals("TT")) {
                     String recto = carte.getString("recto");
                     String verso = carte.getString("verso");
                     CarteTt carteToAdd = new CarteTt(k,recto, verso);
                     paquetToAdd.ajouterCarte(carteToAdd);
                 }
            }
                paquetsList.add(paquetToAdd);
            }

        return paquetsList;
    }

}
