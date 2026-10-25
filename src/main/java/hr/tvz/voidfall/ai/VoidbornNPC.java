package hr.tvz.voidfall.ai;

import hr.tvz.voidfall.modeli.Sektor;
import hr.tvz.voidfall.modeli.VrstaKartice;
import hr.tvz.voidfall.refleksija.OpisAI;
import java.util.ArrayList;
import java.util.Random;

public class VoidbornNPC {
    private int niskoZdravlje;
    private int igracUOpasnosti;
    private int dovoljnoNapada;
    private Random slucajni = new Random();

    public VoidbornNPC() {
        this(4, 3, 2);
    }

    public VoidbornNPC(int niskoZdravlje, int igracUOpasnosti, int dovoljnoNapada) {
        this.niskoZdravlje = niskoZdravlje;
        this.igracUOpasnosti = igracUOpasnosti;
        this.dovoljnoNapada = dovoljnoNapada;
    }

    @OpisAI("Nužda (70% šanse): popravak ili napad. Inače nasumična karta iz skupa za razinu prijetnje.")
    public VrstaKartice odaberiKartu(Sektor svoj, Sektor igrac, int razinaPrijetnje) {
        if (svoj.getZdravlje() <= niskoZdravlje && slucajni.nextInt(100) < 70) {
            return VrstaKartice.POPRAVAK;
        }
        if (igrac.getZdravlje() <= igracUOpasnosti && slucajni.nextInt(100) < 70) {
            return VrstaKartice.ORBITALNI_NAPAD;
        }
        ArrayList<VrstaKartice> moguce;
        if (razinaPrijetnje == 1) {
            moguce = razvojneKarte();
        } else if (razinaPrijetnje == 2) {
            moguce = podmukleKarte();
        } else {
            moguce = napadneKarte(svoj);
        }
        return moguce.get(slucajni.nextInt(moguce.size()));
    }

    @OpisAI("Razina prijetnje 1: Voidborn uglavnom razvija sektor, ponekad ispipa napadom.")
    public ArrayList<VrstaKartice> razvojneKarte() {
        ArrayList<VrstaKartice> lista = new ArrayList<>();
        lista.add(VrstaKartice.INDUSTRIJALIZACIJA);
        lista.add(VrstaKartice.ISTRAZIVANJE);
        lista.add(VrstaKartice.UTVRDA);
        lista.add(VrstaKartice.NOVA_TEHNOLOGIJA);
        lista.add(VrstaKartice.TRGOVINA);
        lista.add(VrstaKartice.ENERGETSKI_STIT);
        lista.add(VrstaKartice.ORBITALNI_NAPAD);
        return lista;
    }

    @OpisAI("Razina prijetnje 2: Voidborn širi korupciju i slabi protivnika.")
    public ArrayList<VrstaKartice> podmukleKarte() {
        ArrayList<VrstaKartice> lista = new ArrayList<>();
        lista.add(VrstaKartice.KORUPCIJA);
        lista.add(VrstaKartice.KORUPCIJA);
        lista.add(VrstaKartice.KONTAMINIRANI_SIGNAL);
        lista.add(VrstaKartice.SABOTAZA);
        lista.add(VrstaKartice.SPIJUNAZA);
        lista.add(VrstaKartice.ZASJEDA);
        lista.add(VrstaKartice.NOVA_TEHNOLOGIJA);
        lista.add(VrstaKartice.INDUSTRIJALIZACIJA);
        return lista;
    }

    @OpisAI("Razina prijetnje 3: Voidborn napada; ako mu je istraživanje nisko, još razvija napad.")
    public ArrayList<VrstaKartice> napadneKarte(Sektor svoj) {
        ArrayList<VrstaKartice> lista = new ArrayList<>();
        lista.add(VrstaKartice.ORBITALNI_NAPAD);
        lista.add(VrstaKartice.PRECIZNI_UDAR);
        lista.add(VrstaKartice.ZASJEDA);
        lista.add(VrstaKartice.PREOPTERECENJE);
        lista.add(VrstaKartice.RIZICNI_EKSPERIMENT);
        lista.add(VrstaKartice.KORUPCIJA);
        lista.add(VrstaKartice.SABOTAZA);
        if (svoj.getPloca().getIstrazivanje() < dovoljnoNapada) {
            lista.add(VrstaKartice.ISTRAZIVANJE);
            lista.add(VrstaKartice.NOVA_TEHNOLOGIJA);
        }
        return lista;
    }
}