package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.TextArea;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;

import localization.ControlLang;
import log.LogChangeListener;
import log.LogEntry;
import log.LogWindowSource;

public class LogWindow extends JInternalFrame implements LogChangeListener, PropertyChangeListener {
    private LogWindowSource m_logSource;
    private TextArea m_logContent;
    private static final ControlLang control = ControlLang.getInstance();

    public LogWindow(LogWindowSource logSource) {
        super(control.getLocale("LOG_WINDOW"), true, true, true, true);
        control.addLocaleChangeListener(this);
        m_logSource = logSource;
        m_logSource.registerListener(this);
        m_logContent = new TextArea("");
        m_logContent.setSize(200, 500);

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(m_logContent, BorderLayout.CENTER);
        getContentPane().add(panel);
        pack();
        updateLogContent();
    }

    private void updateLogContent() {
        StringBuilder content = new StringBuilder();
        for (LogEntry entry : m_logSource.all()) {
            content.append(entry.getMessage()).append("\n");
        }
        m_logContent.setText(content.toString());
        m_logContent.invalidate();
    }

    @Override
    public void onLogChanged() {
        EventQueue.invokeLater(this::updateLogContent);
    }

    @Override
    public void doDefaultCloseAction() {
        try {
            m_logSource.unregisterListener(this);
        } finally {
            super.doDefaultCloseAction();
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (control.equals(evt.getSource())) {
            if (ControlLang.PROPERTY_LANG.equals(evt.getPropertyName())) {
                LogWindow.super.setTitle(control.getLocale("LOG_WINDOW"));
            }
        }
    }
}
