package hr.tvz.voidfall.niti;

import hr.tvz.voidfall.kontroleri.GlavniKontroler;
import hr.tvz.voidfall.logika.VoidfallEngine;
import javafx.application.Platform;

public class VoidbornNit implements Runnable {
    private VoidfallEngine engine;
    private GlavniKontroler kontroler;

    public VoidbornNit(VoidfallEngine engine, GlavniKontroler kontroler) {
        this.engine = engine;
        this.kontroler = kontroler;
    }

    @Override
    public void run() {
        int brojKoraka = engine.getIgrac().getOdabranePlan().size();
        for (int i = 0; i < brojKoraka; i++) {
            pricekaj(1500);
            if (engine.getBorba().isKraj()) {
                break;
            }
            String poruka = engine.odigrajKorak(i);
            Platform.runLater(() -> {
                kontroler.prikaziPoruku(poruka);
                kontroler.osvjeziSucelje();
            });
        }
        pricekaj(1500);
        Platform.runLater(() -> kontroler.zavrsiPlan());
    }

    private void pricekaj(int milisekundi) {
        try {
            Thread.sleep(milisekundi);
        } catch (InterruptedException e) {
        }
    }
}