package hr.tvz.voidfall.logika;

import hr.tvz.voidfall.modeli.AkcijskaKartica;
import hr.tvz.voidfall.modeli.Igrac;
import hr.tvz.voidfall.modeli.Sektor;
import hr.tvz.voidfall.modeli.VrstaKartice;
import java.util.ArrayList;
import java.util.Collections;

public class Spil {
    private int stetaOrbitalnog = 2;
    private int lijecenje = 3;

    public AkcijskaKartica napraviKarticu(VrstaKartice vrsta) {
        switch (vrsta) {
            case INDUSTRIJALIZACIJA:
                return new AkcijskaKartica("Industrijalizacija", vrsta, "+1 industrija (obrana)");
            case ISTRAZIVANJE:
                return new AkcijskaKartica("Istraživanje", vrsta, "+1 istraživanje (napad)");
            case DIPLOMACIJA:
                return new AkcijskaKartica("Diplomacija", vrsta, "+1 diplomacija, -1 korupcija");
            case POPRAVAK:
                return new AkcijskaKartica("Popravak", vrsta, "+" + lijecenje + " zdravlje");
            case ORBITALNI_NAPAD:
                return new AkcijskaKartica("Orbitalni napad", vrsta, "-" + stetaOrbitalnog + " zdravlje protivniku");
            default:
                return new AkcijskaKartica("Korupcija", vrsta, "+1 korupcija");
        }
    }

    public void podijeliRuku(Igrac igrac) {
        ArrayList<AkcijskaKartica> sve = new ArrayList<>();
        sve.add(napraviKarticu(VrstaKartice.INDUSTRIJALIZACIJA));
        sve.add(napraviKarticu(VrstaKartice.ISTRAZIVANJE));
        sve.add(napraviKarticu(VrstaKartice.DIPLOMACIJA));
        sve.add(napraviKarticu(VrstaKartice.POPRAVAK));
        sve.add(napraviKarticu(VrstaKartice.ORBITALNI_NAPAD));
        Collections.shuffle(sve);
        for (int i = 0; i < 4; i++) {
            igrac.getRuka().add(sve.get(i));
        }
    }

    public String odigrajKartu(AkcijskaKartica karta, Sektor svoj, Sektor protivnik, Korupcija korupcija) {
        String poruka = "";
        switch (karta.getVrsta()) {
            case INDUSTRIJALIZACIJA:
                svoj.getPloca().setIndustrija(svoj.getPloca().getIndustrija() + 1);
                poruka = "industrija raste.";
                break;
            case ISTRAZIVANJE:
                svoj.getPloca().setIstrazivanje(svoj.getPloca().getIstrazivanje() + 1);
                poruka = "istraživanje raste.";
                break;
            case DIPLOMACIJA:
                svoj.getPloca().setDiplomacija(svoj.getPloca().getDiplomacija() + 1);
                korupcija.promijeni(-1);
                poruka = "diplomacija raste, korupcija pada.";
                break;
            case POPRAVAK:
                svoj.setZdravlje(svoj.getZdravlje() + lijecenje);
                poruka = "sektor se popravlja za " + lijecenje + ".";
                break;
            case ORBITALNI_NAPAD:
                protivnik.setZdravlje(protivnik.getZdravlje() - stetaOrbitalnog);
                poruka = "nanosi " + stetaOrbitalnog + " štete.";
                break;
            case KORUPCIJA:
                korupcija.promijeni(1);
                poruka = "korupcija raste.";
                break;
        }
        // Industrija daje obranu, istraživanje daje napad (1 razina = 1 bod)
        svoj.setObrana(svoj.getPloca().getIndustrija());
        svoj.setNapad(svoj.getPloca().getIstrazivanje());
        return poruka;
    }

    public String sastaviPlan(ArrayList<String> imena, String prazno) {
        String tekst = "Plan: ";
        for (int i = 0; i < 3; i++) {
            if (i < imena.size()) {
                tekst += imena.get(i);
            } else {
                tekst += prazno;
            }
            if (i < 2) {
                tekst += " | ";
            }
        }
        return tekst;
    }
}