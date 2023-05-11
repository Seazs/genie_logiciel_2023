package ulb.infof307.g12.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

public class CarteTt extends Carte{
    @Getter
    @JsonProperty("begin")
    private String begin;

    @Getter
    @JsonProperty("end")
    private String end;

    @Getter
    @JsonProperty("answer")
    private String answer;

    /**
     * Constructeur de la carte TT
     * @param id  id de la carte
     * @param recto recto de la carte
     * @param verso verso de la carte
     */
    public CarteTt(int id, String recto, String verso) {
        super(id, recto, verso);
        this.type="TT";
        editRecto(recto);
        this.verso = verso;
        getTTInfo();
    }

    /**
     * Constructeur pour la désérialisation avec jackson
     * @param id id de la carte
     * @param recto recto de la carte
     * @param verso verso de la carte
     * @param begin début de la phrase
     * @param end fin de la phrase
     * @param answer réponse
     */
    @JsonCreator
    public CarteTt(@JsonProperty("id") int id,@JsonProperty("recto") String recto,@JsonProperty("verso") String verso,@JsonProperty("begin") String begin,@JsonProperty("end") String end,@JsonProperty("answer") String answer) {
        super(id, recto, verso);
        this.type="TT";
        editRecto(recto);
        this.verso = verso;
        this.begin = begin;
        this.end = end;
        this.answer = answer;
    }

    /**
     * Complète les infos à partir des rectos et verso reçus au stockage
     * recto = begin§end
     * verso = reponse
     */
    private void getTTInfo(){
        String[] listinfos = this.getRecto().split("§");
        this.begin = listinfos[0];
        this.end = listinfos[1];
        this.answer = this.getVerso();
    }

    /**
     * @return les informations de la carte
     */
    @Override
    @JsonIgnore
    public String[] getCarteInfo(){
        String[] info = new String[3];
        info[0] = this.begin;
        info[1]=this.end;
        info[2] = this.answer;
        return info;
    }
}
