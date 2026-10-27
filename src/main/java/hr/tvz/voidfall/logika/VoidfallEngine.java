package hr.tvz.voidfall.logika;

import hr.tvz.voidfall.ai.VoidbornNPC;
import hr.tvz.voidfall.modeli.AkcijskaKartica;
import hr.tvz.voidfall.modeli.Igrac;
import hr.tvz.voidfall.modeli.Sektor;
import hr.tvz.voidfall.modeli.VrstaKartice;
import hr.tvz.voidfall.serijalizacija.StanjeIgre;
import hr.tvz.voidfall.xml.XmlZapisivac;

public class VoidfallEngine {
    private Igrac igrac;
    private Sektor voidborn;
    private int runda;                          // 1 do 4
    private Korupcija korupcija;
    private Borba borba;
    private XmlZapisivac zapisivac;
    private Spil spil = new Spil();
    private VoidbornNPC npc = new VoidbornNPC();
    private AkcijskaKartica zadnjaKartaIgraca;
    private AkcijskaKartica zadnjaKartaVoidborna;

    public VoidfallEngine() {
        novaIgra();
    }

    public void novaIgra() {
        igrac = new Igrac("Igrač");
        voidborn = new Sektor();
        zapisivac = new XmlZapisivac();
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
        zadnjaKartaIgraca = null;
        zadnjaKartaVoidborna = null;
        if (broj >= igrac.getOdabranePlan().size()) {
            return "";
        }
        AkcijskaKartica karta = igrac.getOdabranePlan().get(broj);
        zadnjaKartaIgraca = karta;
        String poruka = igrac.getIme() + ": " + karta.getNaziv() + " - "
                + spil.odigrajKartu(karta, igrac.getSektor(), voidborn, korupcija);
        zapisi("KARTA", poruka);
        borba.provjeriKraj();
        if (borba.isKraj()) {
            return poruka;
        }
        VrstaKartice odluka = npc.odaberiKartu(voidborn, igrac.getSektor(), korupcija.razinaPrijetnje());
        AkcijskaKartica kartaVoidborna = spil.napraviKarticu(odluka);
        zadnjaKartaVoidborna = kartaVoidborna;
        String porukaVoidborna = "Voidborn: " + kartaVoidborna.getNaziv() + " - "
                + spil.odigrajKartu(kartaVoidborna, voidborn, igrac.getSektor(), korupcija);
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
        String uvjet = korupcija.kratkiOpis(runda);
        int prije = igrac.getSektor().getKorupcija();
        String poruka;
        if (korupcija.uvjetIspunjen(runda)) {
            poruka = "KRAJ RUNDE " + runda + ". Uvjet runde: " + uvjet + " - ISPUNJEN, korupcija ostaje " + prije + ".";
        } else {
            korupcija.promijeni(1);
            poruka = "KRAJ RUNDE " + runda + ". Uvjet runde: " + uvjet + " - NIJE ispunjen, korupcija raste s "
                    + prije + " na " + igrac.getSektor().getKorupcija() + ". Veća korupcija znači jačeg Voidborna.";
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

    public StanjeIgre uzmiStanje() {
        return new StanjeIgre(igrac, voidborn, runda);
    }

    public void postaviStanje(StanjeIgre stanje) {
        igrac = stanje.getIgrac();
        voidborn = stanje.getVoidborn();
        runda = stanje.getRunda();
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

    public AkcijskaKartica getZadnjaKartaIgraca() {
        return zadnjaKartaIgraca;
    }

    public AkcijskaKartica getZadnjaKartaVoidborna() {
        return zadnjaKartaVoidborna;
    }
}