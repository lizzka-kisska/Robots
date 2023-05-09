package localization;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Класс, который изменяет локаль, если произошло событие, и сообщает об этом подписчикам.
 */
public class ControlLang {
    /**
     * @value Идентификатор изменений.
     */
    public static String PROPERTY_LANG = "PROPERTY_LANG";
    private final PropertyChangeSupport propChangeDispatcher = new PropertyChangeSupport(this);
    private Locale currentLang = Locale.getDefault();
    private static final ControlLang controlLang = new ControlLang();
    /**
     * @value Поле, через которое осуществляется доступ к файлам с локалью.
     */
    private static ResourceBundle resourceBundle;

    /**
     * Конструктор класса - singleton, который инициализирует resourceBundle
     */
    private ControlLang() {
        resourceBundle = ResourceBundle.getBundle("resources.locale", currentLang);
    }

    /**
     * @return Неизменный статический класс.
     */
    public static ControlLang getInstance() {
        return controlLang;
    }

    /**
     * @param key константа(ключ), по которой ищется запись в "resource.locale".
     * @return Значение, найденное по ключу в "resource.locale".
     */
    public String getLocale(String key) {
        return resourceBundle.getString(key);
    }

    /**
     * Метод, который меняет resourceBundle, используя новую локаль, и уведомляет слушателей об этом.
     *
     * @param newLang новая локаль.
     */
    public void setLocale(Locale newLang) {
        if (!currentLang.equals(newLang)) {
            Locale oldValue = currentLang;
            currentLang = newLang;
            resourceBundle = ResourceBundle.getBundle("resources.locale", newLang);
            propChangeDispatcher.firePropertyChange(PROPERTY_LANG, oldValue, newLang);
        }
    }

    /**
     * Метод, который добавляет слушателей
     *
     * @param listener слушатель, который подписывается на изменения
     */
    public void addLocaleChangeListener(PropertyChangeListener listener) //добавить слушателей
    {
        propChangeDispatcher.addPropertyChangeListener(PROPERTY_LANG, listener);
    }

    public Locale getCurrentLang() {
        return currentLang;
    }
}