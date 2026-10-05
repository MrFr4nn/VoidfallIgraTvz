package hr.tvz.voidfall.modeli;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Frakcija implements Serializable {

    private String naziv;
    private int zlato;
    private List<Sektor> sektori;
    private FrakcijskaPloca frakcijskaPloca;

    public Frakcija(String naziv) {
        this.naziv = naziv;
        this.zlato = 0;
        this.sektori = new ArrayList<>();
        this.frakcijskaPloca = new FrakcijskaPloca();
    }

    public List<Sektor> getSektori() {
        return sektori;
    }

    public void dodajSektor(Sektor sektor) {
        sektori.add(sektor);
    }
}