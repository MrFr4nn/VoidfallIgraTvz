package hr.tvz.voidfall.dom;

import java.io.File;
import java.util.ArrayList;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.validation.SchemaFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class ReplayDom {
    private String greska = "";

    public String getGreska() {
        return greska;
    }

    private int broj(Element dogadaj, String ime) {
        return Integer.parseInt(dogadaj.getAttribute(ime));
    }

    private int[] sektor(Element dogadaj, String prefiks) {
        return new int[]{broj(dogadaj, prefiks + "Zdravlje"), broj(dogadaj, prefiks + "Napad"),
                broj(dogadaj, prefiks + "Obrana"), broj(dogadaj, prefiks + "Industrija"),
                broj(dogadaj, prefiks + "Istrazivanje"), broj(dogadaj, prefiks + "Diplomacija")};
    }

    public ArrayList<ReplayKorak> ucitaj(String datoteka) {
        ArrayList<ReplayKorak> koraci = new ArrayList<>();
        greska = "";
        try {
            DocumentBuilderFactory tvornica = DocumentBuilderFactory.newInstance();
            tvornica.setNamespaceAware(true);
            tvornica.setSchema(SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI)
                    .newSchema(ReplayDom.class.getResource("/hr/tvz/voidfall/xml/igra.xsd")));
            DocumentBuilder graditelj = tvornica.newDocumentBuilder();
            Document dokument = graditelj.parse(new File(datoteka));
            NodeList dogadaji = dokument.getElementsByTagName("dogadaj");
            for (int i = 0; i < dogadaji.getLength(); i++) {
                Element d = (Element) dogadaji.item(i);
                koraci.add(new ReplayKorak(broj(d, "runda"), d.getAttribute("vrsta"), d.getAttribute("akter"),
                        d.getAttribute("opis"), sektor(d, "igrac"), sektor(d, "voidborn"),
                        broj(d, "korupcija"), broj(d, "prijetnja")));
            }
        } catch (Exception e) {
            greska = "DOM: greška - " + e.getMessage();
        }
        return koraci;
    }
}