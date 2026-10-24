package hr.tvz.voidfall.ai;

import hr.tvz.voidfall.modeli.Sektor;
import hr.tvz.voidfall.modeli.VrstaKartice;

public class VoidbornNPC {
    private int niskoZdravlje = 4;
    private int igracUOpasnosti = 3;
    private int dovoljnoNapada = 2;

    public VrstaKartice odaberiKartu(Sektor svoj, Sektor igrac, int razinaPrijetnje) {
        if (svoj.getZdravlje() <= niskoZdravlje) {
            return VrstaKartice.POPRAVAK;
        }
        if (igrac.getZdravlje() <= igracUOpasnosti) {
            return VrstaKartice.ORBITALNI_NAPAD;
        }
        if (razinaPrijetnje == 1) {
            return razvojnaKarta(svoj);
        }
        if (razinaPrijetnje == 2) {
            return podmuklaKarta(svoj);
        }
        return napadnaKarta(svoj);
    }

    public VrstaKartice razvojnaKarta(Sektor svoj) {
        int industrija = svoj.getPloca().getIndustrija();
        int istrazivanje = svoj.getPloca().getIstrazivanje();
        if (industrija < 3 && industrija <= istrazivanje) {
            return VrstaKartice.INDUSTRIJALIZACIJA;
        }
        if (istrazivanje < 3) {
            return VrstaKartice.ISTRAZIVANJE;
        }
        return VrstaKartice.ORBITALNI_NAPAD;
    }

    public VrstaKartice podmuklaKarta(Sektor svoj) {
        if (svoj.getPloca().getIstrazivanje() < dovoljnoNapada) {
            return VrstaKartice.ISTRAZIVANJE;
        }
        return VrstaKartice.KORUPCIJA;
    }

    public VrstaKartice napadnaKarta(Sektor svoj) {
        if (svoj.getPloca().getIstrazivanje() < dovoljnoNapada) {
            return VrstaKartice.ISTRAZIVANJE;
        }
        return VrstaKartice.ORBITALNI_NAPAD;
    }
}