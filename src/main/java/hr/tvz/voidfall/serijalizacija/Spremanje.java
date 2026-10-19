package hr.tvz.voidfall.serijalizacija;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Spremanje {

    public boolean spremi(StanjeIgre stanje, String datoteka) {
        try {
            ObjectOutputStream izlaz = new ObjectOutputStream(new FileOutputStream(datoteka));
            izlaz.writeObject(stanje);
            izlaz.close();
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public StanjeIgre ucitaj(String datoteka) {
        try {
            ObjectInputStream ulaz = new ObjectInputStream(new FileInputStream(datoteka));
            StanjeIgre stanje = (StanjeIgre) ulaz.readObject();
            ulaz.close();
            return stanje;
        } catch (Exception e) {
            return null;
        }
    }
}