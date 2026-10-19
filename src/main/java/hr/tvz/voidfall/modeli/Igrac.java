package hr.tvz.voidfall.modeli;

import java.io.Serializable;
import java.util.ArrayList;

public class Igrac implements Serializable {
    private String ime;
    private Sektor sektor;
    private ArrayList<AkcijskaKartica> ruka;
    private ArrayList<AkcijskaKartica> odabranePlan;

    public Igrac(String ime) {
        this.ime = ime;
        sektor = new Sektor();
        ruka = new ArrayList<>();
        odabranePlan = new ArrayList<>();
    }

    public String getIme() {
        return ime;
    }

    public Sektor getSektor() {
        return sektor;
    }

    public ArrayList<AkcijskaKartica> getRuka() {
        return ruka;
    }

    public ArrayList<AkcijskaKartica> getOdabranePlan() {
        return odabranePlan;
    }
}