package hr.tvz.voidfall.modeli;

import java.io.Serializable;

public class FrakcijskaPloca implements Serializable {
    private int industrija;
    private int istrazivanje;
    private int diplomacija;

    public int getIndustrija() {
      return industrija;}

    public void setIndustrija(int industrija) {
        if (industrija > 3) industrija = 3;
        if (industrija < 0) industrija = 0;
        this.industrija = industrija;
    }

    public int getIstrazivanje() {
        return istrazivanje;
    }

    public void setIstrazivanje(int istrazivanje) {
        if (istrazivanje > 3) istrazivanje = 3;
        if (istrazivanje < 0) istrazivanje = 0;
        this.istrazivanje = istrazivanje;
    }

    public int getDiplomacija() {
        return diplomacija;
    }

    public void setDiplomacija(int diplomacija) {
        if (diplomacija > 3) diplomacija = 3;
        if (diplomacija < 0) diplomacija = 0;
        this.diplomacija = diplomacija;
    }
}
