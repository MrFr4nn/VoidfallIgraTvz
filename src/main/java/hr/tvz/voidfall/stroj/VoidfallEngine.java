package hr.tvz.voidfall.stroj;

import hr.tvz.voidfall.modeli.AkcijskaKartica;
import hr.tvz.voidfall.modeli.Faza;
import hr.tvz.voidfall.modeli.Frakcija;
import hr.tvz.voidfall.modeli.Igrac;
import hr.tvz.voidfall.modeli.Sektor;
import hr.tvz.voidfall.modeli.StanjeIgre;
import hr.tvz.voidfall.modeli.TipKartice;
import hr.tvz.voidfall.modeli.VoidbornNPC;

public class VoidfallEngine {

    private StanjeIgre stanjeIgre;

    public VoidfallEngine() {
        novaIgra();
    }

    public StanjeIgre getStanjeIgre() {
        return stanjeIgre;
    }

    public void novaIgra() {
        Igrac igrac = new Igrac("igrac");
        VoidbornNPC voidborn = new VoidbornNPC("voidborn");

        Sektor igracGlavni = new Sektor("igrac-glavni", "igrac", 5, 5);
        igracGlavni.setMaterijali(5);
        igracGlavni.setUtjecaj(2);
        igrac.dodajSektor(igracGlavni);

        Sektor voidbornGlavni = new Sektor("voidborn-glavni", "voidborn", 5, 5);
        voidbornGlavni.setMaterijali(5);
        voidbornGlavni.setUtjecaj(2);
        voidborn.dodajSektor(voidbornGlavni);

        Sektor neutralni1 = new Sektor("neutralni-1", "nitko", 0, 0);
        Sektor neutralni2 = new Sektor("neutralni-2", "nitko", 0, 0);

        stanjeIgre = new StanjeIgre(igrac, voidborn, neutralni1, neutralni2);
    }

    public boolean dodajKarticuUPlan(TipKartice tip, char opcija, int redoslijed) {
        if (stanjeIgre.getFaza() != Faza.PLANIRANJE) {
            return false;
        }
        if (opcija != 'A' && opcija != 'B' && opcija != 'C') {
            return false;
        }
        if (redoslijed < 1 || redoslijed > 3) {
            return false;
        }

        Igrac igrac = stanjeIgre.getIgrac();
        if (igrac.getTrenutniPlan().size() >= 3) {
            return false;
        }
        for (AkcijskaKartica k : igrac.getTrenutniPlan()) {
            if (k.getTip() == tip || k.getRedoslijed() == redoslijed) {
                return false;
            }
        }

        igrac.dodajKarticuUPlan(new AkcijskaKartica(tip, opcija, redoslijed));
        return true;
    }

    public boolean zakljucajPlan() {
        if (stanjeIgre.getFaza() != Faza.PLANIRANJE) {
            return false;
        }
        if (stanjeIgre.getIgrac().getTrenutniPlan().size() != 3) {
            return false;
        }
        stanjeIgre.setFaza(Faza.ZAKLJUCANO);
        return true;
    }

    public boolean izvrsiCiklus() {
        if (stanjeIgre.getFaza() != Faza.ZAKLJUCANO) {
            return false;
        }
        stanjeIgre.setFaza(Faza.REZOLUCIJA);
        stanjeIgre.getIgrac().ocistiPlan();
        stanjeIgre.sljedeciCiklus();
        proizvodnja(stanjeIgre.getIgrac());
        proizvodnja(stanjeIgre.getVoidborn());
        azurirajThreat();
        stanjeIgre.setFaza(Faza.PLANIRANJE);
        return true;
    }

    private void proizvodnja(Frakcija frakcija) {
        for (Sektor s : frakcija.getSektori()) {
            s.setMaterijali(s.getMaterijali() + 5);
            s.setVojska(s.getVojska() + 1);
        }
    }

    private void azurirajThreat() {
        int razina = (stanjeIgre.getRedniBrojCiklusa() - 1) / 2 + 1;
        if (razina > 3) {
            razina = 3;
        }
        stanjeIgre.getVoidborn().setThreatLevel(razina);
    }
}