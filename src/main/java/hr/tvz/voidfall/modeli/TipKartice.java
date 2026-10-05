package hr.tvz.voidfall.modeli;

public enum TipKartice {

    EKSPANZIJA("Ekspanzija",
            "Vojna: kontrola ≥1 sektora osim glavnog → +3 vojske",
            "Kolonijalna: materijali ≥4 → +2 vojske, +2 materijala",
            "Brza: nema Crisis na glavnom → zauzimanje praznog neutralnog sektora bez trošenja vojske u borbi"),
    INDUSTRIJALIZACIJA("Industrijalizacija",
            "Ratna proizvodnja → +3 vojske",
            "Industrijska proizvodnja → +4 materijala",
            "Utvrđivanje → glavna obrana +2 trajno, industrija +1"),
    ISTRAZIVANJE("Istraživanje",
            "Vojna tehnologija: industrija ≥1 → trajni modifikator stila +0.1",
            "Proizvodnja → +3 materijala",
            "Stabilizacija: postoji Corruption → ukloni ga iz 1 sektora bez plaćanja"),
    DIPLOMACIJA("Diplomacija",
            "Utjecaj → +3 Influence",
            "Napredak → diplomacija +1",
            "Posredovanje: aktivna Crisis → ukloni je iz 1 sektora bez plaćanja");

    private String naziv;
    private String opisA;
    private String opisB;
    private String opisC;

    TipKartice(String naziv, String opisA, String opisB, String opisC) {
        this.naziv = naziv;
        this.opisA = opisA;
        this.opisB = opisB;
        this.opisC = opisC;
    }
}