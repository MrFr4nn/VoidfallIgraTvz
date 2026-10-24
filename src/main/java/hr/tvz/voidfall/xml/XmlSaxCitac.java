package hr.tvz.voidfall.xml;

import java.io.File;
import javax.xml.XMLConstants;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

public class XmlSaxCitac extends DefaultHandler {
    private int brojDogadaja;
    private int brojKarata;
    private int brojNapada;

    @Override
    public void startElement(String uri, String lokalnoIme, String imeElementa, Attributes atributi) {
        if (imeElementa.equals("dogadaj")) {
            brojDogadaja = brojDogadaja + 1;
            String vrsta = atributi.getValue("vrsta");
            if (vrsta.equals("KARTA")) {
                brojKarata = brojKarata + 1;
            }
            if (vrsta.equals("NAPAD")) {
                brojNapada = brojNapada + 1;
            }
        }
    }

    @Override
    public void error(SAXParseException greska) throws SAXException {
        throw greska;
    }

    public String procitaj(String datoteka) {
        brojDogadaja = 0;
        brojKarata = 0;
        brojNapada = 0;
        try {
            SchemaFactory tvornica = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema shema = tvornica.newSchema(XmlSaxCitac.class.getResource("/hr/tvz/voidfall/xml/igra.xsd"));
            SAXParserFactory fabrika = SAXParserFactory.newInstance();
            fabrika.setNamespaceAware(true);
            fabrika.setSchema(shema);
            SAXParser parser = fabrika.newSAXParser();
            parser.parse(new File(datoteka), this);
            return "SAX: XML je ispravan prema XSD-u. Događaja: " + brojDogadaja
                    + " (karata: " + brojKarata + ", napada: " + brojNapada + ")";
        } catch (Exception e) {
            return "SAX: greška - " + e.getMessage();
        }
    }
}