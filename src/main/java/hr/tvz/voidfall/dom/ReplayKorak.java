package hr.tvz.voidfall.dom;

public class ReplayKorak {
    private int runda;
    private String vrsta;
    private String akter;
    private String opis;
    private int[] igrac;
    private int[] voidborn;
    private int korupcija;
    private int prijetnja;

    public ReplayKorak(int runda, String vrsta, String akter, String opis,
                       int[] igrac, int[] voidborn, int korupcija, int prijetnja) {
        this.runda = runda;
        this.vrsta = vrsta;
        this.akter = akter;
        this.opis = opis;
        this.igrac = igrac;
        this.voidborn = voidborn;
        this.korupcija = korupcija;
        this.prijetnja = prijetnja;
    }

    public int getRunda() {
        return runda;
    }

    public String getVrsta() {
        return vrsta;
    }

    public String getAkter() {
        return akter;
    }

    public String getOpis() {
        return opis;
    }

    public int[] getIgrac() {
        return igrac;
    }

    public int[] getVoidborn() {
        return voidborn;
    }

    public int getKorupcija() {
        return korupcija;
    }

    public int getPrijetnja() {
        return prijetnja;
    }

    public String tekst() {
        return "Runda " + runda + " | " + vrsta + " | " + opis;
    }
}