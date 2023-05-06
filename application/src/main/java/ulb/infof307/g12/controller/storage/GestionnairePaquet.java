package ulb.infof307.g12.controller.storage;

import ulb.infof307.g12.Main;
import ulb.infof307.g12.controller.javafx.connexion.MenuPrincipal;
import ulb.infof307.g12.model.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Integer.parseInt;

public class GestionnairePaquet {

    /**
     * Sauvegarde un paquet de cartes sous forme de fichier dans le dossier de l'utilisateur.
     * @param user utilisateur
     * @throws IOException exception
     */
    public void save(Utilisateur user) throws IOException {
        List<Paquet> listPaquet = user.getListPaquet();
        for (Paquet paquet : listPaquet){
            File paquetdatabase = new File(Main.getFolderPath()+user.getPseudo(),paquet.getNom()); // On crée un fichier avec le nom du paquet dans le dossier de l'utilisateur
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
            if(carte.getType()=="Spec"){
                CarteSpec cartespec=(CarteSpec)carte;
                out.write("#" + cartespec.getLanguage());
            }
            out.write("#" + carte.getConnaissance());
        }

    }

    /**
     * Charge la liste des paquets correspondant à l'utilisateur en mémoire
     * @param user
     * @return
     */
    public List<Paquet> load(Utilisateur user) {

        try {
            File userfolder = new File(Main.getFolderPath()+user.getPseudo());
            File[] listOfFilePaquet = userfolder.listFiles(); //Enumère les fichiers dans le dossier de l'utilisateur
            List<Paquet> loadedListOfPaquet = new ArrayList<Paquet>();

            assert listOfFilePaquet != null; //Si le dossier est vide, on renvoie une liste vide
            for (File file : listOfFilePaquet) { //Pour chaque fichier dans le dossier de l'utilisateur
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
            if (listdata[0].equals("QCM")){ //Si la carte est un QCM
                CarteQcm bufferCarte = new CarteQcm(i, listdata[1],  listdata[2]);
                bufferCarte.setConnaissance(parseInt(listdata[3].strip()));
                newPaquet.addCard(bufferCarte);
            }
            else if(listdata[0].equals("Simple")){
                Carte bufferCarte = new Carte(i, listdata[1], listdata[2]);
                bufferCarte.setConnaissance(parseInt(listdata[3].strip()));
                newPaquet.addCard(bufferCarte);
            }
            else if(listdata[0].equals("TT")){
                CarteTt bufferCarte = new CarteTt(i, listdata[1], listdata[2]);
                bufferCarte.setConnaissance(parseInt(listdata[3].strip()));
                newPaquet.addCard(bufferCarte);
            }
            else if(listdata[0].equals("Spec")){
                CarteSpec bufferCarte = new CarteSpec(i, listdata[1], listdata[2],listdata[3]);
                bufferCarte.setConnaissance(parseInt(listdata[4].strip()));
                newPaquet.addCard(bufferCarte);
            }

            i++;}
    }

    /**
     * Supprime le fichier associé au paquet voulu et supprime le paquet de la mémoire
     * @param user
     * @param paquet
     */
    public void remove(Utilisateur user, Paquet paquet) {
        File f = new File("./src/main/resources/stockage/"+user.getPseudo()+"/"+paquet.getNom());
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
     * Récupération des catégories sous forme de liste
     * @param line de type cat1#cat2#...#cat3
     * @return String[] des catégories
     */
    private String[] loadCategories(String line){
        return line.split("#");
    }


    /**
     * Liste les catégories dans un string dans le bon format pour le stockage
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
