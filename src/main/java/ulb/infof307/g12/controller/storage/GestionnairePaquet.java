package ulb.infof307.g12.controller.storage;

import ulb.infof307.g12.model.Paquet;

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

            String categorie = reader.readLine();

            reader.close();

            return new Paquet(sauvegarde.getName().replace(".ulb",""), categorie);
        }catch (IOException erreur){
            return null;
        }
    }

    public static void remove(Paquet paquet, File dossier){

    }

}
