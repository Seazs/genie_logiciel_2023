package com.ulb.infof307.g12.server.model;

import lombok.Getter;
import lombok.Setter;

public class Carte {

    @Getter
    @Setter
    private int id;
    @Getter
    @Setter
    private String recto, verso, type;
}
