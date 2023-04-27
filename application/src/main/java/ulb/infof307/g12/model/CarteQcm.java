package ulb.infof307.g12.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CarteQcm extends Carte {
    public CarteQcm(int id, String recto, String verso) {
        super(id, recto, verso);
        this.type="QCM";
        editRecto(recto);
        this.verso = verso;
    }

    public ArrayList<String> getQCMInfo(){
        ArrayList<String> infos = new ArrayList<String>();
        String[] listinfos = this.getRecto().split("§");
        Collections.addAll(infos, listinfos);
        infos.add(this.getVerso());
        return infos;
    }

}
