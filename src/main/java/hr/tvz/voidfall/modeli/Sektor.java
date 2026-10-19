package hr.tvz.voidfall.modeli;

import java.io.Serializable;

public class Sektor implements Serializable {
    private int zdravlje;
    private int napad;
    private int obrana;
    private int korupcija;
    private FrakcijskaPloca ploca;

    public Sektor() {
        zdravlje = 10;
        napad = 0;
        obrana = 0;
        korupcija = 0;
        ploca = new FrakcijskaPloca();
    }

    public int getZdravlje() {
        return zdravlje;
    }

    public void setZdravlje(int zdravlje) {
        if (zdravlje > 10) zdravlje = 10;
        if (zdravlje < 0) zdravlje = 0;
        this.zdravlje = zdravlje;
    }

    public int getNapad() {
        return napad;
    }

    public void setNapad(int napad) {
        this.napad = napad;
    }

    public int getObrana() {
        return obrana;
    }

    public void setObrana(int obrana) {
        this.obrana = obrana;
    }

    public int getKorupcija() {
        return korupcija;
    }

    public void setKorupcija(int korupcija) {
        if (korupcija > 3) korupcija = 3;
        if (korupcija < 0) korupcija = 0;
        this.korupcija = korupcija;
    }

    public FrakcijskaPloca getPloca() {
        return ploca;
    }
}