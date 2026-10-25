package hr.tvz.voidfall.dom;

import java.io.File;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class ReplayDom {

    public ArrayList<String> ucitaj(String datoteka) {
        ArrayList<String> redovi = new ArrayList<>();
        try {
            DocumentBuilderFactory tvornica = DocumentBuilderFactory.newInstance();
            DocumentBuilder graditelj = tvornica.newDocumentBuilder();
            Document dokument = graditelj.parse(new File(datoteka));
            NodeList dogadaji = dokument.getElementsByTagName("dogadaj");
            for (int i = 0; i < dogadaji.getLength(); i++) {
                Element dogadaj = (Element) dogadaji.item(i);
                redovi.add("Runda " + dogadaj.getAttribute("runda") + " | " + dogadaj.getAttribute("opis")
                        + "  [zdravlje igrača " + dogadaj.getAttribute("zdravljeIgraca")
                        + ", Voidborna " + dogadaj.getAttribute("zdravljeVoidborna")
                        + ", korupcija " + dogadaj.getAttribute("korupcija") + "]");
            }
        } catch (Exception e) {
            redovi.add("DOM: greška - " + e.getMessage());
        }
        return redovi;
    }
}