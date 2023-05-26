package saving.states;

import log.Logger;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Locale;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

public class LocaleState {
    private final Preferences preferredLocale;
    private static final LocaleState localState = new LocaleState();

    public LocaleState() {
        preferredLocale = Preferences.userRoot().node("configuration/preferredLocale.xml");
    }

    public LocaleState getInstance() {
        return localState;
    }

    public Locale getPreferredLocale() {
        String locale = preferredLocale.get("locale", "EN");
        if (locale.equals("RU")) {
            return Locale.getDefault();
        } else {
            return Locale.ENGLISH;
        }
    }

    public void setPreferredLocale(Locale locale) {
        if (locale.getCountry().equals("RU")) {
            preferredLocale.put("locale", "RU");
        } else {
            preferredLocale.put("locale", "EN");
        }
    }

    public void uploadPreferredLocale() {
        try {
            preferredLocale.exportNode(new FileOutputStream("configuration/preferredLocale.xml"));
        } catch (BackingStoreException | IOException e) {
            Logger.debug(e.toString());
        }
    }
}
