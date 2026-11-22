package hr.tvz.voidfall.xml;

import hr.tvz.voidfall.modeli.Sektor;
import java.io.PrintWriter;
import java.util.ArrayList;

public class XmlZapisivac {
    public static final String DATOTEKA = "igra_zapis.xml";
    private ArrayList<String> redovi = new ArrayList<>();

    public int[] snimi(Sektor igrac, Sektor voidborn) {
        return new int[]{igrac.getZdravlje(), voidborn.getZdravlje(), igrac.getKorupcija()};
    }

    private String izbjegni(String tekst) {
        return tekst.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;").replace("\"", "&quot;");
    }

    private String vrijednosti(String prefiks, Sektor s) {
        return " " + prefiks + "Zdravlje=\"" + s.getZdravlje() + "\""
                + " " + prefiks + "Napad=\"" + s.getNapad() + "\""
                + " " + prefiks + "Obrana=\"" + s.getObrana() + "\""
                + " " + prefiks + "Industrija=\"" + s.getPloca().getIndustrija() + "\""
                + " " + prefiks + "Istrazivanje=\"" + s.getPloca().getIstrazivanje() + "\""
                + " " + prefiks + "Diplomacija=\"" + s.getPloca().getDiplomacija() + "\"";
    }

    public synchronized void dodajDogadaj(int runda, String vrsta, String akter, String opis,
                                          Sektor igrac, Sektor voidborn, int prijetnja) {
        redovi.add("  <dogadaj runda=\"" + runda + "\" vrsta=\"" + vrsta + "\" akter=\"" + akter + "\""
                + vrijednosti("igrac", igrac) + vrijednosti("voidborn", voidborn)
                + " korupcija=\"" + igrac.getKorupcija() + "\" prijetnja=\"" + prijetnja + "\""
                + " opis=\"" + izbjegni(opis) + "\"/>");
    }

    public synchronized void dodajPromjene(int runda, String akter, int[] prije,
                                           Sektor igrac, Sektor voidborn, int prijetnja) {
        if (prije[0] != igrac.getZdravlje()) {
            dodajDogadaj(runda, "ZDRAVLJE", akter, "Zdravlje igrača: " + prije[0] + " → "
                    + igrac.getZdravlje(), igrac, voidborn, prijetnja);
        }
        if (prije[1] != voidborn.getZdravlje()) {
            dodajDogadaj(runda, "ZDRAVLJE", akter, "Zdravlje Voidborna: " + prije[1] + " → "
                    + voidborn.getZdravlje(), igrac, voidborn, prijetnja);
        }
        if (prije[2] != igrac.getKorupcija()) {
            dodajDogadaj(runda, "KORUPCIJA", akter, "Korupcija: " + prije[2] + " → "
                    + igrac.getKorupcija() + " (razina prijetnje " + prijetnja + ")", igrac, voidborn, prijetnja);
        }
    }

    public synchronized boolean imaVrstu(String vrsta) {
        for (int i = 0; i < redovi.size(); i++) {
            if (redovi.get(i).contains("vrsta=\"" + vrsta + "\"")) {
                return true;
            }
        }
        return false;
    }

    public synchronized ArrayList<String> getRedovi() {
        return new ArrayList<>(redovi);
    }

    public synchronized void postaviRedove(ArrayList<String> novi) {
        redovi = new ArrayList<>(novi);
    }

    public synchronized void spremi() {
        try {
            PrintWriter pisac = new PrintWriter(DATOTEKA, "UTF-8");
            pisac.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
            pisac.println("<igra>");
            for (int i = 0; i < redovi.size(); i++) {
                pisac.println(redovi.get(i));
            }
            pisac.println("</igra>");
            pisac.close();
        } catch (Exception e) {
            System.out.println("Greška pri zapisu XML-a: " + e.getMessage());
        }
    }
}