package hr.tvz.voidfall.logika;

import hr.tvz.voidfall.modeli.AkcijskaKartica;
import hr.tvz.voidfall.modeli.FrakcijskaPloca;
import hr.tvz.voidfall.modeli.Sektor;

public class UcinakKarte {
    private int najvecaVrijednost = 6;

    public String izvrsi(AkcijskaKartica karta, Sektor svoj, Sektor protivnik, Korupcija korupcija) {
        FrakcijskaPloca ploca = svoj.getPloca();
        switch (karta.getVrsta()) {
            case INDUSTRIJALIZACIJA:
                ploca.setIndustrija(ploca.getIndustrija() + 1);
                promijeniObranu(svoj, 1);
                break;
            case ISTRAZIVANJE:
                ploca.setIstrazivanje(ploca.getIstrazivanje() + 1);
                promijeniNapad(svoj, 1);
                break;
            case DIPLOMACIJA:
                ploca.setDiplomacija(ploca.getDiplomacija() + 1);
                korupcija.promijeni(-1);
                break;
            case POPRAVAK:
                promijeniZdravlje(svoj, 3);
                break;
            case ORBITALNI_NAPAD:
                promijeniZdravlje(protivnik, -2);
                break;
            case UTVRDA:
                promijeniObranu(svoj, 2);
                break;
            case NOVA_TEHNOLOGIJA:
                promijeniNapad(svoj, 2);
                break;
            case PRECIZNI_UDAR:
                promijeniZdravlje(protivnik, -3);
                promijeniObranu(svoj, -1);
                break;
            case SABOTAZA:
                promijeniNapad(protivnik, -1);
                promijeniObranu(protivnik, -1);
                break;
            case TRGOVINA:
                ploca.setIndustrija(ploca.getIndustrija() + 1);
                ploca.setIstrazivanje(ploca.getIstrazivanje() + 1);
                break;
            case MIROVNI_SPORAZUM:
                korupcija.promijeni(-2);
                break;
            case RIZICNI_EKSPERIMENT:
                promijeniNapad(svoj, 3);
                promijeniZdravlje(svoj, -2);
                break;
            case HITNA_POPRAVKA:
                promijeniZdravlje(svoj, 5);
                promijeniObranu(svoj, -1);
                break;
            case ENERGETSKI_STIT:
                promijeniObranu(svoj, 3);
                promijeniNapad(svoj, -1);
                break;
            case ZASJEDA:
                promijeniZdravlje(protivnik, -2);
                promijeniNapad(svoj, 1);
                break;
            case SPIJUNAZA:
                ploca.setIstrazivanje(ploca.getIstrazivanje() + 1);
                promijeniObranu(protivnik, -1);
                break;
            case PRECISCAVANJE:
                korupcija.promijeni(-1);
                promijeniZdravlje(svoj, 2);
                break;
            case KONTAMINIRANI_SIGNAL:
                promijeniNapad(svoj, 2);
                promijeniObranu(svoj, 1);
                korupcija.promijeni(1);
                break;
            case EVAKUACIJA:
                promijeniZdravlje(svoj, 2);
                promijeniObranu(svoj, 1);
                break;
            case PREOPTERECENJE:
                promijeniZdravlje(protivnik, -4);
                promijeniZdravlje(svoj, -2);
                break;
            case KORUPCIJA:
                korupcija.promijeni(1);
                break;
        }
        return karta.getOpis();
    }

    private void promijeniZdravlje(Sektor sektor, int promjena) {
        sektor.setZdravlje(sektor.getZdravlje() + promjena);
    }

    private void promijeniNapad(Sektor sektor, int promjena) {
        sektor.setNapad(ogranici(sektor.getNapad() + promjena));
    }

    private void promijeniObranu(Sektor sektor, int promjena) {
        sektor.setObrana(ogranici(sektor.getObrana() + promjena));
    }

    private int ogranici(int vrijednost) {
        if (vrijednost > najvecaVrijednost) vrijednost = najvecaVrijednost;
        if (vrijednost < 0) vrijednost = 0;
        return vrijednost;
    }
}