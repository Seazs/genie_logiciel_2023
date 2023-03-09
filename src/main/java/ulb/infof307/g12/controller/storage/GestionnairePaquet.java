package ulb.infof307.g12.controller.storage;

import ulb.infof307.g12.model.Carte;
import ulb.infof307.g12.model.Paquet;
import ulb.infof307.g12.model.Utilisateur;

import java.io.*;

public class GestionnairePaquet {

    /**
     * Sauvegarde un paquet de cartes sous forme de fichier.
     * Format:
     * - categorie
     *
     * @param paquet
     * @param sauvegarde
     * @throws IOException
     */
    public static void save(Paquet paquet, File sauvegarde) throws IOException {

        if(!sauvegarde.exists())
            sauvegarde.mkdir();

        sauvegarde.setWritable(true);
        FileWriter fileWriter = new FileWriter(sauvegarde,true);
        BufferedWriter writer = new BufferedWriter(fileWriter);

        writer.write(paquet.getCategorie());

        writer.close();
        sauvegardeCartes(paquet,sauvegarde);
    }

    /**
     * Charge un paquet en mémoire à partir de son nom et du dossier où il est stocké
     * @param sauvegarde
     * @return le paquet si le fichier existe et qu'il n'y a pas de corruption de donnée
     * @throws IOException
     */
    public static Paquet load(File sauvegarde) {

        if(!sauvegarde.exists())
            sauvegarde.mkdir();

        try {
            FileReader fileReader = new FileReader(sauvegarde);
            BufferedReader reader = new BufferedReader(fileReader);

            String line = reader.readLine();
            new Paquet(sauvegarde.getName().replace(".ulb",""), line);
            new Carte bufferCarte =
            while(line != null) {
                line = reader.readLine();

                if (!line.isBlank()) {
                    String[] listdata = line.split("#");
                    listeUtilisateur.add(new Utilisateur(listdata[0].strip(), listdata[1].strip()));
                }
            }


            reader.close();

            return Paquet;
        }catch (IOException erreur){
            return null;
        }
    }

    public static void remove(Paquet paquet, File dossier){

    }

    public static void sauvegardeCartes(Paquet paquet, File sauvegarde) throws IOException {
        if(!sauvegarde.exists())
            sauvegarde.mkdir();

        sauvegarde.setWritable(true);
        FileWriter fileWriter = new FileWriter(sauvegarde,true);
        BufferedWriter writer = new BufferedWriter(fileWriter);

        for(int i = 0; i < paquet.cartes.length ; i++){
            Carte carte = paquet.cartes[i];

            writer.newLine();
            writer.write(carte.getRecto() + "#" + carte.getVerso());
        }

        writer.close();
    }

}
