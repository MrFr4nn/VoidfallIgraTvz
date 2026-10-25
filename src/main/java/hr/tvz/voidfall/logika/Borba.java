package hr.tvz.voidfall.logika;

import hr.tvz.voidfall.modeli.Sektor;

public class Borba {
    private Sektor igrac;
    private Sektor voidborn;
    private boolean kraj;
    private String ishod;

    public Borba(Sektor igrac, Sektor voidborn) {
        this.igrac = igrac;
        this.voidborn = voidborn;
        kraj = false;
        ishod = "";
    }

    public int izracunajStetu(Sektor napadac, Sektor branitelj) {
        int steta = napadac.getNapad() - branitelj.getObrana();
        if (steta < 0) {
            steta = 0;
        }
        return steta;
    }

    public String napadni(boolean igracNapada) {
        Sektor napadac = voidborn;
        Sektor branitelj = igrac;
        String ime = "Voidborn";
        if (igracNapada) {
            napadac = igrac;
            branitelj = voidborn;
            ime = "Igrač";
        }
        int steta = izracunajStetu(napadac, branitelj);
        String poruka = ime + " napada (napad " + napadac.getNapad() + ", obrana branitelja "
                + branitelj.getObrana() + "): šteta " + steta + ".";
        if (steta == 0 && branitelj.getObrana() > 0) {
            branitelj.setObrana(branitelj.getObrana() - 1);
            poruka += " Obrana popušta na " + branitelj.getObrana() + ".";
        }
        branitelj.setZdravlje(branitelj.getZdravlje() - steta);
        provjeriKraj();
        return poruka;
    }

    public boolean bezSanse() {
        return igrac.getNapad() == 0 && voidborn.getNapad() == 0;
    }

    public void provjeriKraj() {
        int zdravljeIgraca = igrac.getZdravlje();
        int zdravljeVoidborna = voidborn.getZdravlje();
        if (zdravljeIgraca == 0 && zdravljeVoidborna == 0) {
            kraj = true;
            ishod = "NERIJEŠENO - oba sektora su uništena.";
        } else if (zdravljeVoidborna == 0) {
            kraj = true;
            ishod = "POBJEDA - Voidbornov sektor je uništen.";
        } else if (zdravljeIgraca == 0) {
            kraj = true;
            ishod = "PORAZ - igračev sektor je uništen.";
        }
    }

    public void odlukaPoZdravlju() {
        if (kraj) {
            return;
        }
        kraj = true;
        if (igrac.getZdravlje() > voidborn.getZdravlje()) {
            ishod = "POBJEDA - igrač ima više zdravlja.";
        } else if (igrac.getZdravlje() < voidborn.getZdravlje()) {
            ishod = "PORAZ - Voidborn ima više zdravlja.";
        } else {
            ishod = "NERIJEŠENO - jednako zdravlje.";
        }
    }

    public boolean isKraj() {
        return kraj;
    }

    public String getIshod() {
        return ishod;
    }
}