package ulb.infof307.g12.model;

import lombok.Getter;

public class CarteTt extends Carte{
    @Getter
    private String begin;
    @Getter
    private String end;
    @Getter
    private String answer;
    public CarteTt(int id, String recto, String verso) {
        super(id, recto, verso);
        this.type="TT";
        editRecto(recto);
        this.verso = verso;
        getTTInfo();
    }

    /**
     * Complète les infos à partir des rectos et verso reçus au stockage
     * recto = begin§end
     * verso = reponse
     */
    public void getTTInfo(){
        String[] listinfos = this.getRecto().split("§");
        this.begin = listinfos[0];
        this.end = listinfos[1];
        this.answer = this.getVerso();
    }
}
