package hr.tvz.voidfall.modeli;

public enum VrstaKartice {
    INDUSTRIJALIZACIJA("Industrijalizacija", "+1 industrija, +1 obrana"),
    ISTRAZIVANJE("Istraživanje", "+1 istraživanje, +1 napad"),
    DIPLOMACIJA("Diplomacija", "+1 diplomacija, -1 korupcija"),
    POPRAVAK("Popravak", "+3 zdravlje"),
    ORBITALNI_NAPAD("Orbitalni napad", "Protivniku -2 zdravlje"),
    UTVRDA("Utvrda", "+2 obrana"),
    NOVA_TEHNOLOGIJA("Nova tehnologija", "+2 napad"),
    PRECIZNI_UDAR("Precizni udar", "Protivniku -3 zdravlje, tvoja obrana -1"),
    SABOTAZA("Sabotaža", "Protivniku -1 napad i -1 obrana"),
    TRGOVINA("Trgovina", "+1 industrija, +1 istraživanje"),
    MIROVNI_SPORAZUM("Mirovni sporazum", "-2 korupcija"),
    RIZICNI_EKSPERIMENT("Rizični eksperiment", "+3 napad, tvoje zdravlje -2"),
    HITNA_POPRAVKA("Hitna popravka", "+5 zdravlje, -1 obrana"),
    ENERGETSKI_STIT("Energetski štit", "+3 obrana, -1 napad"),
    ZASJEDA("Zasjeda", "Protivniku -2 zdravlje, +1 napad"),
    SPIJUNAZA("Špijunaža", "+1 istraživanje, protivniku -1 obrana"),
    PRECISCAVANJE("Pročišćavanje", "-1 korupcija, +2 zdravlje"),
    KONTAMINIRANI_SIGNAL("Kontaminirani signal", "+2 napad, +1 obrana, +1 korupcija"),
    EVAKUACIJA("Evakuacija", "+2 zdravlje, +1 obrana"),
    PREOPTERECENJE("Preopterećenje", "Protivniku -4 zdravlje, tebi -2"),
    KORUPCIJA("Korupcija", "+1 korupcija");

    private String naziv;
    private String opis;

    VrstaKartice(String naziv, String opis) {
        this.naziv = naziv;
        this.opis = opis;
    }

    public String getNaziv() {
        return naziv;
    }

    public String getOpis() {
        return opis;
    }
}