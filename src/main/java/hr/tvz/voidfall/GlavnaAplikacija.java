package hr.tvz.voidfall;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GlavnaAplikacija extends Application {

    @Override
    public void start(Stage pozornica) throws Exception {
        FXMLLoader ucitavac = new FXMLLoader(GlavnaAplikacija.class.getResource("glavni_prozor.fxml"));
        Scene scena = new Scene(ucitavac.load());
        pozornica.setTitle("Voidfall");
        pozornica.setScene(scena);
        pozornica.show();
    }

    public static void main(String[] args) {
        launch();
    }
}