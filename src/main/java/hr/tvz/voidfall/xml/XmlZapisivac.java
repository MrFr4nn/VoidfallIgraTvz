package hr.tvz.voidfall.xml;

import java.io.PrintWriter;
import java.util.ArrayList;

public class XmlZapisivac {
    public static final String DATOTEKA = "igra_zapis.xml";
    private ArrayList<String> redovi = new ArrayList<>();

    public void dodajDogadaj(int runda, String vrsta, String opis,
                             int zdravljeIgraca, int zdravljeVoidborna, int korupcija) {
        opis = opis.replace("&", "i").replace("<", "").replace(">", "").replace("\"", "'");
        String red = "  <dogadaj runda=\"" + runda + "\" vrsta=\"" + vrsta
                + "\" zdravljeIgraca=\"" + zdravljeIgraca
                + "\" zdravljeVoidborna=\"" + zdravljeVoidborna
                + "\" korupcija=\"" + korupcija
                + "\" opis=\"" + opis + "\"/>";
        redovi.add(red);
    }

    public void spremi() {
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