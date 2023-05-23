package gui;

import localization.ControlLang;

import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;

public class InternalFrameClosingAdapter extends InternalFrameAdapter implements FrameClosingAdapter {
    ControlLang control;

    public InternalFrameClosingAdapter(ControlLang control) {
        this.control = control;
    }

    @Override
    public void internalFrameClosing(InternalFrameEvent e) {
        int choice = getChoice(e.getInternalFrame());
        if (choice == 0) {
            e.getInternalFrame().dispose();
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
        return control.getLocale("INTERNAL_FRAME_CLOSING_MES");
    }
}
