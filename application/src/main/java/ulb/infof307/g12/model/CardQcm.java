package ulb.infof307.g12.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

public class CardQcm extends Card {

    /**
     * Constructeur de la carte QCM
     */
    @Getter
    @JsonProperty("question")
    private String question;

    @Getter
    @JsonProperty("answer")
    private String answer = this.getVerso();

    @Getter
    @JsonProperty("propositions")
    private String[] propositions;

    public CardQcm(int id, String recto, String verso) {
        super(id, recto, verso);
        this.type="QCM";
        editRecto(recto);
        this.verso = verso;
        getQCMInfo();
    }
    @JsonCreator
    public CardQcm(@JsonProperty("id") int id, @JsonProperty("recto") String recto, @JsonProperty("verso") String verso, @JsonProperty("propositions") String[] propositions) {
        super(id, recto, verso);
        this.type="QCM";
        editRecto(recto);
        this.verso = verso;
        this.propositions = propositions;
        this.question = recto;
        this.answer = verso;
    }

    /**
     * Traduit le recto et verso en les bons éléments
     * recto = Question§rep1§rep2§rep3§ ... §repn
     * verso = bonne réponse
     */
    public void getQCMInfo(){
        String[] listinfos = this.getRecto().split("§");
        this.question = listinfos[0];
        this.propositions = new String[listinfos.length-1];
        System.arraycopy(listinfos, 1, this.propositions, 0, listinfos.length - 1);
        this.answer = this.getVerso();
    }

    /**
     * @return les informations de la carte
     */
    @Override
    @JsonIgnore
    public String[] getCardInfo(){
        String[] info = new String[5];
        info[0] = this.question;
        info[1] = this.propositions[0];
        info[2] = this.propositions[1];
        info[3] = this.propositions[2];
        info[4] = this.answer;
        return info;
    }

}
