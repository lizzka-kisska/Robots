package gui.internalframes;

import localization.ControlLang;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class RobotsMetaInformation extends JInternalFrame implements PropertyChangeListener {
    private static final ControlLang control = ControlLang.getInstance();
    private final JPanel panel = new JPanel(new BorderLayout());
    protected TextArea usersRobotTextField;
    protected TextArea robotTextField;
    private JButton usersRobotButton = new JButton(control.getLocale("USERS_ROBOT"));
    private JButton robotButton = new JButton(control.getLocale("ROBOT"));
    private final String title;

    public RobotsMetaInformation(String title) {
        super(control.getLocale(title), true, true, true, true);
        this.title = title;
        control.addLocaleChangeListener(this);
        createTextField();
        createButtonsPanel();
        getContentPane().add(panel);
        pack();
    }

    private void createTextField() {
        usersRobotTextField = new TextArea("");
        robotTextField = new TextArea("");
    }

    private void createButtonsPanel() {
        usersRobotButton = new JButton(control.getLocale("USERS_ROBOT"));
        usersRobotButton.addActionListener(e -> showUsersRobotInfo());
        robotButton = new JButton(control.getLocale("ROBOT"));
        robotButton.addActionListener(e -> showRobotInfo());
        JPanel buttonsPanel = new JPanel(new BorderLayout());
        buttonsPanel.add(usersRobotButton, BorderLayout.WEST);
        buttonsPanel.add(robotButton, BorderLayout.EAST);
        panel.add(buttonsPanel, BorderLayout.SOUTH);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (control.equals(evt.getSource())) {
            if (ControlLang.PROPERTY_LANG.equals(evt.getPropertyName())) {
                RobotsMetaInformation.super.setTitle(control.getLocale(title));
                usersRobotButton.setText(control.getLocale("USERS_ROBOT"));
                robotButton.setText(control.getLocale("ROBOT"));
            }
        }
    }

    private void showUsersRobotInfo() {
        panel.remove(robotTextField);
        panel.add(usersRobotTextField);
        pack();
    }

    private void showRobotInfo() {
        panel.remove(usersRobotTextField);
        panel.add(robotTextField);
        pack();
    }
}