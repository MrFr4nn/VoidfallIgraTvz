package hr.tvz.voidfall.modeli;

import java.io.Serializable;

public class AkcijskaKartica implements Serializable {
    private String naziv;
    private VrstaKartice vrsta;
    private String opis;

    public AkcijskaKartica(String naziv, VrstaKartice vrsta, String opis) {
        this.naziv = naziv;
        this.vrsta = vrsta;
        this.opis = opis;
    }

    public String getNaziv() {
        return naziv;
    }

    public VrstaKartice getVrsta() {
        return vrsta;
    }

    public String getOpis() {
        return opis;
    }
}