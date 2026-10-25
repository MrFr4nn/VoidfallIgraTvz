package hr.tvz.voidfall.refleksija;

import hr.tvz.voidfall.GlavnaAplikacija;
import hr.tvz.voidfall.ai.VoidbornNPC;
import hr.tvz.voidfall.dom.ReplayDom;
import hr.tvz.voidfall.kontroleri.AkcijeDatoteka;
import hr.tvz.voidfall.kontroleri.GlavniKontroler;
import hr.tvz.voidfall.kontroleri.ReplayKontroler;
import hr.tvz.voidfall.kontroleri.TekstProzorKontroler;
import hr.tvz.voidfall.logika.Borba;
import hr.tvz.voidfall.logika.Korupcija;
import hr.tvz.voidfall.logika.Spil;
import hr.tvz.voidfall.logika.UcinakKarte;
import hr.tvz.voidfall.logika.VoidfallEngine;
import hr.tvz.voidfall.modeli.AkcijskaKartica;
import hr.tvz.voidfall.modeli.FrakcijskaPloca;
import hr.tvz.voidfall.modeli.Igrac;
import hr.tvz.voidfall.modeli.Sektor;
import hr.tvz.voidfall.modeli.VrstaKartice;
import hr.tvz.voidfall.niti.PulsNit;
import hr.tvz.voidfall.niti.ReplayNit;
import hr.tvz.voidfall.niti.VoidbornNit;
import hr.tvz.voidfall.niti.ZavrsnaBorbaNit;
import hr.tvz.voidfall.serijalizacija.Spremanje;
import hr.tvz.voidfall.serijalizacija.StanjeIgre;
import hr.tvz.voidfall.xml.XmlSaxCitac;
import hr.tvz.voidfall.xml.XmlZapisivac;
import java.util.LinkedHashMap;

public class PopisKlasa {

    public LinkedHashMap<Class<?>, Object[]> napravi(VoidfallEngine engine, GlavniKontroler kontroler) {
        LinkedHashMap<Class<?>, Object[]> popis = new LinkedHashMap<>();
        popis.put(GlavnaAplikacija.class, new Object[]{});

        popis.put(Sektor.class, new Object[]{});
        popis.put(FrakcijskaPloca.class, new Object[]{});
        popis.put(Igrac.class, new Object[]{"Probni igrač"});
        popis.put(AkcijskaKartica.class, new Object[]{"Probna karta", VrstaKartice.POPRAVAK, "+3 zdravlje"});
        popis.put(VrstaKartice.class, new Object[]{});

        popis.put(VoidbornNPC.class, new Object[]{5, 2, 1});

        popis.put(VoidfallEngine.class, new Object[]{});
        popis.put(Spil.class, new Object[]{});
        popis.put(UcinakKarte.class, new Object[]{});
        popis.put(Korupcija.class, new Object[]{new Sektor()});
        popis.put(Borba.class, new Object[]{new Sektor(), new Sektor()});

        popis.put(VoidbornNit.class, new Object[]{new VoidfallEngine(), null});
        popis.put(ZavrsnaBorbaNit.class, new Object[]{new VoidfallEngine(), null});
        popis.put(ReplayNit.class, new Object[]{null});
        popis.put(PulsNit.class, new Object[]{null});

        popis.put(StanjeIgre.class, new Object[]{new Igrac("Probni"), new Sektor(), 1});
        popis.put(Spremanje.class, new Object[]{});
        popis.put(XmlZapisivac.class, new Object[]{});
        popis.put(XmlSaxCitac.class, new Object[]{});
        popis.put(ReplayDom.class, new Object[]{});

        popis.put(OpisAI.class, new Object[]{});
        popis.put(DokumentacijaRefleksijom.class, new Object[]{new LinkedHashMap<Class<?>, Object[]>()});
        popis.put(PopisKlasa.class, new Object[]{});

        popis.put(GlavniKontroler.class, new Object[]{});
        popis.put(AkcijeDatoteka.class, new Object[]{engine, kontroler});
        popis.put(ReplayKontroler.class, new Object[]{});
        popis.put(TekstProzorKontroler.class, new Object[]{});
        return popis;
    }
}