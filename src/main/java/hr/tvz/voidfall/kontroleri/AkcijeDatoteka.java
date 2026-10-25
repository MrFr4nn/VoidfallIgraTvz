package hr.tvz.voidfall.kontroleri;

import hr.tvz.voidfall.dom.ReplayDom;
import hr.tvz.voidfall.logika.VoidfallEngine;
import hr.tvz.voidfall.refleksija.DokumentacijaRefleksijom;
import hr.tvz.voidfall.refleksija.PopisKlasa;
import hr.tvz.voidfall.serijalizacija.Spremanje;
import hr.tvz.voidfall.serijalizacija.StanjeIgre;
import hr.tvz.voidfall.xml.XmlSaxCitac;
import hr.tvz.voidfall.xml.XmlZapisivac;
import java.io.IOException;
import java.util.ArrayList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AkcijeDatoteka {
    private VoidfallEngine engine;
    private GlavniKontroler kontroler;
    private Spremanje spremanje = new Spremanje();
    private String datotekaSpremanja = "igra.sav";

    public AkcijeDatoteka(VoidfallEngine engine, GlavniKontroler kontroler) {
        this.engine = engine;
        this.kontroler = kontroler;
    }

    public void spremi() {
        if (engine.getBorba().isKraj()) {
            kontroler.prikaziPoruku("Igra je završena, nema što spremiti.");
        } else if (spremanje.spremi(engine.uzmiStanje(), datotekaSpremanja)) {
            kontroler.prikaziPoruku("Igra spremljena (" + datotekaSpremanja + ").");
        } else {
            kontroler.prikaziPoruku("Greška pri spremanju.");
        }
    }

    public void ucitaj() {
        StanjeIgre stanje = spremanje.ucitaj(datotekaSpremanja);
        if (stanje == null) {
            kontroler.prikaziPoruku("Nema spremljene igre ili je datoteka neispravna.");
        } else {
            engine.postaviStanje(stanje);
            kontroler.prikaziPoruku("Igra učitana, runda " + engine.getRunda() + ".");
        }
    }

    private FXMLLoader otvoriProzor(String datoteka, String naslov) throws IOException {
        FXMLLoader ucitavac = new FXMLLoader(getClass().getResource("/hr/tvz/voidfall/" + datoteka));
        Stage prozor = new Stage();
        prozor.setTitle(naslov);
        prozor.setScene(new Scene(ucitavac.load()));
        prozor.show();
        return ucitavac;
    }

    public void replay() {
        engine.spremiZapis();
        String saxPoruka = new XmlSaxCitac().procitaj(XmlZapisivac.DATOTEKA);
        ArrayList<String> redovi = new ReplayDom().ucitaj(XmlZapisivac.DATOTEKA);
        try {
            FXMLLoader ucitavac = otvoriProzor("replay_prozor.fxml", "Replay igre");
            ReplayKontroler prozor = ucitavac.getController();
            prozor.postavi(redovi, saxPoruka);
        } catch (IOException e) {
            kontroler.prikaziPoruku("Greška pri otvaranju prozora: " + e.getMessage());
        }
    }

    public void dokumentacija() {
        try {
            FXMLLoader ucitavac = otvoriProzor("tekst_prozor.fxml", "Dokumentacija (Reflection API)");
            TekstProzorKontroler prozor = ucitavac.getController();
            prozor.postaviNaslov("DOKUMENTACIJA (Reflection API)");
            DokumentacijaRefleksijom dokumentacija =
                    new DokumentacijaRefleksijom(new PopisKlasa().napravi(engine, kontroler));
            prozor.dodajRedak(dokumentacija.napisiSve());
        } catch (IOException e) {
            kontroler.prikaziPoruku("Greška pri otvaranju prozora: " + e.getMessage());
        }
    }
}