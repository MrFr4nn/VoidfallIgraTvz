package hr.tvz.voidfall.modeli;

public class VoidbornNPC extends Frakcija {

    private int threatLevel;

    public VoidbornNPC(String naziv) {
        super(naziv);
        this.threatLevel = 1;
    }
}