package ulb.infof307.g12.model;

public class Carte {
    public int id;
    public String recto;
    public String verso;

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
     * Function that edit the recto of the card
     */
    public void editRecto(String new_recto){
        if (new_recto == null || new_recto.equals(""))
            throw new IllegalArgumentException("La carte doit posséder un recto");
        recto = new_recto;
    }

    public void editVerso(String new_verso){
        if (new_verso == null || new_verso.equals(""))
            throw new IllegalArgumentException("La carte doit posséder un verso");
        verso = new_verso;
    }


    public String getRecto() {
        return recto;
    }

    public String getVerso() {
        return verso;
    }
}
