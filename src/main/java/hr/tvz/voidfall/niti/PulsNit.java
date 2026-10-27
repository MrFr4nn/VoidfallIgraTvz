package hr.tvz.voidfall.niti;

import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class PulsNit implements Runnable {
    private Node sektor;
    private HBox redGore;
    private HBox redDolje;

    public PulsNit(Node sektor, HBox redGore, HBox redDolje) {
        this.sektor = sektor;
        this.redGore = redGore;
        this.redDolje = redDolje;
    }

    @Override
    public void run() {
        for (int i = 0; i < 4; i++) {
            HBox red = redGore;
            if (i % 2 == 1) {
                red = redDolje;
            }
            HBox odabrani = red;
            Platform.runLater(() -> {
                Label neprijatelj = new Label("▲");
                neprijatelj.setStyle("-fx-text-fill: red; -fx-font-size: 20px;");
                odabrani.getChildren().add(neprijatelj);
                sektor.setStyle("-fx-background-color: #ff6666;");
            });
            pricekaj(200);
            Platform.runLater(() -> sektor.setStyle(""));
            pricekaj(200);
        }
        pricekaj(500);
        Platform.runLater(() -> {
            redGore.getChildren().clear();
            redDolje.getChildren().clear();
        });
    }

    private void pricekaj(int milisekundi) {
        try {
            Thread.sleep(milisekundi);
        } catch (InterruptedException e) {
        }
    }
}