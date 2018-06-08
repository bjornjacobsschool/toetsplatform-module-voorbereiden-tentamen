package nl.han.toetsplatform.module.voorbereiden.models;

import nl.han.toetsapplicatie.apimodels.dto.VersieDto;

public class Versie extends VersieDto {

    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
