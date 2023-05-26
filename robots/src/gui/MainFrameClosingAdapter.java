package gui;

import localization.ControlLang;
import saving.SavingData;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Locale;

public class MainFrameClosingAdapter extends WindowAdapter implements FrameClosingAdapter {
    ControlLang control;
    SavingData savingData;
    Locale currentLang;

    public MainFrameClosingAdapter(ControlLang control, SavingData savingData) {
        this.control = control;
        this.savingData = savingData;
    }

    @Override
    public void windowClosing(WindowEvent e) {
        int choice = getChoice(e.getWindow());
        if (choice == 0) {
            currentLang = control.getCurrentLang();
            savingData.localeState().setPreferredLocale(currentLang);
            savingData.localeState().uploadPreferredLocale();
            savingData.windowState().uploadWindowState();
            e.getWindow().setVisible(false);
            System.exit(0);
        }
    }

    @Override
    public Object[] getOptions() {
        return new Object[]{
                control.getLocale("OPTION_YES"),
                control.getLocale("OPTION_NO")
        };
    }

    @Override
    public String getTittle() {
        return control.getLocale("OPTION_DIALOG_TITLE");
    }

    @Override
    public Object getMessage() {
        return control.getLocale("APP_QUIT_OPTION_DIALOG_MES");
    }
}
