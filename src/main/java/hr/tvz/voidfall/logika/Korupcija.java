package hr.tvz.voidfall.logika;

import hr.tvz.voidfall.modeli.FrakcijskaPloca;
import hr.tvz.voidfall.modeli.Sektor;

public class Korupcija {
    private Sektor sektor;

    public Korupcija(Sektor sektor) {
        this.sektor = sektor;
    }

    public synchronized void promijeni(int promjena) {
        sektor.setKorupcija(sektor.getKorupcija() + promjena);
    }

    public int razinaPrijetnje() {
        int korupcija = sektor.getKorupcija();
        if (korupcija == 0) {
            return 1;
        }
        if (korupcija == 1) {
            return 2;
        }
        return 3;
    }

    public boolean uvjetIspunjen(int runda) {
        FrakcijskaPloca ploca = sektor.getPloca();
        if (runda == 1) {
            return ploca.getIndustrija() >= 1;
        }
        if (runda == 2) {
            return ploca.getIstrazivanje() >= 2;
        }
        return ploca.getDiplomacija() >= 1;
    }

    public String opisUvjeta(int runda) {
        if (runda == 1) {
            return "Uvjet runde: industrija najmanje 1";
        }
        if (runda == 2) {
            return "Uvjet runde: istraživanje najmanje 2";
        }
        if (runda == 3) {
            return "Uvjet runde: diplomacija najmanje 1";
        }
        return "Završna borba: nema uvjeta";
    }
}