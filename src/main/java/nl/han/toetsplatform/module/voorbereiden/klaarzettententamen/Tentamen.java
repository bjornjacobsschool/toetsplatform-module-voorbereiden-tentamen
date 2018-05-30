package nl.han.toetsplatform.module.voorbereiden.klaarzettententamen;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.Date;

public class Tentamen {
    private StringProperty naam;
    private StringProperty desc;
    private Date van;
    private Date tot;
    private StringProperty sleutel;

    public Tentamen(String naam, String sleutel, String desc) {
        this.naam = new SimpleStringProperty(naam);
        this.van = van;
        this.tot = tot;
        this.sleutel = new SimpleStringProperty(sleutel);
        this.desc = new SimpleStringProperty(desc);
    }

    public String getNaam() {
        return naam.get();
    }

    public StringProperty naamProperty() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam.set(naam);
    }

    public String getDesc() {
        return desc.get();
    }

    public StringProperty descProperty() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc.set(desc);
    }

    public String getSleutel() {
        return sleutel.get();
    }

    public StringProperty sleutelProperty() {
        return sleutel;
    }

    public void setSleutel(String sleutel) {
        this.sleutel.set(sleutel);
    }
}
