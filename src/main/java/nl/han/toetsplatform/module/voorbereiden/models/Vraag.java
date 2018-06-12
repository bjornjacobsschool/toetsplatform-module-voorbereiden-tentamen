package nl.han.toetsplatform.module.voorbereiden.models;

import com.owlike.genson.annotation.JsonIgnore;
import nl.han.toetsapplicatie.apimodels.dto.VersieDto;
import nl.han.toetsapplicatie.apimodels.dto.VragenbankVraagDto;

public class Vraag extends VragenbankVraagDto {

    @JsonIgnore
    private Versie versieVersie;


    @JsonIgnore
    public Versie getVersieVersie() {
        return versieVersie;
    }


    public void setVersieVersie(Versie versie) {
        this.versieVersie =versie;
        this.setVersie(getVersie());
    }

    @Override
    public VersieDto getVersie() {
        VersieDto versieDto = new VersieDto();
        versieDto.setDatum(versieVersie.getDatum());
        versieDto.setNummer(versieVersie.getNummer());
        versieDto.setOmschrijving(versieVersie.getOmschrijving());
        return versieDto;
    }

    @Override
    public void setVersie(VersieDto versie) {
        this.versieVersie = new Versie();
        this.versieVersie.setDatum(versie.getDatum());
        this.versieVersie.setOmschrijving(versie.getOmschrijving());
        this.versieVersie.setNummer(versie.getNummer());
        super.setVersie(versie);
    }
}
