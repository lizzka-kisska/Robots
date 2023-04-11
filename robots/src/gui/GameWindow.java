package gui;

import localization.ControlLang;

import java.awt.BorderLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;

public class GameWindow extends JInternalFrame implements PropertyChangeListener {
    private static final ControlLang control = ControlLang.getInstance();

    public GameWindow() {
        super(control.getLocale("GAME_WINDOW"), true, true, true, true);
        control.addLocaleChangeListener(this);
        GameVisualizer m_visualizer = new GameVisualizer();
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(m_visualizer, BorderLayout.CENTER);
        getContentPane().add(panel);
        pack();
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (control.equals(evt.getSource())) {
            if (ControlLang.PROPERTY_LANG.equals(evt.getPropertyName())) {
                GameWindow.super.setTitle(control.getLocale("GAME_WINDOW"));
            }
        }
    }
}
