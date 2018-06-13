package nl.han.toetsplatform.module.voorbereiden.models;

import nl.han.toetsapplicatie.apimodels.dto.KlaargezetTentamenDto;
import nl.han.toetsapplicatie.apimodels.dto.SamengesteldTentamenDto;

import java.util.Date;

public class KlaargezetTentamen {

    private SamengesteldTentamenDto tentamen;
    private Date van;
    private Date tot;
    private String sleutel;

    public KlaargezetTentamen() {
    }


    public KlaargezetTentamen(SamengesteldTentamenDto tentamen, Date van, Date tot, String sleutel) {
        this.tentamen = tentamen;
        this.van = van;
        this.tot = tot;
        this.sleutel = sleutel;
    }

    public SamengesteldTentamenDto getTentamen() {
        return tentamen;
    }

    public void setTentamen(SamengesteldTentamenDto tentamen) {
        this.tentamen = tentamen;
    }

    public Date getVan() {
        return van;
    }

    public void setVan(Date van) {
        this.van = van;
    }

    public Date getTot() {
        return tot;
    }

    public void setTot(Date tot) {
        this.tot = tot;
    }

    public String getSleutel() {
        return sleutel;
    }

    public void setSleutel(String sleutel) {
        this.sleutel = sleutel;
    }
}
