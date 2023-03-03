package ulb.infof307.g12.storage;

import ulb.infof307.g12.model.Paquet;

import java.io.*;

public class GestionnairePaquet {

    /**
     * Sauvegarde un paquet de cartes sous forme de fichier.
     * Format:
     * - categorie
     *
     * @param paquet
     * @param dossier
     * @throws IOException
     */
    public static void save(Paquet paquet, File dossier) throws IOException {

        if(!dossier.exists())
            dossier.mkdir();

        File sauvegarde = new File(dossier.getPath()+"/"+paquet.getName()+".ulb");

        if(!sauvegarde.exists())
            sauvegarde.mkdir();

        FileWriter fileWriter = new FileWriter(sauvegarde);
        BufferedWriter writer = new BufferedWriter(fileWriter);

        writer.write(paquet.getCategorie());

    }

    /**
     * Charge un paquet en mémoire à partir de son nom et du dossier où il est stocké
     * @param nom
     * @param dossier
     * @return le paquet si le fichier existe et qu'il n'y a pas de corruption de donnée
     * @throws IOException
     */
    public static Paquet load(String nom, File dossier) {
        if(!dossier.exists())
            dossier.mkdir();

        File sauvegarde = new File(dossier.getPath()+"/"+nom+".ulb");

        if(!sauvegarde.exists())
            sauvegarde.mkdir();

        try {
            FileReader fileReader = new FileReader(sauvegarde);
            BufferedReader reader = new BufferedReader(fileReader);

            String categorie = reader.readLine();

            return new Paquet(nom, categorie);
        }catch (IOException erreur){
            return null;
        }
    }

    public static void remove(Paquet paquet, File dossier){

    }

}
