package hr.tvz.voidfall.modeli;

import java.io.Serializable;
import java.util.ArrayList;

public class Igrac implements Serializable {
    private String ime;
    private Sektor sektor;
    private ArrayList<AkcijskaKartica> ruka;
    private ArrayList<AkcijskaKartica> odabranePlan;
}