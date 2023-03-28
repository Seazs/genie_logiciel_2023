package ulb.infof307.g12.controller.storage;

import ulb.infof307.g12.model.Carte;
import ulb.infof307.g12.model.Paquet;
import ulb.infof307.g12.model.Utilisateur;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class GestionnairePaquet {


    /**
     * Sauvegarde un paquet de cartes sous forme de fichier dans le dossier de l'utilisateur.
     * @param user
     * @throws IOException
     */
    public static void save(Utilisateur user) throws IOException {
        List<Paquet> listPaquet = user.getListPaquet();
        for (Paquet paquet : listPaquet){
            File paquetdatabase = new File("./stockage/"+user.getPseudo(),paquet.getNom()); // On crée un fichier avec le nom du paquet dans le dossier de l'utilisateur
            FileWriter writer = new  FileWriter(paquetdatabase);
            BufferedWriter out = new BufferedWriter(writer);

            out.write(paquet.getNom());
            out.newLine();
            out.write(paquet.getCategorie());

            for(int i = 0; i < paquet.cartes.size() ; i++){ //Ecriture de toutes les cartes dans le fichier
                Carte carte = paquet.cartes.get(i);
                out.newLine();
                out.write(carte.getRecto() + "#" + carte.getVerso());
            }
            out.close();
        }

    }

    /**
     * Charge la liste des paquets correspondant a l'utilisateur en mémoire
     * @param user
     * @return
     */
    public List<Paquet> load(Utilisateur user) {

        try {
            File userfolder = new File("./stockage/"+user.getPseudo());
            File[] listOfFilePaquet = userfolder.listFiles(); //Enumère les fichiers dans le dossier de l'utilisateur
            List<Paquet> loadedListOfPaquet = new ArrayList<Paquet>();

            for (File file : listOfFilePaquet) {
                FileReader fileReader = new FileReader(file);
                BufferedReader reader = new BufferedReader(fileReader);

                String lineNom = reader.readLine();
                String lineCategorie = reader.readLine();
                Paquet newPaquet = new Paquet(lineNom, lineCategorie);
                int i=0;
                String line;
                while((line = reader.readLine())!=null) {
                    String[] listdata = line.split("#");

                    Carte bufferCarte = new Carte(i, "recto", "verso");

                    bufferCarte.recto = listdata[0].strip();
                    bufferCarte.verso = listdata[1].strip();
                    newPaquet.ajouterCarte(bufferCarte);
                    i++;}
                loadedListOfPaquet.add(newPaquet);
                reader.close();
            }
            return loadedListOfPaquet;
        }catch (IOException erreur){
            return null;
        }
    }

    /**
     * Supprime le fichier associé au paquet voulu et supprime le paquet de la mémoire
     * @param user
     * @param paquet
     * @throws FileNotFoundException
     */
    public static void remove(Utilisateur user, Paquet paquet) throws FileNotFoundException {
        File f = new File("./stockage/"+user.getPseudo()+"/"+paquet.getNom());
        try{
            if(f.exists()){
                f.delete();
                user.removePaquet(paquet.getNom());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }


    }

}
