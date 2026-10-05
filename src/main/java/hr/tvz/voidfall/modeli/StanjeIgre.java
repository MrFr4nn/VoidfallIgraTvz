package hr.tvz.voidfall.modeli;

import java.io.Serializable;

public class StanjeIgre implements Serializable {

    private Igrac igrac;
    private VoidbornNPC voidborn;
    private Sektor neutralniSektor1;
    private Sektor neutralniSektor2;
    private Faza faza;
    private int redniBrojCiklusa;

    public StanjeIgre(Igrac igrac, VoidbornNPC voidborn, Sektor neutralniSektor1, Sektor neutralniSektor2) {
        this.igrac = igrac;
        this.voidborn = voidborn;
        this.neutralniSektor1 = neutralniSektor1;
        this.neutralniSektor2 = neutralniSektor2;
        this.faza = Faza.PLANIRANJE;
        this.redniBrojCiklusa = 1;
    }

    public Igrac getIgrac() {
        return igrac;
    }

    public VoidbornNPC getVoidborn() {
        return voidborn;
    }

    public Faza getFaza() {
        return faza;
    }

    public void setFaza(Faza faza) {
        this.faza = faza;
    }

    public int getRedniBrojCiklusa() {
        return redniBrojCiklusa;
    }

    public void sljedeciCiklus() {
        redniBrojCiklusa++;
    }
}