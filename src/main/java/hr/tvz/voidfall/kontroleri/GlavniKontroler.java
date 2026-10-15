package hr.tvz.voidfall.kontroleri;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;

public class GlavniKontroler {
    @FXML private ProgressBar trakaKorupcije;
    @FXML private Label oznakaKorupcije, oznakaRunde, oznakaCiklusa;
    @FXML private Label voidbornZdravlje, voidbornObrana, voidbornNapad, voidbornPlan, voidbornPrijetnja;
    @FXML private Label voidbornIndustrija, voidbornIstrazivanje, voidbornDiplomacija;
    @FXML private Label igracZdravlje, igracObrana, igracNapad, igracPlan;
    @FXML private Label igracIndustrija, igracIstrazivanje, igracDiplomacija;
    @FXML private Label uvjetRunde;
    @FXML private VBox borbenaZona;
    @FXML private TextArea dnevnikBorbe;
    @FXML private Button gumbKarta1, gumbKarta2, gumbKarta3, gumbKarta4;
    @FXML private Button gumbIzvrsiPlan, gumbPonistiOdabir;
    @FXML private Button gumbSpremiIgru, gumbReplay, gumbUcitajIgru, gumbDokumentacija, gumbNovaIgra;

    @FXML
    private void initialize() {
        napisiUBorbenuZonu("VOIDFALL - izaberite 3 karte");
    }

    private void napisiUBorbenuZonu(String poruka) {
        dnevnikBorbe.appendText(poruka + "\n");
    }

    @FXML private void novaIgra() { napisiUBorbenuZonu("Nova igra"); }
    @FXML private void odaberiKartu1() { napisiUBorbenuZonu("Karta 1"); }
    @FXML private void odaberiKartu2() { napisiUBorbenuZonu("Karta 2"); }
    @FXML private void odaberiKartu3() { napisiUBorbenuZonu("Karta 3"); }
    @FXML private void odaberiKartu4() { napisiUBorbenuZonu("Karta 4"); }
    @FXML private void izvrsiPlan() { napisiUBorbenuZonu("Izvrši plan"); }
    @FXML private void ponistiOdabir() { napisiUBorbenuZonu("Poništi odabir"); }
    @FXML private void spremiIgru() { napisiUBorbenuZonu("Spremi igru"); }
    @FXML private void pokreniReplay() { napisiUBorbenuZonu("Replay"); }
    @FXML private void ucitajIgru() { napisiUBorbenuZonu("Učitaj igru"); }
    @FXML private void prikaziDokumentaciju() { napisiUBorbenuZonu("Dokumentacija"); }
}