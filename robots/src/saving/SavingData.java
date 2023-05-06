package saving;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Locale;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

import log.Logger;

public class SavingData {
    private final Preferences userPrefs;
    private static final SavingData savingData = new SavingData();

    public SavingData(){
        userPrefs = Preferences.userRoot().node("config.xml");
    }

    public static SavingData getInstance() {
        return savingData;
    }

    public Preferences getUserPrefs(){
        return userPrefs;
    }

    public Locale getPreferredLocale() {
        String locale = userPrefs.get("locale", "EN");
        if (locale.equals("RU")){
            return Locale.getDefault();
        }
        else{
            return Locale.ENGLISH;
        }
    }

    public void setPreferredLocale(Locale locale){
        if (locale.getCountry().equals("RU")){
            userPrefs.put("locale", "RU");
        }
        else {
            userPrefs.put("locale", "EN");
        }
    }

    public void uploadPreferredLocale(){
        try {
            userPrefs.exportNode(new FileOutputStream("config.xml"));
        } catch (BackingStoreException | IOException e) {
            Logger.debug(e.toString());
        }
    }
}
