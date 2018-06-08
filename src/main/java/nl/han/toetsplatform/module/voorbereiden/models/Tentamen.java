package nl.han.toetsplatform.module.voorbereiden.models;

import nl.han.toetsapplicatie.apimodels.dto.SamengesteldTentamenDto;
import nl.han.toetsapplicatie.apimodels.dto.VragenbankVraagDto;

import java.util.ArrayList;
import java.util.List;

public class Tentamen extends SamengesteldTentamenDto {

    Versie versie;

    List<Vraag> vragen;

    public Tentamen() {
        vragen = new ArrayList<>();

    }

    public List<Vraag> getVragenVraag(){
        return vragen;
    }

    public void setVragenVraag(List<Vraag> vragen){
        this.vragen = vragen;
    }


    @Override
    public List<VragenbankVraagDto> getVragen() {
        List<VragenbankVraagDto> dtoVragen = new ArrayList<>();
        for(VragenbankVraagDto v : vragen){
            dtoVragen.add(v);
        }
        return dtoVragen;
    }

    @Override
    public void setVragen(List<VragenbankVraagDto> vragen) {
        System.out.println("DIT KAN NOG NIET!!!");
    }

    @Override
    public Versie getVersie() {
        return versie;
    }

    public void setVersie(Versie versie) {
        this.versie = versie;
    }
}
