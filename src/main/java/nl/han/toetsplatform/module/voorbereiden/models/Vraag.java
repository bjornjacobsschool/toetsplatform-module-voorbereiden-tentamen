package nl.han.toetsplatform.module.voorbereiden.models;

import nl.han.toetsapplicatie.apimodels.dto.VersieDto;
import nl.han.toetsapplicatie.apimodels.dto.VragenbankVraagDto;

public class Vraag extends VragenbankVraagDto {
    private Versie versie;


    @Override
    public Versie getVersie() {
        return versie;
    }


    public void setVersie(Versie versie) {
        this.versie = versie;
    }
}
