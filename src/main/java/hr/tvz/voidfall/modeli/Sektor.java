package hr.tvz.voidfall.modeli;

import java.io.Serializable;

public class Sektor implements Serializable {

    private String naziv;
    private String vlasnik;
    private int obrana;
    private int vojska;
    private int materijali;
    private int utjecaj;
    private TipTokena aktivniToken;
    private int corruptionBrojac;
    private int crisisBrojac;
    private boolean deaktiviran;

    public Sektor(String naziv, String vlasnik, int obrana, int vojska) {
        this.naziv = naziv;
        this.vlasnik = vlasnik;
        this.obrana = obrana;
        this.vojska = vojska;
        this.materijali = 0;
        this.utjecaj = 0;
        this.aktivniToken = null;
        this.corruptionBrojac = 0;
        this.crisisBrojac = 0;
        this.deaktiviran = false;
    }
}