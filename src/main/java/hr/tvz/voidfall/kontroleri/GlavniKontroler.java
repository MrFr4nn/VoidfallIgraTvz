package hr.tvz.voidfall.kontroleri;

import hr.tvz.voidfall.logika.VoidfallEngine;
import hr.tvz.voidfall.modeli.AkcijskaKartica;
import hr.tvz.voidfall.modeli.Sektor;
import hr.tvz.voidfall.niti.PulsNit;
import hr.tvz.voidfall.niti.VoidbornNit;
import hr.tvz.voidfall.niti.ZavrsnaBorbaNit;
import java.util.ArrayList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class GlavniKontroler {
    @FXML private ProgressBar trakaKorupcije, trakaZdravljaIgraca, trakaZdravljaVoidborna;
    @FXML private Label oznakaKorupcije, oznakaRunde, oznakaCiklusa, oznakaDogadaja;
    @FXML private Label oznakaBorbaIgrac, oznakaBorbaVoidborn, igracNeprijatelji;
    @FXML private Label voidbornZdravlje, voidbornObrana, voidbornNapad, voidbornPlan, voidbornPrijetnja;
    @FXML private Label voidbornIndustrija, voidbornIstrazivanje, voidbornDiplomacija;
    @FXML private Label igracZdravlje, igracObrana, igracNapad, igracPlan;
    @FXML private Label igracIndustrija, igracIstrazivanje, igracDiplomacija;
    @FXML private Label uvjetRunde;
    @FXML private HBox okvirIgraca, okvirVoidborna;
    @FXML private VBox borbenaZona;
    @FXML private TextArea dnevnikBorbe;
    @FXML private Button gumbKarta1, gumbKarta2, gumbKarta3, gumbKarta4;
    @FXML private Button gumbIzvrsiPlan, gumbPonistiOdabir;
    @FXML private Button gumbSpremiIgru, gumbReplay, gumbUcitajIgru, gumbDokumentacija, gumbNovaIgra;

    private VoidfallEngine engine = new VoidfallEngine();
    private AkcijeDatoteka akcije;
    private Button[] gumbiKarata;
    private boolean zauzeto = false;

    @FXML
    private void initialize() {
        akcije = new AkcijeDatoteka(engine, this);
        gumbiKarata = new Button[]{gumbKarta1, gumbKarta2, gumbKarta3, gumbKarta4};
        prikaziPoruku("VOIDFALL - izaberite 3 karte");
        osvjeziSucelje();
    }

    private void pokreniNit(Runnable posao) {
        Thread nit = new Thread(posao);
        nit.setDaemon(true);
        nit.start();
    }

    public void pulsirajSektor(boolean igrac) {
        if (igrac) {
            pokreniNit(new PulsNit(okvirIgraca));
        } else {
            pokreniNit(new PulsNit(okvirVoidborna));
        }
    }

    public void prikaziPoruku(String poruka) {
        dnevnikBorbe.appendText(poruka + "\n");
        dnevnikBorbe.setScrollTop(Double.MAX_VALUE);
        oznakaDogadaja.setText(poruka.substring(poruka.lastIndexOf("\n") + 1));
    }

    public void osvjeziSucelje() {
        Sektor igracSektor = engine.getIgrac().getSektor();
        Sektor voidborn = engine.getVoidborn();
        ArrayList<AkcijskaKartica> ruka = engine.getIgrac().getRuka();
        ArrayList<AkcijskaKartica> plan = engine.getIgrac().getOdabranePlan();
        boolean zavrsna = engine.getRunda() > 3;
        boolean kraj = engine.getBorba().isKraj();
        boolean slobodno = !zauzeto && !kraj;

        oznakaKorupcije.setText(igracSektor.getKorupcija() + "/3");
        trakaKorupcije.setProgress(igracSektor.getKorupcija() / 3.0);
        oznakaRunde.setText("Runda: " + engine.getRunda() + "/4");
        oznakaCiklusa.setText("Odabrano: " + plan.size() + "/3");
        uvjetRunde.setText(engine.getKorupcija().opisUvjeta(engine.getRunda()));
        voidbornPrijetnja.setText("Razina prijetnje: " + engine.getKorupcija().razinaPrijetnje());
        trakaZdravljaIgraca.setProgress(igracSektor.getZdravlje() / 10.0);
        trakaZdravljaVoidborna.setProgress(voidborn.getZdravlje() / 10.0);
        oznakaBorbaIgrac.setText("IGRAČ " + igracSektor.getZdravlje() + "/10");
        oznakaBorbaVoidborn.setText("VOIDBORN " + voidborn.getZdravlje() + "/10");
        String neprijatelji = "";
        for (int i = 0; i < voidborn.getNapad(); i++) {
            neprijatelji += "# ";
        }
        igracNeprijatelji.setText("Neprijatelji u sektoru: " + neprijatelji);
        prikaziSektor(voidborn, voidbornZdravlje, voidbornObrana, voidbornNapad,
                voidbornIndustrija, voidbornIstrazivanje, voidbornDiplomacija);
        prikaziSektor(igracSektor, igracZdravlje, igracObrana, igracNapad,
                igracIndustrija, igracIstrazivanje, igracDiplomacija);
        voidbornPlan.setText(engine.planVoidborna());
        igracPlan.setText(engine.planIgraca());

        for (int i = 0; i < 4; i++) {
            if (i < ruka.size()) {
                AkcijskaKartica karta = ruka.get(i);
                gumbiKarata[i].setText(karta.getNaziv() + "\n" + karta.getOpis());
                gumbiKarata[i].setDisable(zauzeto || kraj || plan.contains(karta));
            } else {
                gumbiKarata[i].setText("-");
                gumbiKarata[i].setDisable(true);
            }
        }
        if (zavrsna) {
            gumbIzvrsiPlan.setText("ZAVRŠNA BORBA");
        } else {
            gumbIzvrsiPlan.setText("IZVRŠI PLAN");
        }
        gumbIzvrsiPlan.setDisable(!slobodno || (!zavrsna && plan.size() < 3));
        gumbPonistiOdabir.setDisable(!slobodno || zavrsna);
        gumbNovaIgra.setDisable(zauzeto);
        gumbSpremiIgru.setDisable(!slobodno);
        gumbUcitajIgru.setDisable(zauzeto);
    }

    private void prikaziSektor(Sektor s, Label zdravlje, Label obrana, Label napad,
                               Label industrija, Label istrazivanje, Label diplomacija) {
        zdravlje.setText("Zdravlje: " + s.getZdravlje() + "/10");
        obrana.setText("Obrana: " + s.getObrana());
        napad.setText("Napad: " + s.getNapad());
        industrija.setText("Industrija: " + s.getPloca().getIndustrija() + "/3");
        istrazivanje.setText("Istraživanje: " + s.getPloca().getIstrazivanje() + "/3");
        diplomacija.setText("Diplomacija: " + s.getPloca().getDiplomacija() + "/3");
    }

    @FXML
    private void novaIgra() {
        engine.novaIgra();
        dnevnikBorbe.clear();
        prikaziPoruku("Nova igra - izaberite 3 karte");
        osvjeziSucelje();
    }

    private void odaberiKartu(int indeks) {
        engine.dodajUPlan(indeks);
        osvjeziSucelje();
    }

    @FXML private void odaberiKartu1() { odaberiKartu(0); }
    @FXML private void odaberiKartu2() { odaberiKartu(1); }
    @FXML private void odaberiKartu3() { odaberiKartu(2); }
    @FXML private void odaberiKartu4() { odaberiKartu(3); }
    @FXML private void ponistiOdabir() { engine.ponistiPlan(); osvjeziSucelje(); }

    @FXML
    private void izvrsiPlan() {
        if (zauzeto) {
            return;
        }
        zauzeto = true;
        osvjeziSucelje();
        if (engine.getRunda() <= 3) {
            prikaziPoruku("--- Runda " + engine.getRunda() + " ---");
            pokreniNit(new VoidbornNit(engine, this));
        } else {
            prikaziPoruku("--- ZAVRŠNA BORBA ---");
            pokreniNit(new ZavrsnaBorbaNit(engine, this));
        }
    }

    public void zavrsiPlan() {
        zauzeto = false;
        if (engine.getBorba().isKraj()) {
            zavrsiIgru();
            return;
        }
        prikaziPoruku(engine.zavrsiRundu());
        if (engine.getRunda() <= 3) {
            prikaziPoruku("--- Runda " + engine.getRunda() + ": izaberite 3 karte ---");
        } else {
            prikaziPoruku("--- Razvoj gotov. Pritisnite ZAVRŠNA BORBA ---");
        }
        osvjeziSucelje();
    }

    public void zavrsiIgru() {
        zauzeto = false;
        engine.spremiZapis();
        prikaziPoruku("KRAJ IGRE: " + engine.getBorba().getIshod());
        osvjeziSucelje();
    }

    @FXML private void spremiIgru() { akcije.spremi(); }
    @FXML private void ucitajIgru() { akcije.ucitaj(); osvjeziSucelje(); }
    @FXML private void prikaziDokumentaciju() { akcije.dokumentacija(); }
    @FXML private void pokreniReplay() { akcije.replay(); }
}