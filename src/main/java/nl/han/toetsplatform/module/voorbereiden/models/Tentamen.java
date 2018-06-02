package nl.han.toetsplatform.module.voorbereiden.models;

import nl.han.toetsapplicatie.module.model.Vraag;

import java.util.ArrayList;
import java.util.List;

public class Tentamen {
    private String id;
    private String naam;
    private String beschrijving;
    private String toegestaandeHulpmiddelen;
    private String vak;
    private int tijdsduur;
    private Versie versie;
    private boolean isVerzegeld;
    private String hash;
    private List<Vraag> vragen;

    public Tentamen() {
        vragen = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public String getBeschrijving() {
        return beschrijving;
    }

    public void setBeschrijving(String beschrijving) {
        this.beschrijving = beschrijving;
    }

    public String getToegestaandeHulpmiddelen() {
        return toegestaandeHulpmiddelen;
    }

    public void setToegestaandeHulpmiddelen(String toegestaandeHulpmiddelen) {
        this.toegestaandeHulpmiddelen = toegestaandeHulpmiddelen;
    }

    public int getTijdsduur() {
        return tijdsduur;
    }

    public void setTijdsduur(int tijdsduur) {
        this.tijdsduur = tijdsduur;
    }

    public Versie getVersie() {
        return versie;
    }

    public void setVersie(Versie versie) {
        this.versie = versie;
    }

    public boolean isVerzegeld() {
        return isVerzegeld;
    }

    public void setVerzegeld(boolean verzegeld) {
        isVerzegeld = verzegeld;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public List<Vraag> getVragen() {
        return vragen;
    }

    public void setVragen(List<Vraag> vragen) {
        this.vragen = vragen;
    }

    public String getVak() {
        return vak;
    }

    public void setVak(String vak) {
        this.vak = vak;
    }
}
