package hr.tvz.voidfall.niti;

import hr.tvz.voidfall.kontroleri.GlavniKontroler;
import hr.tvz.voidfall.logika.VoidfallEngine;
import javafx.application.Platform;

public class ZavrsnaBorbaNit implements Runnable {
    private VoidfallEngine engine;
    private GlavniKontroler kontroler;

    public ZavrsnaBorbaNit(VoidfallEngine engine, GlavniKontroler kontroler) {
        this.engine = engine;
        this.kontroler = kontroler;
    }

    @Override
    public void run() {
        boolean igracNapada = Math.random() < 0.5;
        int brojNapada = 0;
        while (!engine.getBorba().isKraj() && !engine.getBorba().bezSanse() && brojNapada < 30) {
            pricekaj(1500);
            String poruka = engine.napadni(igracNapada);
            boolean napadnutJeIgrac = !igracNapada;
            Platform.runLater(() -> {
                kontroler.prikaziPoruku(poruka);
                kontroler.osvjeziSucelje();
                kontroler.napadnutSektor(napadnutJeIgrac);
            });
            igracNapada = !igracNapada;
            brojNapada = brojNapada + 1;
        }
        engine.getBorba().odlukaPoZdravlju();
        pricekaj(2500);
        Platform.runLater(() -> kontroler.zavrsiIgru());
    }

    private void pricekaj(int milisekundi) {
        try {
            Thread.sleep(milisekundi);
        } catch (InterruptedException e) {
        }
    }
}