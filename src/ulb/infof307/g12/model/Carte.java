package ulb.infof307.g12.model;


public class
Carte {
    public int id;
    public String recto;
    public String verso;

    /**
     * Crée une carte dont ni le verso ni le recto ne peuvent être vides.
     * @param id
     * @param recto
     * @param verso
     */
    Carte(int id, String recto,  String verso){
        if(recto == null || recto.equals(""))
            throw new IllegalArgumentException("La carte doit posséder un recto");

        if(verso == null || verso.equals(""))
            throw new IllegalArgumentException("La carte doit posséder un verso");

        this.id = id;
        this.recto = recto;
        this.verso = verso;
    }

    /**
     * Fonction qui édite la variable "recto" de la classe carte
     * @param new_recto
     */
    public void editRecto(String new_recto){
        if (new_recto == null || new_recto.equals(""))
            throw new IllegalArgumentException("La carte doit posséder un recto");
        recto = new_recto;
    }

    /**
     * Fonction qui édite la variable "verso" de la classe carte
     * @param new_verso
     */
    public void editVerso(String new_verso){
        if (new_verso == null || new_verso.equals(""))
            throw new IllegalArgumentException("La carte doit posséder un verso");
        verso = new_verso;
    }


    /**
     * @return recto
     */
    public String getRecto() {
        return recto;
    }

    /**
     * @return verso
     */
    public String getVerso() {
        return verso;
    }
}
