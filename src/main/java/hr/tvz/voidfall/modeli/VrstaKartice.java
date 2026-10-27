package hr.tvz.voidfall.modeli;

public enum VrstaKartice {
    ORBITALNI_NAPAD("EKSPANZIJA", "Orbitalni napad", "Protivniku -2 zdravlje"),
    PRECIZNI_UDAR("EKSPANZIJA", "Precizni udar", "Protivniku -3 zdravlje, tvoja obrana -1"),
    ZASJEDA("EKSPANZIJA", "Zasjeda", "Protivniku -2 zdravlje, +1 napad"),
    PREOPTERECENJE("EKSPANZIJA", "Preopterećenje", "Protivniku -4 zdravlje, tebi -2"),
    SABOTAZA("EKSPANZIJA", "Sabotaža", "Protivniku -1 napad i -1 obrana"),
    INDUSTRIJALIZACIJA("INDUSTRIJALIZACIJA", "Industrijalizacija", "+1 industrija, +1 obrana"),
    UTVRDA("INDUSTRIJALIZACIJA", "Utvrda", "+2 obrana"),
    ENERGETSKI_STIT("INDUSTRIJALIZACIJA", "Energetski štit", "+3 obrana, -1 napad"),
    HITNA_POPRAVKA("INDUSTRIJALIZACIJA", "Hitna popravka", "+5 zdravlje, -1 obrana"),
    EVAKUACIJA("INDUSTRIJALIZACIJA", "Evakuacija", "+2 zdravlje, +1 obrana"),
    ISTRAZIVANJE("ISTRAŽIVANJE", "Istraživanje", "+1 istraživanje, +1 napad"),
    NOVA_TEHNOLOGIJA("ISTRAŽIVANJE", "Nova tehnologija", "+2 napad"),
    TRGOVINA("ISTRAŽIVANJE", "Trgovina", "+1 industrija, +1 istraživanje"),
    RIZICNI_EKSPERIMENT("ISTRAŽIVANJE", "Rizični eksperiment", "+3 napad, tvoje zdravlje -2"),
    KONTAMINIRANI_SIGNAL("ISTRAŽIVANJE", "Kontaminirani signal", "+2 napad, +1 obrana, +1 korupcija"),
    DIPLOMACIJA("DIPLOMACIJA", "Diplomacija", "+1 diplomacija, -1 korupcija"),
    MIROVNI_SPORAZUM("DIPLOMACIJA", "Mirovni sporazum", "-2 korupcija"),
    PRECISCAVANJE("DIPLOMACIJA", "Pročišćavanje", "-1 korupcija, +2 zdravlje"),
    POPRAVAK("DIPLOMACIJA", "Popravak", "+3 zdravlje"),
    SPIJUNAZA("DIPLOMACIJA", "Špijunaža", "+1 istraživanje, protivniku -1 obrana"),
    KORUPCIJA("VOIDBORN", "Korupcija", "+1 korupcija");

    private String kategorija;
    private String naziv;
    private String opis;

    VrstaKartice(String kategorija, String naziv, String opis) {
        this.kategorija = kategorija;
        this.naziv = naziv;
        this.opis = opis;
    }

    public String getKategorija() {
        return kategorija;
    }

    public String getNaziv() {
        return naziv;
    }

    public String getOpis() {
        return opis;
    }
}