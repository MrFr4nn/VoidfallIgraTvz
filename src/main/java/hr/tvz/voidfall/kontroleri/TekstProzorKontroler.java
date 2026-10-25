package hr.tvz.voidfall.kontroleri;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class TekstProzorKontroler {
    @FXML private Label naslovProzora;
    @FXML private TextArea tekstPrikaz;
    @FXML private Button gumbZatvori;

    public void postaviNaslov(String naslov) {
        naslovProzora.setText(naslov);
    }

    public void dodajRedak(String redak) {
        tekstPrikaz.appendText(redak + "\n");
    }

    @FXML
    private void zatvori() {
        Stage prozor = (Stage) gumbZatvori.getScene().getWindow();
        prozor.close();
    }
}