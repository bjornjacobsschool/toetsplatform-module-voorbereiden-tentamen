package nl.han.toetsplatform.module.voorbereiden.samenstellententamen.model;



import nl.han.toetsapplicatie.module.model.Vraag;

import java.util.List;

public class Toets {

    public int id;

    public String naam;

    public List<Vraag> vragen;

    public List<Vraag> getVragen() {
        return vragen;
    }

    public void setVragen(List<Vraag> vragen) {
        this.vragen = vragen;
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
