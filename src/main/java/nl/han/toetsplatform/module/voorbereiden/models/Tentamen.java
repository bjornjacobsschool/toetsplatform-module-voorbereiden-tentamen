package nl.han.toetsplatform.module.voorbereiden.models;

import com.owlike.genson.annotation.JsonIgnore;
import nl.han.toetsapplicatie.apimodels.dto.SamengesteldTentamenDto;
import nl.han.toetsapplicatie.apimodels.dto.VersieDto;
import nl.han.toetsapplicatie.apimodels.dto.VragenbankVraagDto;

import java.util.ArrayList;
import java.util.List;

public class Tentamen extends SamengesteldTentamenDto {

    @JsonIgnore
    Versie versieVersie;

    List<Vraag> vragenVragen;

    public Tentamen() {
        vragenVragen = new ArrayList<>();

    }

    public List<Vraag> getVragenVraag(){
        return vragenVragen;
    }

    public void setVragenVraag(List<Vraag> vragen){
        this.vragenVragen = vragen;
    }


    @Override
    public List<VragenbankVraagDto> getVragen() {
        List<VragenbankVraagDto> dtoVragen = new ArrayList<>();
        for(VragenbankVraagDto v : vragenVragen){
            dtoVragen.add(v);
        }
        return dtoVragen;
    }

    @Override
    public void setVragen(List<VragenbankVraagDto> vragen) {
        System.out.println("DIT KAN NOG NIET!!!");
    }


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
        if(versieVersie == null) return  null;

        VersieDto versieDto = new VersieDto();
        versieDto.setDatum(versieVersie.getDatum());
        versieDto.setNummer(versieVersie.getNummer());
        versieDto.setOmschrijving(versieVersie.getOmschrijving());
        return versieDto;
    }

    @Override
    public void setVersie(VersieDto versie) {
        if(versie == null) return;
        this.versieVersie = new Versie();
        this.versieVersie.setDatum(versie.getDatum());
        this.versieVersie.setOmschrijving(versie.getOmschrijving());
        this.versieVersie.setNummer(versie.getNummer());
        super.setVersie(versie);
    }
}
