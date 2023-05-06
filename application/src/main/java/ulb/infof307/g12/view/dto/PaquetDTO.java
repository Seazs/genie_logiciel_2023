package ulb.infof307.g12.view.dto;

import ulb.infof307.g12.controller.javafx.connexion.MenuPrincipal;
import ulb.infof307.g12.model.Paquet;

import java.util.List;
import java.util.Optional;

public record PaquetDTO(String nom, List<String> categories) {

    /**
     * Récupère le paquet associé au DTO
     * @return le premier paquet trouvé
     */
    public Optional<Paquet> getPaquet(){
        return MenuPrincipal.getINSTANCE()
                .getUserPaquets()
                .stream()
                .filter(p -> p.getDTO().equals(this))
                .findFirst();
    }

}
