package hr.tvz.voidfall.kontroleri;

import hr.tvz.voidfall.niti.ReplayNit;
import java.util.ArrayList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class ReplayKontroler {
    @FXML private Label oznakaSax, oznakaPoteza, oznakaTrenutnog;
    @FXML private TextArea tekstReplaya;
    @FXML private Button gumbPrethodni, gumbPauza, gumbSljedeci, gumbZatvori;

    private ArrayList<String> redovi = new ArrayList<>();
    private int trenutni = 0;
    private boolean pauza = false;
    private Thread nit;

    public void postavi(ArrayList<String> redovi, String saxPoruka) {
        this.redovi = redovi;
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
        if (trenutni < redovi.size()) {
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

    private void osvjezi() {
        String tekst = "";
        for (int i = 0; i < trenutni; i++) {
            tekst += redovi.get(i) + "\n";
        }
        tekstReplaya.setText(tekst);
        tekstReplaya.setScrollTop(Double.MAX_VALUE);
        oznakaPoteza.setText("Potez " + trenutni + " / " + redovi.size());
        if (trenutni > 0) {
            oznakaTrenutnog.setText(redovi.get(trenutni - 1));
        } else {
            oznakaTrenutnog.setText("Replay počinje");
        }
        if (pauza) {
            gumbPauza.setText("NASTAVI AUTOMATSKI");
        } else {
            gumbPauza.setText("PAUZA (ručno)");
        }
        gumbPrethodni.setDisable(trenutni == 0);
        gumbSljedeci.setDisable(trenutni == redovi.size());
    }
}