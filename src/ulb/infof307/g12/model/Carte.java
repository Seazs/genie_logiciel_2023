package ulb.infof307.g12.model;

public class Carte {
    public int id;
    public String recto;
    public String verso;

    Carte(int id, String recto,  String verso){
        this.id = id;
        this.recto = recto;
        this.verso = verso;
    }

    /**
     * Function that
     */
    public void EditCarte(){
        recto = ""; // A récuperer avec les input de l'interface
        verso = "";
    }

    public void CreerCarte(String question, String reponse){
        recto = question;
        verso = reponse;
    }

    public String PrintCarte(){
        return ("Question: "+ recto + " Réponse: "+ verso);
    }

    public String getRecto() {
        return recto;
    }

    public String getVerso() {
        return verso;
    }
}
