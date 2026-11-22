package hr.tvz.voidfall.logika;

import hr.tvz.voidfall.modeli.AkcijskaKartica;
import hr.tvz.voidfall.modeli.FrakcijskaPloca;
import hr.tvz.voidfall.modeli.Sektor;

public class UcinakKarte {
    private String napomene = "";
    private Korupcija trenutnaKorupcija;

    public String izvrsi(AkcijskaKartica karta, Sektor svoj, Sektor protivnik, Korupcija korupcija) {
        napomene = "";
        trenutnaKorupcija = korupcija;
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
                promijeniKorupciju(korupcija, -1);
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
                promijeniKorupciju(korupcija, -2);
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
                promijeniKorupciju(korupcija, -1);
                promijeniZdravlje(svoj, 2);
                break;
            case KONTAMINIRANI_SIGNAL:
                promijeniNapad(svoj, 2);
                promijeniObranu(svoj, 1);
                promijeniKorupciju(korupcija, 1);
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
                promijeniKorupciju(korupcija, 1);
                break;
        }
        return karta.getOpis() + napomene;
    }

    private void dodajNapomenu(String tekst) {
        if (napomene.isEmpty()) {
            napomene = " [" + tekst + "]";
        } else {
            napomene = napomene.substring(0, napomene.length() - 1) + " " + tekst + "]";
        }
    }

    private void promijeniZdravlje(Sektor sektor, int promjena) {
        if (sektor.getZdravlje() + promjena > 10) {
            String cije = "Voidborna";
            if (trenutnaKorupcija.jeIgracev(sektor)) {
                cije = "igrača";
            }
            dodajNapomenu("Zdravlje " + cije + " je na maksimumu (10/10), ne može više.");
        }
        sektor.setZdravlje(sektor.getZdravlje() + promjena);
    }

    private void promijeniKorupciju(Korupcija korupcija, int promjena) {
        if (korupcija.trenutna() + promjena > 3) {
            dodajNapomenu("Korupcija ne može ići iznad 3.");
        } else if (korupcija.trenutna() + promjena < 0) {
            dodajNapomenu("Korupcija ne može ići u minus (najmanje 0).");
        }
        korupcija.promijeni(promjena);
    }

    private void promijeniNapad(Sektor sektor, int promjena) {
        sektor.setNapad(sektor.getNapad() + promjena);
    }

    private void promijeniObranu(Sektor sektor, int promjena) {
        sektor.setObrana(sektor.getObrana() + promjena);
    }
}