package hr.tvz.voidfall.kontroleri;

import hr.tvz.voidfall.logika.VoidfallEngine;
import hr.tvz.voidfall.modeli.AkcijskaKartica;
import hr.tvz.voidfall.modeli.Sektor;
import hr.tvz.voidfall.niti.LetKarte;
import hr.tvz.voidfall.niti.PulsNit;
import hr.tvz.voidfall.niti.VoidbornNit;
import hr.tvz.voidfall.niti.ZavrsnaBorbaNit;
import java.util.ArrayList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class GlavniKontroler {
    @FXML private ProgressBar trakaKorupcije, trakaZdravljaIgraca, trakaZdravljaVoidborna;
    @FXML private Label oznakaKorupcije, oznakaRunde, oznakaCiklusa, oznakaDogadaja, letecaKarta;
    @FXML private Label igracSlot1, igracSlot2, igracSlot3, voidbornSlot1, voidbornSlot2, voidbornSlot3;
    @FXML private Label voidbornZdravlje, voidbornObrana, voidbornNapad, voidbornPrijetnja;
    @FXML private Label voidbornIndustrija, voidbornIstrazivanje, voidbornDiplomacija;
    @FXML private Label igracZdravlje, igracObrana, igracNapad;
    @FXML private Label igracIndustrija, igracIstrazivanje, igracDiplomacija;
    @FXML private Label uvjetRunde;
    @FXML private VBox okvirIgraca, okvirVoidborna;
    @FXML private HBox neprijateljiIgracGore, neprijateljiIgracDolje;
    @FXML private HBox neprijateljiVoidbornGore, neprijateljiVoidbornDolje;
    @FXML private Button gumbKarta1, gumbKarta2, gumbKarta3, gumbKarta4;
    @FXML private Button gumbIzvrsiPlan, gumbPonistiOdabir;
    @FXML private Button gumbSpremiIgru, gumbUcitajIgru, gumbNovaIgra;

    private VoidfallEngine engine = new VoidfallEngine();
    private AkcijeDatoteka akcije;
    private Button[] gumbiKarata;
    private Label[] slotoviIgraca;
    private Label[] slotoviVoidborna;
    private boolean zauzeto = false;

    @FXML
    private void initialize() {
        akcije = new AkcijeDatoteka(engine, this);
        gumbiKarata = new Button[]{gumbKarta1, gumbKarta2, gumbKarta3, gumbKarta4};
        slotoviIgraca = new Label[]{igracSlot1, igracSlot2, igracSlot3};
        slotoviVoidborna = new Label[]{voidbornSlot1, voidbornSlot2, voidbornSlot3};
        prikaziPoruku(uputaRunde());
        osvjeziSucelje();
    }

    private void pokreniNit(Runnable posao) {
        Thread nit = new Thread(posao);
        nit.setDaemon(true);
        nit.start();
    }

    private String uputaRunde() {
        return "RUNDA " + engine.getRunda() + ": odaberi 3 karte i pritisni IZVRŠI PLAN.\n"
                + "Da korupcija ne raste, do kraja runde moraš imati: "
                + engine.getKorupcija().kratkiOpis(engine.getRunda()) + ".";
    }

    public void prikaziPoruku(String poruka) {
        oznakaDogadaja.setText(poruka);
    }

    public void letKarte(AkcijskaKartica karta, boolean igrac, int indeks) {
        Label slot = slotoviVoidborna[indeks];
        if (igrac) {
            slot = slotoviIgraca[indeks];
        }
        new LetKarte(letecaKarta, slot, karta, igrac).leti();
    }

    public void napadnutSektor(boolean igrac) {
        if (igrac) {
            pokreniNit(new PulsNit(okvirIgraca, neprijateljiIgracGore, neprijateljiIgracDolje));
        } else {
            pokreniNit(new PulsNit(okvirVoidborna, neprijateljiVoidbornGore, neprijateljiVoidbornDolje));
        }
    }

    private void sakrijVoidbornovPlan() {
        for (int i = 0; i < 3; i++) {
            slotoviVoidborna[i].setText("?");
            slotoviVoidborna[i].setStyle("");
        }
    }

    public void osvjeziSucelje() {
        Sektor igracSektor = engine.getIgrac().getSektor();
        Sektor voidborn = engine.getVoidborn();
        ArrayList<AkcijskaKartica> ruka = engine.getIgrac().getRuka();
        ArrayList<AkcijskaKartica> plan = engine.getIgrac().getOdabranePlan();
        boolean zavrsna = engine.getRunda() > 3;
        boolean slobodno = !zauzeto && !engine.getBorba().isKraj();

        oznakaKorupcije.setText(igracSektor.getKorupcija() + "/3");
        trakaKorupcije.setProgress(igracSektor.getKorupcija() / 3.0);
        oznakaRunde.setText("Runda: " + engine.getRunda() + "/4");
        oznakaCiklusa.setText("Odabrano: " + plan.size() + "/3");
        uvjetRunde.setText(engine.getKorupcija().opisUvjeta(engine.getRunda()));
        voidbornPrijetnja.setText("Razina prijetnje: " + engine.getKorupcija().razinaPrijetnje());
        trakaZdravljaIgraca.setProgress(igracSektor.getZdravlje() / 10.0);
        trakaZdravljaVoidborna.setProgress(voidborn.getZdravlje() / 10.0);
        prikaziSektor(voidborn, voidbornZdravlje, voidbornObrana, voidbornNapad,
                voidbornIndustrija, voidbornIstrazivanje, voidbornDiplomacija);
        prikaziSektor(igracSektor, igracZdravlje, igracObrana, igracNapad,
                igracIndustrija, igracIstrazivanje, igracDiplomacija);

        for (int i = 0; i < 3; i++) {
            if (i < plan.size()) {
                slotoviIgraca[i].setText(plan.get(i).getNaziv());
            } else {
                slotoviIgraca[i].setText("prazno");
                slotoviIgraca[i].setStyle("");
            }
        }
        for (int i = 0; i < 4; i++) {
            if (i < ruka.size()) {
                AkcijskaKartica karta = ruka.get(i);
                gumbiKarata[i].setText(karta.getVrsta().getKategorija() + "\n"
                        + karta.getNaziv() + "\n" + karta.getOpis());
                gumbiKarata[i].setDisable(!slobodno || plan.contains(karta));
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
        sakrijVoidbornovPlan();
        prikaziPoruku(uputaRunde());
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
        sakrijVoidbornovPlan();
        osvjeziSucelje();
        if (engine.getRunda() <= 3) {
            prikaziPoruku("RUNDA " + engine.getRunda() + ": karte se bacaju redom iz plana...");
            pokreniNit(new VoidbornNit(engine, this));
        } else {
            prikaziPoruku("ZAVRŠNA BORBA!");
            pokreniNit(new ZavrsnaBorbaNit(engine, this));
        }
    }

    public void zavrsiPlan() {
        zauzeto = false;
        if (engine.getBorba().isKraj()) {
            zavrsiIgru();
            return;
        }
        String poruka = engine.zavrsiRundu() + "\n";
        if (engine.getRunda() <= 3) {
            poruka += uputaRunde();
        } else {
            poruka += "Razvoj je gotov. Pritisni ZAVRŠNA BORBA.";
        }
        prikaziPoruku(poruka);
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