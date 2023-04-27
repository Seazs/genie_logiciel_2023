package ulb.infof307.g12.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CarteQcm extends Carte {
    /**
     * Constructeur de la carte QCM
     * @param id
     * @param recto
     * @param verso
     */
    public CarteQcm(int id, String recto, String verso) {
        super(id, recto, verso);
        this.type="QCM";
        editRecto(recto);
        this.verso = verso;
    }

    /**
     * Fonction qui retourne les informations de la carte QCM
     * @return
     */
    public ArrayList<String> getQCMInfo(){
        ArrayList<String> infos = new ArrayList<String>();
        String[] listinfos = this.getRecto().split("§");
        Collections.addAll(infos, listinfos);
        infos.add(this.getVerso());
        return infos;
    }

}
