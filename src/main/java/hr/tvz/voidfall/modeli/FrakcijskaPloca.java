package hr.tvz.voidfall.modeli;

import java.io.Serializable;

public class FrakcijskaPloca implements Serializable {

    private int utvrdivanje;
    private int vojnaProizvodnja;
    private int diplomacija;

    public FrakcijskaPloca() {
        this.utvrdivanje = 0;
        this.vojnaProizvodnja = 0;
        this.diplomacija = 0;
    }
}