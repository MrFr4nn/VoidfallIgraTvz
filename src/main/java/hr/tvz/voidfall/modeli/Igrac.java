package hr.tvz.voidfall.modeli;

import java.util.ArrayList;
import java.util.List;

public class Igrac extends Frakcija {

    private List<AkcijskaKartica> trenutniPlan;

    public Igrac(String naziv) {
        super(naziv);
        this.trenutniPlan = new ArrayList<>();
    }
}