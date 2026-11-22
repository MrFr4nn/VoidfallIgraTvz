package hr.tvz.voidfall.kontroleri;

import hr.tvz.voidfall.modeli.AkcijskaKartica;
import hr.tvz.voidfall.modeli.Sektor;
import java.util.ArrayList;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class PrikazKarata {

    public void prikaziSektor(Sektor s, Label[] oznake) {
        oznake[0].setText("Zdravlje: " + s.getZdravlje() + "/10");
        oznake[1].setText("Obrana: " + s.getObrana());
        oznake[2].setText("Napad: " + s.getNapad());
        oznake[3].setText("Industrija: " + s.getPloca().getIndustrija() + "/3");
        oznake[4].setText("Istraživanje: " + s.getPloca().getIstrazivanje() + "/3");
        oznake[5].setText("Diplomacija: " + s.getPloca().getDiplomacija() + "/3");
    }

    public void prikaziPlan(ArrayList<AkcijskaKartica> plan, Label[] slotovi) {
        for (int i = 0; i < 3; i++) {
            if (i < plan.size()) {
                slotovi[i].setText(plan.get(i).getNaziv());
            } else {
                slotovi[i].setText("prazno");
                slotovi[i].setStyle("");
            }
        }
    }

    public void prikaziRuku(ArrayList<AkcijskaKartica> ruka, ArrayList<AkcijskaKartica> plan,
                            boolean slobodno, Button[] gumbi) {
        for (int i = 0; i < 4; i++) {
            if (i < ruka.size()) {
                AkcijskaKartica karta = ruka.get(i);
                gumbi[i].setText(karta.getVrsta().getKategorija() + "\n" + karta.getNaziv() + "\n" + karta.getOpis());
                gumbi[i].setDisable(!slobodno || plan.contains(karta));
            } else {
                gumbi[i].setText("-");
                gumbi[i].setDisable(true);
            }
        }
    }
}