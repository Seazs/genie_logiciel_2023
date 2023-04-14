package ulb.infof307.g12.controller.storage;

import ulb.infof307.g12.controller.javafx.connexion.MenuPrincipal;
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
    public void save(Utilisateur user) throws IOException {
        List<Paquet> listPaquet = user.getListPaquet();
        for (Paquet paquet : listPaquet){
            File paquetdatabase = new File("./stockage/"+user.getPseudo(),paquet.getNom()); // On crée un fichier avec le nom du paquet dans le dossier de l'utilisateur
            FileWriter writer = new  FileWriter(paquetdatabase);
            BufferedWriter out = new BufferedWriter(writer);
            out.write(paquet.getNom());
            out.newLine();
            out.write(saveCategories(paquet));
            save_card(paquet, out);
            out.close();
        }

    }

    /**
     * Sauvegarde une carte
     * @param paquet
     * @param out
     * @throws IOException
     */
    private void save_card(Paquet paquet, BufferedWriter out) throws IOException {
        for(int i = 0; i < paquet.cartes.size() ; i++){ //Ecriture de toutes les cartes dans le fichier
            Carte carte = paquet.cartes.get(i);
            out.newLine();
            out.write(carte.getType()+ "#"+ carte.getRecto() + "#" + carte.getVerso());
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
                Paquet newPaquet = new Paquet(lineNom, loadCategories(lineCategorie));
                load_all_cards(reader, newPaquet);
                loadedListOfPaquet.add(newPaquet);
                reader.close();
            }
            return loadedListOfPaquet;
        }catch (IOException erreur){
            return null;
        }
    }

    /**
     * Load toutes les cartes du paquet
     * @param reader
     * @param newPaquet
     * @throws IOException
     */
    private void load_all_cards(BufferedReader reader, Paquet newPaquet) throws IOException {
        int i=0;
        String line;
        while((line = reader.readLine())!=null) {
            String[] listdata = line.split("#");

            Carte bufferCarte = new Carte(i, "recto", "verso", "");
            bufferCarte.setType(listdata[0].strip());
            bufferCarte.setRecto(listdata[1].strip());
            bufferCarte.setVerso(listdata[2].strip()) ;
            newPaquet.ajouterCarte(bufferCarte);
            i++;}
    }

    /**
     * Supprime le fichier associé au paquet voulu et supprime le paquet de la mémoire
     * @param user
     * @param paquet
     */
    public void remove(Utilisateur user, Paquet paquet) {
        File f = new File("./stockage/"+user.getPseudo()+"/"+paquet.getNom());
        try{
            if(f.exists()){
                f.delete();
                user.removePaquet(paquet.getNom());
            }
        } catch (Exception e) {
            e.printStackTrace();
            MenuPrincipal.getINSTANCE().showErrorPopup("Impossible de retirer le paquet "+paquet.getNom()+" rattaché à l'utilisateur "+user.getPseudo()+" !");
        }


    }


    /**
     * @param line
     * @return String[] des catégories
     */
    private String[] loadCategories(String line){
        return line.split("#");
    }


    /**
     * @param paquet
     * @return cat1#cat2#cat3#
     */
    public static String saveCategories(Paquet paquet){
        String save = "";
        for (int i = 0; i < paquet.getCategories().size(); i++){
            paquet.getCategories().get(i);
            save = save + paquet.getCategories().get(i)+ "#";
        }
        return save;
    }

}
