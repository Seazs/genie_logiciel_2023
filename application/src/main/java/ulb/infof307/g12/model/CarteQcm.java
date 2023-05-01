package ulb.infof307.g12.model;

import lombok.Getter;

public class CarteQcm extends Carte {

    /**
     * Constructeur de la carte QCM
     */
    @Getter
    private String question;
    @Getter
    private String answer = this.getVerso();
    @Getter
    private String[] propositions;
    public CarteQcm(int id, String recto, String verso) {
        super(id, recto, verso);
        this.type="QCM";
        editRecto(recto);
        this.verso = verso;
        getQCMInfo();
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
    public String[] getCarteInfo(){
        String[] info = new String[5];
        info[0] = this.question;
        info[1] = this.propositions[0];
        info[2] = this.propositions[1];
        info[3] = this.propositions[2];
        info[4] = this.answer;
        return info;
    }

}