package hr.tvz.voidfall.logika;

import hr.tvz.voidfall.modeli.AkcijskaKartica;
import hr.tvz.voidfall.xml.XmlZapisivac;
import java.util.ArrayList;

public class DnevnikIgre {
    private VoidfallEngine engine;
    private XmlZapisivac zapisivac = new XmlZapisivac();

    public DnevnikIgre(VoidfallEngine engine) {
        this.engine = engine;
    }

    public void ocisti() {
        zapisivac = new XmlZapisivac();
    }

    public int[] stanje() {
        return zapisivac.snimi(engine.getIgrac().getSektor(), engine.getVoidborn());
    }

    public void zapisi(String vrsta, String akter, String opis) {
        zapisivac.dodajDogadaj(engine.getRunda(), vrsta, akter, opis, engine.getIgrac().getSektor(),
                engine.getVoidborn(), engine.getKorupcija().razinaPrijetnje());
    }

    public void zapisiPromjene(String akter, int[] prije) {
        zapisivac.dodajPromjene(engine.getRunda(), akter, prije, engine.getIgrac().getSektor(),
                engine.getVoidborn(), engine.getKorupcija().razinaPrijetnje());
    }

    public void zapisiAkciju(String akter, AkcijskaKartica karta, String poruka, int[] prije) {
        String vrsta = "KARTA";
        if (karta.getVrsta().getKategorija().equals("EKSPANZIJA")) {
            vrsta = "NAPAD";
        }
        zapisi(vrsta, akter, poruka);
        zapisiPromjene(akter, prije);
    }

    public String imena(ArrayList<AkcijskaKartica> karte) {
        String tekst = "";
        for (int i = 0; i < karte.size(); i++) {
            if (i > 0) {
                tekst += ", ";
            }
            tekst += karte.get(i).getNaziv();
        }
        return tekst;
    }

    public ArrayList<String> getRedovi() {
        return zapisivac.getRedovi();
    }

    public void postaviRedove(ArrayList<String> redovi) {
        zapisivac.postaviRedove(redovi);
    }

    public void spremi() {
        if (engine.getBorba().isKraj() && !zapisivac.imaVrstu("KRAJ_IGRE")) {
            zapisi("KRAJ_IGRE", "SUSTAV", "KRAJ IGRE: " + engine.getBorba().getIshod());
        }
        zapisivac.spremi();
    }
}