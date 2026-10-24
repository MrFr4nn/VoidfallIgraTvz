package hr.tvz.voidfall.logika;

import hr.tvz.voidfall.ai.VoidbornNPC;
import hr.tvz.voidfall.modeli.AkcijskaKartica;
import hr.tvz.voidfall.modeli.Igrac;
import hr.tvz.voidfall.modeli.Sektor;
import hr.tvz.voidfall.modeli.VrstaKartice;
import hr.tvz.voidfall.serijalizacija.StanjeIgre;
import hr.tvz.voidfall.xml.XmlZapisivac;
import java.util.ArrayList;

public class VoidfallEngine {
    private Igrac igrac;
    private Sektor voidborn;
    private int runda;
    private ArrayList<String> voidbornPlan;
    private Korupcija korupcija;
    private Borba borba;
    private XmlZapisivac zapisivac;
    private Spil spil = new Spil();
    private VoidbornNPC npc = new VoidbornNPC();

    public VoidfallEngine() {
        novaIgra();
    }

    public void novaIgra() {
        igrac = new Igrac("Igrač");
        voidborn = new Sektor();
        zapisivac = new XmlZapisivac();
        voidbornPlan = new ArrayList<>();
        runda = 1;
        postaviPomocneKlase();
        podijeliKarte();
    }

    private void postaviPomocneKlase() {
        korupcija = new Korupcija(igrac.getSektor());
        borba = new Borba(igrac.getSektor(), voidborn);
    }

    private void podijeliKarte() {
        igrac.getRuka().clear();
        igrac.getOdabranePlan().clear();
        voidbornPlan.clear();
        if (runda <= 3) {
            spil.podijeliRuku(igrac);
        }
    }

    public boolean dodajUPlan(int indeks) {
        if (indeks >= igrac.getRuka().size()) {
            return false;
        }
        AkcijskaKartica karta = igrac.getRuka().get(indeks);
        if (igrac.getOdabranePlan().size() >= 3 || igrac.getOdabranePlan().contains(karta)) {
            return false;
        }
        igrac.getOdabranePlan().add(karta);
        return true;
    }

    public void ponistiPlan() {
        igrac.getOdabranePlan().clear();
    }

    public String odigrajKorak(int broj) {
        AkcijskaKartica karta = igrac.getOdabranePlan().get(broj);
        String poruka = "Igrač: " + karta.getNaziv() + " - "
                + spil.odigrajKartu(karta, igrac.getSektor(), voidborn, korupcija);
        zapisi("KARTA", poruka);
        borba.provjeriKraj();
        if (borba.isKraj()) {
            return poruka;
        }
        VrstaKartice odluka = npc.odaberiKartu(voidborn, igrac.getSektor(), korupcija.razinaPrijetnje());
        AkcijskaKartica kartaVoidborna = spil.napraviKarticu(odluka);
        String porukaVoidborna = "Voidborn: " + kartaVoidborna.getNaziv() + " - "
                + spil.odigrajKartu(kartaVoidborna, voidborn, igrac.getSektor(), korupcija);
        voidbornPlan.add(kartaVoidborna.getNaziv());
        String vrsta = "KARTA";
        if (odluka == VrstaKartice.ORBITALNI_NAPAD) {
            vrsta = "NAPAD";
        } else if (odluka == VrstaKartice.KORUPCIJA) {
            vrsta = "KORUPCIJA";
        }
        zapisi(vrsta, porukaVoidborna);
        borba.provjeriKraj();
        return poruka + "\n" + porukaVoidborna;
    }

    public String zavrsiRundu() {
        String poruka = "Uvjet ispunjen, korupcija se ne mijenja.";
        if (!korupcija.uvjetIspunjen(runda)) {
            korupcija.promijeni(1);
            poruka = "Uvjet NIJE ispunjen, korupcija raste za 1.";
            zapisi("KORUPCIJA", poruka);
        }
        zapisi("KRAJ_RUNDE", "Kraj runde " + runda + ".");
        runda = runda + 1;
        podijeliKarte();
        return poruka;
    }

    public String napadni(boolean igracNapada) {
        String poruka = borba.napadni(igracNapada);
        zapisi("NAPAD", poruka);
        return poruka;
    }

    private void zapisi(String vrsta, String opis) {
        zapisivac.dodajDogadaj(runda, vrsta, opis, igrac.getSektor().getZdravlje(),
                voidborn.getZdravlje(), igrac.getSektor().getKorupcija());
    }

    public String planIgraca() {
        ArrayList<String> imena = new ArrayList<>();
        for (int i = 0; i < igrac.getOdabranePlan().size(); i++) {
            imena.add(igrac.getOdabranePlan().get(i).getNaziv());
        }
        return spil.sastaviPlan(imena, "-");
    }

    public String planVoidborna() {
        return spil.sastaviPlan(voidbornPlan, "?");
    }

    public StanjeIgre uzmiStanje() {
        return new StanjeIgre(igrac, voidborn, runda);
    }

    public void postaviStanje(StanjeIgre stanje) {
        igrac = stanje.getIgrac();
        voidborn = stanje.getVoidborn();
        runda = stanje.getRunda();
        voidbornPlan.clear();
        postaviPomocneKlase();
    }

    public void spremiZapis() {
        zapisivac.spremi();
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

    public Korupcija getKorupcija() {
        return korupcija;
    }

    public Borba getBorba() {
        return borba;
    }
}