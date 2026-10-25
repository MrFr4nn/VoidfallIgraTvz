package hr.tvz.voidfall.niti;

import javafx.application.Platform;
import javafx.scene.Node;

public class PulsNit implements Runnable {
    private Node sektor;

    public PulsNit(Node sektor) {
        this.sektor = sektor;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            Platform.runLater(() -> sektor.setStyle("-fx-background-color: #ff6666;"));
            pricekaj(200);
            Platform.runLater(() -> sektor.setStyle(""));
            pricekaj(200);
        }
    }

    private void pricekaj(int milisekundi) {
        try {
            Thread.sleep(milisekundi);
        } catch (InterruptedException e) {
        }
    }
}