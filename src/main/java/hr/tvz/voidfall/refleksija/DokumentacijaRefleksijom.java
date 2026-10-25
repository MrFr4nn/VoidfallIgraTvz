package hr.tvz.voidfall.refleksija;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;

public class DokumentacijaRefleksijom {
    private Map<Class<?>, Object[]> dozvoljeneKlase;

    public DokumentacijaRefleksijom(Map<Class<?>, Object[]> dozvoljeneKlase) {
        this.dozvoljeneKlase = dozvoljeneKlase;
    }

    public String napisiSve() {
        String tekst = "";
        for (Class<?> klasa : dozvoljeneKlase.keySet()) {
            tekst += napisi(klasa) + "\n";
        }
        return tekst;
    }

    public String napisi(Class<?> klasa) {
        if (!dozvoljeneKlase.containsKey(klasa)) {
            return "";
        }
        Object instanca = napraviInstancu(klasa);
        String tekst = "=== " + klasa.getPackageName() + "." + klasa.getSimpleName() + " ===\n";
        if (instanca != null) {
            tekst += " (instanca napravljena Reflectionom preko konstruktora s parametrima)\n";
        }
        if (klasa.isEnum()) {
            tekst += " vrijednosti: " + Arrays.toString(klasa.getEnumConstants()) + "\n";
        }

        Field[] polja = klasa.getDeclaredFields();
        for (int i = 0; i < polja.length; i++) {
            if (!polja[i].isSynthetic() && !polja[i].isEnumConstant()) {
                tekst += " polje " + poljeUTekst(polja[i], instanca) + "\n";
            }
        }
        Constructor<?>[] konstruktori = klasa.getDeclaredConstructors();
        for (int i = 0; i < konstruktori.length; i++) {
            tekst += " konstruktor(" + tipovi(konstruktori[i].getParameterTypes()) + ")\n";
        }
        Method[] metode = klasa.getDeclaredMethods();
        for (int i = 0; i < metode.length; i++) {
            if (metode[i].isSynthetic()) {
                continue;
            }
            tekst += " metoda " + metode[i].getName() + "(" + tipovi(metode[i].getParameterTypes())
                    + ") vraća " + metode[i].getReturnType().getSimpleName() + "\n";
            OpisAI opis = metode[i].getAnnotation(OpisAI.class);
            if (opis != null) {
                tekst += "      opis: " + opis.value() + "\n";
            }
        }
        return tekst;
    }

    private String poljeUTekst(Field polje, Object instanca) {
        String tekst = polje.getType().getSimpleName() + " " + polje.getName();
        if (instanca != null && (polje.getType().isPrimitive() || polje.getType() == String.class)) {
            try {
                polje.setAccessible(true);
                tekst += " = " + polje.get(instanca);
            } catch (IllegalAccessException e) {
                return tekst;
            }
        }
        return tekst;
    }

    private String tipovi(Class<?>[] tipovi) {
        String tekst = "";
        for (int i = 0; i < tipovi.length; i++) {
            tekst += tipovi[i].getSimpleName() + " ";
        }
        return tekst;
    }

    private Object napraviInstancu(Class<?> klasa) {
        if (klasa.isEnum() || klasa.isInterface()) {
            return null;
        }
        Object[] parametri = dozvoljeneKlase.get(klasa);
        try {
            Constructor<?>[] konstruktori = klasa.getDeclaredConstructors();
            for (int i = 0; i < konstruktori.length; i++) {
                if (konstruktori[i].getParameterCount() == parametri.length) {
                    konstruktori[i].setAccessible(true);
                    return konstruktori[i].newInstance(parametri);
                }
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }
}