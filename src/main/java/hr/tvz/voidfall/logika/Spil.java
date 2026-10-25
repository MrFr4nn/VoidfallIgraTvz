package hr.tvz.voidfall.logika;

import hr.tvz.voidfall.modeli.AkcijskaKartica;
import hr.tvz.voidfall.modeli.Igrac;
import hr.tvz.voidfall.modeli.Sektor;
import hr.tvz.voidfall.modeli.VrstaKartice;
import java.util.ArrayList;
import java.util.Collections;

public class Spil {
    private UcinakKarte ucinak = new UcinakKarte();

    public AkcijskaKartica napraviKarticu(VrstaKartice vrsta) {
        return new AkcijskaKartica(vrsta.getNaziv(), vrsta, vrsta.getOpis());
    }

    public void podijeliRuku(Igrac igrac) {
        ArrayList<AkcijskaKartica> sve = new ArrayList<>();
        VrstaKartice[] vrste = VrstaKartice.values();
        for (int i = 0; i < vrste.length; i++) {
            if (vrste[i] != VrstaKartice.KORUPCIJA) {
                sve.add(napraviKarticu(vrste[i]));
            }
        }
        Collections.shuffle(sve);
        for (int i = 0; i < 4; i++) {
            igrac.getRuka().add(sve.get(i));
        }
    }

    public String odigrajKartu(AkcijskaKartica karta, Sektor svoj, Sektor protivnik, Korupcija korupcija) {
        return ucinak.izvrsi(karta, svoj, protivnik, korupcija);
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