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

    private int potrebnaVrijednost(int runda) {
        if (runda == 2) {
            return 2;
        }
        return 1;
    }

    private int trenutnaVrijednost(int runda) {
        FrakcijskaPloca ploca = sektor.getPloca();
        if (runda == 1) {
            return ploca.getIndustrija();
        }
        if (runda == 2) {
            return ploca.getIstrazivanje();
        }
        return ploca.getDiplomacija();
    }

    private String nazivUvjeta(int runda) {
        if (runda == 1) {
            return "industrija";
        }
        if (runda == 2) {
            return "istraživanje";
        }
        return "diplomacija";
    }

    public boolean uvjetIspunjen(int runda) {
        return trenutnaVrijednost(runda) >= potrebnaVrijednost(runda);
    }

    public String kratkiOpis(int runda) {
        return nazivUvjeta(runda) + " najmanje " + potrebnaVrijednost(runda)
                + " (imaš " + trenutnaVrijednost(runda) + ")";
    }

    public String opisUvjeta(int runda) {
        if (runda > 3) {
            return "Završna borba: nema uvjeta.";
        }
        String tekst = "UVJET RUNDE " + runda + ": " + kratkiOpis(runda) + ".\n";
        if (uvjetIspunjen(runda)) {
            return tekst + "STATUS: ISPUNJEN za rundu " + runda + ". Korupcija neće rasti.";
        }
        return tekst + "STATUS: još NIJE ispunjen. Ako tako ostane do kraja runde " + runda
                + ", korupcija raste za 1.";
    }
}