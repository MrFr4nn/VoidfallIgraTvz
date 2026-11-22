package hr.tvz.voidfall.logika;

import hr.tvz.voidfall.ai.VoidbornNPC;
import hr.tvz.voidfall.modeli.AkcijskaKartica;
import hr.tvz.voidfall.modeli.Igrac;
import hr.tvz.voidfall.modeli.Sektor;
import hr.tvz.voidfall.modeli.VrstaKartice;
import hr.tvz.voidfall.serijalizacija.StanjeIgre;

public class VoidfallEngine {
    private Igrac igrac;
    private Sektor voidborn;
    private int runda;                          // 1 do 4
    private Korupcija korupcija;
    private Borba borba;
    private DnevnikIgre dnevnik = new DnevnikIgre(this);
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
        dnevnik.ocisti();
        runda = 1;
        postaviPomocneKlase();
        dnevnik.zapisi("POCETAK_IGRE", "SUSTAV", "Nova igra. Oba sektora imaju zdravlje 10.");
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
            dnevnik.zapisi("POCETAK_RUNDE", "SUSTAV", "Runda " + runda + ": igrač dobiva karte: "
                    + dnevnik.imena(igrac.getRuka()) + ". Uvjet runde: " + korupcija.kratkiOpis(runda) + ".");
        } else {
            dnevnik.zapisi("POCETAK_RUNDE", "SUSTAV", "Runda 4: završna borba, razvojnih karata više nema.");
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
        if (broj == 0) {
            dnevnik.zapisi("PLAN", "IGRAC", "Igrač izvršava plan: " + dnevnik.imena(igrac.getOdabranePlan()) + ".");
        }
        AkcijskaKartica karta = igrac.getOdabranePlan().get(broj);
        zadnjaKartaIgraca = karta;
        int[] prije = dnevnik.stanje();
        String poruka = igrac.getIme() + ": " + karta.getNaziv() + " - "
                + spil.odigrajKartu(karta, igrac.getSektor(), voidborn, korupcija);
        dnevnik.zapisiAkciju("IGRAC", karta, poruka, prije);
        borba.provjeriKraj();
        if (borba.isKraj()) {
            return poruka;
        }
        VrstaKartice odluka = npc.odaberiKartu(voidborn, igrac.getSektor(), korupcija.razinaPrijetnje());
        AkcijskaKartica kartaVoidborna = spil.napraviKarticu(odluka);
        zadnjaKartaVoidborna = kartaVoidborna;
        prije = dnevnik.stanje();
        String porukaVoidborna = "Voidborn: " + kartaVoidborna.getNaziv() + " - "
                + spil.odigrajKartu(kartaVoidborna, voidborn, igrac.getSektor(), korupcija);
        dnevnik.zapisiAkciju("VOIDBORN", kartaVoidborna, porukaVoidborna, prije);
        borba.provjeriKraj();
        return poruka + "\n" + porukaVoidborna;
    }

    public String zavrsiRundu() {
        String uvjet = korupcija.kratkiOpis(runda);
        int[] prije = dnevnik.stanje();
        String poruka;
        if (korupcija.uvjetIspunjen(runda)) {
            poruka = "KRAJ RUNDE " + runda + ". Uvjet runde: " + uvjet + " - ISPUNJEN, korupcija ostaje "
                    + prije[2] + ".";
        } else {
            korupcija.promijeni(1);
            poruka = "KRAJ RUNDE " + runda + ". Uvjet runde: " + uvjet + " - NIJE ispunjen, korupcija raste s "
                    + prije[2] + " na " + igrac.getSektor().getKorupcija()
                    + ". Veća korupcija znači jačeg Voidborna.";
        }
        dnevnik.zapisi("KRAJ_RUNDE", "SUSTAV", poruka);
        dnevnik.zapisiPromjene("SUSTAV", prije);
        runda = runda + 1;
        podijeliKarte();
        return poruka;
    }

    public void pocniZavrsnuBorbu(boolean igracPrvi) {
        String prvi = "Voidborn";
        if (igracPrvi) {
            prvi = "igrač";
        }
        dnevnik.zapisi("ZAVRSNA_BORBA", "SUSTAV", "Završna borba počinje. Prvi napada: " + prvi + ".");
    }

    public String napadni(boolean igracNapada) {
        int[] prije = dnevnik.stanje();
        String poruka = borba.napadni(igracNapada);
        String akter = "VOIDBORN";
        if (igracNapada) {
            akter = "IGRAC";
        }
        dnevnik.zapisi("NAPAD", akter, poruka);
        dnevnik.zapisiPromjene(akter, prije);
        return poruka;
    }

    public StanjeIgre uzmiStanje() {
        return new StanjeIgre(igrac, voidborn, runda, dnevnik.getRedovi());
    }

    public void postaviStanje(StanjeIgre stanje) {
        igrac = stanje.getIgrac();
        voidborn = stanje.getVoidborn();
        runda = stanje.getRunda();
        dnevnik.postaviRedove(stanje.getZapis());
        postaviPomocneKlase();
    }

    public void spremiZapis() {
        dnevnik.spremi();
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