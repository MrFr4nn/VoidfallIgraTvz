package hr.tvz.voidfall.niti;

import hr.tvz.voidfall.kontroleri.ReplayKontroler;
import javafx.application.Platform;

public class ReplayNit implements Runnable {
    private ReplayKontroler kontroler;

    public ReplayNit(ReplayKontroler kontroler) {
        this.kontroler = kontroler;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(700);
            } catch (InterruptedException e) {
                return;
            }
            Platform.runLater(() -> kontroler.automatskiKorak());
        }
    }
}