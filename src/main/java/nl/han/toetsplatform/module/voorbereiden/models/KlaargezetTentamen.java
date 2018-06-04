package nl.han.toetsplatform.module.voorbereiden.models;

import java.util.Date;

public class KlaargezetTentamen {

    private Tentamen tentamen;
    private Date van;
    private Date tot;
    private String sleutel;

    public KlaargezetTentamen(Tentamen tentamen, Date van, Date tot, String sleutel) {
        this.tentamen = tentamen;
        this.van = van;
        this.tot = tot;
        this.sleutel = sleutel;
    }

    public Tentamen getTentamen() {
        return tentamen;
    }

    public void setTentamen(Tentamen tentamen) {
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
