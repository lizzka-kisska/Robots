package gui.internalframes;

import localization.ControlLang;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class TimerWindow extends JInternalFrame implements PropertyChangeListener {
    private static final ControlLang control = ControlLang.getInstance();
    public static String time = "hype";
    public TextArea m_timerContent;

    public TimerWindow(){
        super(control.getLocale("TIMER_WINDOW"), true, true, true, true);
        control.addLocaleChangeListener(this);
        JPanel panel = new JPanel(new BorderLayout());
        m_timerContent = new TextArea(time);
        panel.add(m_timerContent, BorderLayout.CENTER);
        getContentPane().add(panel);
        pack();
    }

    public void setTime(String newTime){
        m_timerContent.setText(newTime);
        repaint();
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (control.equals(evt.getSource())) {
            if (ControlLang.PROPERTY_LANG.equals(evt.getPropertyName())) {
                TimerWindow.super.setTitle(control.getLocale("TIMER_WINDOW"));
            }
        }
    }
}
