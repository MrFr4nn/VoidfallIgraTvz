package hr.tvz.voidfall.serijalizacija;

import hr.tvz.voidfall.modeli.Igrac;
import hr.tvz.voidfall.modeli.Sektor;
import java.io.Serializable;

public class StanjeIgre implements Serializable {
    private Igrac igrac;
    private Sektor voidborn;
    private int runda;

    public StanjeIgre(Igrac igrac, Sektor voidborn, int runda) {
        this.igrac = igrac;
        this.voidborn = voidborn;
        this.runda = runda;
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
}