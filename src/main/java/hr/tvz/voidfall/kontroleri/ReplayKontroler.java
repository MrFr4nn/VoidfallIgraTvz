package hr.tvz.voidfall.kontroleri;

import hr.tvz.voidfall.dom.ReplayKorak;
import hr.tvz.voidfall.niti.ReplayNit;
import java.util.ArrayList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class ReplayKontroler {
    @FXML private Label oznakaSax, oznakaPoteza, oznakaTrenutnog, oznakaRunde, oznakaKorupcije;
    @FXML private Label igracZdravlje, igracNapad, igracObrana, igracIndustrija, igracIstrazivanje, igracDiplomacija;
    @FXML private Label voidbornZdravlje, voidbornNapad, voidbornObrana, voidbornIndustrija;
    @FXML private Label voidbornIstrazivanje, voidbornDiplomacija;
    @FXML private ProgressBar trakaIgraca, trakaVoidborna, trakaKorupcije;
    @FXML private TextArea tekstReplaya;
    @FXML private Button gumbPrethodni, gumbPauza, gumbSljedeci, gumbZatvori;

    private ArrayList<ReplayKorak> koraci = new ArrayList<>();
    private int trenutni = 0;
    private boolean pauza = false;
    private Thread nit;

    public void postavi(ArrayList<ReplayKorak> koraci, String saxPoruka) {
        this.koraci = koraci;
        oznakaSax.setText(saxPoruka);
        osvjezi();
        nit = new Thread(new ReplayNit(this));
        nit.setDaemon(true);
        nit.start();
        Stage prozor = (Stage) gumbZatvori.getScene().getWindow();
        prozor.setOnHidden(dogadaj -> nit.interrupt());
    }

    public void automatskiKorak() {
        if (!pauza) {
            sljedeciPotez();
        }
    }

    @FXML
    private void sljedeciPotez() {
        if (trenutni < koraci.size()) {
            trenutni = trenutni + 1;
        }
        osvjezi();
    }

    @FXML
    private void prethodniPotez() {
        pauza = true;
        if (trenutni > 0) {
            trenutni = trenutni - 1;
        }
        osvjezi();
    }

    @FXML
    private void pauziraj() {
        pauza = !pauza;
        osvjezi();
    }

    @FXML
    private void zatvori() {
        Stage prozor = (Stage) gumbZatvori.getScene().getWindow();
        prozor.close();
    }

    private void prikaziSektor(int[] v, ProgressBar traka, Label[] oznake) {
        String[] nazivi = {"Zdravlje: ", "Napad: ", "Obrana: ", "Industrija: ", "Istraživanje: ", "Diplomacija: "};
        for (int i = 0; i < 6; i++) {
            oznake[i].setText(nazivi[i] + v[i]);
        }
        oznake[0].setText("Zdravlje: " + v[0] + "/10");
        traka.setProgress(v[0] / 10.0);
    }

    private void osvjezi() {
        int[] pocetno = {10, 0, 0, 0, 0, 0};
        int[] igrac = pocetno;
        int[] voidborn = pocetno;
        int korupcija = 0;
        int prijetnja = 1;
        int runda = 1;
        String tekst = "";
        for (int i = 0; i < trenutni; i++) {
            tekst += koraci.get(i).tekst() + "\n";
        }
        if (trenutni > 0) {
            ReplayKorak korak = koraci.get(trenutni - 1);
            igrac = korak.getIgrac();
            voidborn = korak.getVoidborn();
            korupcija = korak.getKorupcija();
            prijetnja = korak.getPrijetnja();
            runda = korak.getRunda();
            oznakaTrenutnog.setText(korak.tekst());
        } else {
            oznakaTrenutnog.setText("Replay počinje");
        }
        prikaziSektor(igrac, trakaIgraca, new Label[]{igracZdravlje, igracNapad, igracObrana,
                igracIndustrija, igracIstrazivanje, igracDiplomacija});
        prikaziSektor(voidborn, trakaVoidborna, new Label[]{voidbornZdravlje, voidbornNapad, voidbornObrana,
                voidbornIndustrija, voidbornIstrazivanje, voidbornDiplomacija});
        oznakaRunde.setText("Runda: " + runda + "/4");
        oznakaKorupcije.setText("Korupcija: " + korupcija + "/3   Razina prijetnje: " + prijetnja);
        trakaKorupcije.setProgress(korupcija / 3.0);
        tekstReplaya.setText(tekst);
        tekstReplaya.setScrollTop(Double.MAX_VALUE);
        oznakaPoteza.setText("Potez " + trenutni + " / " + koraci.size());
        gumbPauza.setText(pauza ? "NASTAVI AUTOMATSKI" : "PAUZA (ručno)");
        gumbPrethodni.setDisable(trenutni == 0);
        gumbSljedeci.setDisable(trenutni == koraci.size());
    }
}