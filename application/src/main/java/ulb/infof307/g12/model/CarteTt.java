package ulb.infof307.g12.model;

import java.util.ArrayList;
import java.util.Collections;

public class CarteTt extends Carte{
    public CarteTt(int id, String recto, String verso) {
        super(id, recto, verso);
        this.type="TT";
        editRecto(recto);
        this.verso = verso;
    }

    public ArrayList<String> getTTInfo(){
        ArrayList<String> infos = new ArrayList<String>();
        String[] listinfos = this.getRecto().split("§");
        Collections.addAll(infos, listinfos);
        infos.add(this.getVerso());
        return infos;
    }
}
