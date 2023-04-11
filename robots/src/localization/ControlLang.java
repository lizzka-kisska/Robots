package localization;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Locale;
import java.util.ResourceBundle;

public class ControlLang {
    public static String PROPERTY_LANG = "PROPERTY_LANG"; //идентификатор изменений
    private final PropertyChangeSupport propChangeDispatcher = new PropertyChangeSupport(this);
    private Locale currentLang = Locale.getDefault();
    private static final ControlLang controlLang = new ControlLang();
    private static ResourceBundle resourceBundle;

    private ControlLang() { //singleton
        resourceBundle = ResourceBundle.getBundle("resources.locale", currentLang);
    }

    public static ControlLang getInstance() {
        return controlLang;
    }

    public String getLocale(String key) {
        return resourceBundle.getString(key);
    }

    public void setLocale(Locale newLang) {
        if (!currentLang.equals(newLang)) {
            Locale oldValue = currentLang;
            currentLang = newLang;
            resourceBundle = ResourceBundle.getBundle("resources.locale", newLang); //меняем бандл
            propChangeDispatcher.firePropertyChange(PROPERTY_LANG, oldValue, newLang); //уведомить слушателей,
            // что локаль поменялась
        }
    }

    public void addLocaleChangeListener(PropertyChangeListener listener) //добавить слушателей
    {
        propChangeDispatcher.addPropertyChangeListener(PROPERTY_LANG, listener);
    }
}