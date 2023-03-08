package ulb.infof307.g12.connexion;

import ulb.infof307.g12.connexion.*;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        GestionnaireUtilisateur gestionnaire = new GestionnaireUtilisateur();
        Utilisateur user1 = new Utilisateur("Brenno", "brebre");
        Utilisateur user2 = new Utilisateur("Ismail", "isis");
        gestionnaire.add(user1);
        gestionnaire.add(user2);
        gestionnaire.save();
    }

}
