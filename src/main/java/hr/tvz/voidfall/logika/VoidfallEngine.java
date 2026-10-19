package hr.tvz.voidfall.logika;

import hr.tvz.voidfall.modeli.AkcijskaKartica;
import hr.tvz.voidfall.modeli.Igrac;
import hr.tvz.voidfall.modeli.Sektor;

public class VoidfallEngine {
    private Igrac igrac;
    private Sektor voidborn;
    private int runda;

    public VoidfallEngine() {
        igrac = new Igrac("Igrač");
        voidborn = new Sektor();
        runda = 1;
    }

    public int izracunajStetu(Sektor napadac, Sektor branitelj) {
        int steta = napadac.getNapad() - branitelj.getObrana();
        if (steta < 0) {
            steta = 0;
        }
        return steta;
    }

    public void osvjeziNapadIObranu(Sektor sektor) {
        sektor.setObrana(sektor.getPloca().getIndustrija());
        sektor.setNapad(sektor.getPloca().getIstrazivanje());
    }

    public String odigrajKartu(AkcijskaKartica karta, Sektor svoj, Sektor protivnik) {
        String poruka = "";
        switch (karta.getVrsta()) {
            case INDUSTRIJALIZACIJA:
                svoj.getPloca().setIndustrija(svoj.getPloca().getIndustrija() + 1);
                poruka = "Industrija raste.";
                break;
            case ISTRAZIVANJE:
                svoj.getPloca().setIstrazivanje(svoj.getPloca().getIstrazivanje() + 1);
                poruka = "Istraživanje raste.";
                break;
            case DIPLOMACIJA:
                svoj.getPloca().setDiplomacija(svoj.getPloca().getDiplomacija() + 1);
                svoj.setKorupcija(svoj.getKorupcija() - 1);
                poruka = "Diplomacija raste, korupcija pada.";
                break;
            case POPRAVAK:
                svoj.setZdravlje(svoj.getZdravlje() + 3);
                poruka = "Sektor se popravlja za 3.";
                break;
            case ORBITALNI_NAPAD:
                protivnik.setZdravlje(protivnik.getZdravlje() - 2);
                poruka = "Orbitalni napad nanosi 2 štete.";
                break;
        }
        osvjeziNapadIObranu(svoj);
        return poruka;
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