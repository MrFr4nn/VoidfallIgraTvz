package hr.tvz.voidfall.serijalizacija;

import hr.tvz.voidfall.modeli.Igrac;
import hr.tvz.voidfall.modeli.Sektor;
import java.io.Serializable;
import java.util.ArrayList;

public class StanjeIgre implements Serializable {
    private static final long serialVersionUID = 2L;
    private Igrac igrac;
    private Sektor voidborn;
    private int runda;
    private ArrayList<String> zapis;

    public StanjeIgre(Igrac igrac, Sektor voidborn, int runda, ArrayList<String> zapis) {
        this.igrac = igrac;
        this.voidborn = voidborn;
        this.runda = runda;
        this.zapis = zapis;
    }

    public Igrac getIgrac() {
        return igrac;
    }

    public Sektor getVoidborn() {
        return voidborn;
    }

    public int getRunda() {
        return runda;
    }

    public ArrayList<String> getZapis() {
        return zapis;
    }
}