package hr.tvz.voidfall.modeli;

import java.io.Serializable;

public class AkcijskaKartica implements Serializable {

    private TipKartice tip;
    private char odabranaOpcija;
    private int redoslijed;

    public AkcijskaKartica(TipKartice tip, char odabranaOpcija, int redoslijed) {
        this.tip = tip;
        this.odabranaOpcija = odabranaOpcija;
        this.redoslijed = redoslijed;
    }

    public TipKartice getTip() {
        return tip;
    }

    public char getOdabranaOpcija() {
        return odabranaOpcija;
    }

    public int getRedoslijed() {
        return redoslijed;
    }
}