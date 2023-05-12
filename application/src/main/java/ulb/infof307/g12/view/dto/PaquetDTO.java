package ulb.infof307.g12.view.dto;

import ulb.infof307.g12.controller.javafx.connexion.MenuPrincipal;
import ulb.infof307.g12.model.Paquet;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public record PaquetDTO(String uuid,String nom, List<String> categories) {

    /**
     * Récupère le paquet associé au DTO
     * @return le premier paquet trouvé
     */
    public Optional<Paquet> getPaquet(){
        return getPaquet(MenuPrincipal.getINSTANCE().getUserPaquets());
    }

    /**
     * Récupère le paquet associé au DTO se trouvant dans une liste donnée
     * @param paquets liste où le paquet sera cherché
     * @return le premier paquet trouvé
     */
    public Optional<Paquet> getPaquet(List<Paquet> paquets){
        return paquets
                .stream()
                .filter(p -> Objects.equals(p.getDTO(),this))
                .findFirst();
    }

}
