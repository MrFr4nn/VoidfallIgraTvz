package hr.tvz.voidfall.niti;

import hr.tvz.voidfall.modeli.AkcijskaKartica;
import javafx.application.Platform;
import javafx.scene.control.Label;

public class LetKarte {
    private Label leteca;
    private Label slot;
    private AkcijskaKartica karta;
    private int pocetak;

    public LetKarte(Label leteca, Label slot, AkcijskaKartica karta, boolean odIgraca) {
        this.leteca = leteca;
        this.slot = slot;
        this.karta = karta;
        if (odIgraca) {
            pocetak = 230;
        } else {
            pocetak = -230;
        }
    }

    public void leti() {
        String tekst = karta.getVrsta().getKategorija() + "\n" + karta.getNaziv() + "\n" + karta.getOpis();
        Platform.runLater(() -> {
            leteca.setText(tekst);
            leteca.setTranslateY(pocetak);
            leteca.setVisible(true);
        });
        pricekaj(1100);
        for (int korak = 1; korak <= 20; korak++) {
            double y = pocetak - pocetak * korak / 20.0;
            Platform.runLater(() -> leteca.setTranslateY(y));
            pricekaj(25);
        }
        pricekaj(1100);
        String kratko = karta.getVrsta().getKategorija() + "\n" + karta.getNaziv();
        Platform.runLater(() -> {
            slot.setText(kratko);
            slot.setStyle("-fx-background-color: #d0d0d0;");
            leteca.setVisible(false);
        });
        pricekaj(300);
    }

    private void pricekaj(int milisekundi) {
        try {
            Thread.sleep(milisekundi);
        } catch (InterruptedException e) {

        }
    }
}