package hr.tvz.voidfall.niti;

import hr.tvz.voidfall.kontroleri.GlavniKontroler;
import hr.tvz.voidfall.logika.VoidfallEngine;
import hr.tvz.voidfall.modeli.AkcijskaKartica;
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
            pricekaj(800);
            if (engine.getBorba().isKraj()) {
                break;
            }
            String poruka = engine.odigrajKorak(i);
            int potez = i + 1;
            AkcijskaKartica kartaIgraca = engine.getZadnjaKartaIgraca();
            AkcijskaKartica kartaVoidborna = engine.getZadnjaKartaVoidborna();
            if (kartaIgraca != null) {
                kontroler.letKarte(kartaIgraca, true, i);
            }
            if (kartaVoidborna != null) {
                kontroler.letKarte(kartaVoidborna, false, i);
            }
            Platform.runLater(() -> {
                kontroler.prikaziPoruku("RUNDA " + engine.getRunda() + ", potez " + potez + "/" + brojKoraka + ":\n" + poruka);
                kontroler.osvjeziSucelje();
            });
        }
        pricekaj(2500);
        Platform.runLater(() -> kontroler.zavrsiPlan());
    }

    private void pricekaj(int milisekundi) {
        try {
            Thread.sleep(milisekundi);
        } catch (InterruptedException e) {
        }
    }
}