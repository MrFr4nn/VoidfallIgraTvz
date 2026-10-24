package hr.tvz.voidfall.refleksija;

import hr.tvz.voidfall.ai.VoidbornNPC;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class DokumentacijaAI {

    public String napisi() {
        VoidbornNPC npc = new VoidbornNPC();
        Class<?> klasa = npc.getClass();
        String tekst = "=== DOKUMENTACIJA: " + klasa.getSimpleName() + " ===\n";

        tekst += "\nPostavke (polja klase):\n";
        Field[] polja = klasa.getDeclaredFields();
        for (int i = 0; i < polja.length; i++) {
            try {
                polja[i].setAccessible(true);
                tekst += " - " + polja[i].getName() + " = " + polja[i].get(npc) + "\n";
            } catch (IllegalAccessException e) {
                tekst += " - " + polja[i].getName() + " (nedostupno)\n";
            }
        }

        tekst += "\nMetode (pravila odlučivanja):\n";
        Method[] metode = klasa.getDeclaredMethods();
        for (int i = 0; i < metode.length; i++) {
            tekst += " - " + metode[i].getName()
                    + " (parametara: " + metode[i].getParameterCount()
                    + ", vraća: " + metode[i].getReturnType().getSimpleName() + ")\n";
        }

        tekst += "\nUtjecaj Threat Levela:\n";
        tekst += " Razina 1: Voidborn razvija industriju i istraživanje.\n";
        tekst += " Razina 2: Voidborn gradi napad, zatim širi korupciju.\n";
        tekst += " Razina 3: Voidborn napada orbitalno.\n";
        tekst += " Uvijek prije toga: nisko vlastito zdravlje = popravak, igrač blizu uništenja = napad.\n";
        return tekst;
    }
}