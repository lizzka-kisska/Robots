package gui;

import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyVetoException;
import java.util.Locale;
import java.awt.event.*;
import javax.swing.*;

import gui.internalframes.GameWindow;
import gui.internalframes.LogWindow;
import localization.ControlLang;
import log.Logger;
import saving.SavingData;

public class MainApplicationFrame extends JFrame implements PropertyChangeListener {
    private final JDesktopPane desktopPane = new JDesktopPane();
    private LogWindow logWindow;
    private GameWindow gameWindow;
    /**
     * @value Класс, контролирующий выбранную локаль, присваивает существующий класс ControlLang.
     */
    private final ControlLang control = ControlLang.getInstance();
    private static final SavingData savingData = SavingData.getInstance();
    private static Locale currentLang = savingData.localeState().getPreferredLocale();
    /**
     * Поля методов, в которых используется локаль
     */
    private static JMenu switchLang, lookAndFeelMenu, testMenu, quitMenu;
    private static JMenuItem switchLangRu, switchLangEn, quitMenuItem, systemLookAndFeelItem,
            crossplatformLookAndFeelItem, logMessagelItem;

    InternalFrameClosingAdapter internalFrameClosingAdapter = new InternalFrameClosingAdapter(control);
    MainFrameClosingAdapter mainFrameClosingAdapter = new MainFrameClosingAdapter(control, savingData);
    SavingDataAdapter savingDataAdapter = new SavingDataAdapter(savingData) {
    };

    /**
     * Конструктор класса создает главное окно приложения,
     * а также подписывается на изменение локали через метод addLocaleChangeListener.
     */
    public MainApplicationFrame() {
        control.addLocaleChangeListener(this);
        setContentPane(desktopPane);
        setJMenuBar(generateMenuBar());
        control.setLocale(currentLang);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                Object[] options = {control.getLocale("OPTION_YES"),
                        control.getLocale("OPTION_NO")
                };
                int choice = JOptionPane.showOptionDialog(
                        e.getWindow(),
                        control.getLocale("RESTORE_DATA_MES"),
                        control.getLocale("OPTION_DIALOG_TITLE"),
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        options,
                        options[0]);
                if (choice != 0) {
                    currentLang = Locale.getDefault();
                    control.setLocale(currentLang);
                    savingData.windowState().setDefaultGameWindowState();
                    savingData.windowState().setDefaultLogWindowState();
                }
                createGameWindow();
                createLogWindow(control.getLocale("FRAME_WORKING_PROTOCOL"));
                addWindow(logWindow);
                addWindow(gameWindow);
                createMainFrame();
            }
        });
    }

    private void createMainFrame() {
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(mainFrameClosingAdapter);
    }

    private void createLogWindow(String text) {
        logWindow = new LogWindow(Logger.getDefaultLogSource());
        logWindow.pack();
        Logger.debug(text);
        logWindow.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        logWindow.addInternalFrameListener(internalFrameClosingAdapter);
        logWindow.addInternalFrameListener(savingDataAdapter);
        logWindow.addComponentListener(savingDataAdapter);
        restoreLogWindowState();
    }

    private void restoreLogWindowState() {
        logWindow.setLocation(savingData.windowState().getWindowXCoordinate(logWindow),
                savingData.windowState().getWindowYCoordinate(logWindow));
        logWindow.setSize(savingData.windowState().getWindowWidth(logWindow),
                savingData.windowState().getWindowHeight(logWindow));
        switch (savingData.windowState().getWindowState(logWindow)) {
            case "closed" -> logWindow.setVisible(false);
            case "opened" -> logWindow.setVisible(true);
        }
        switch (savingData.windowState().getWindowView(logWindow)) {
            case "iconified" -> {
                try {
                    logWindow.setIcon(true);
                } catch (PropertyVetoException e) {
                    Logger.debug(e.toString());
                }
            }
            case "deiconified" -> {
                try {
                    logWindow.setIcon(false);
                } catch (PropertyVetoException e) {
                    Logger.debug(e.toString());
                }
            }
        }
    }

    private void createGameWindow() {
        gameWindow = new GameWindow();
        gameWindow.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        gameWindow.addInternalFrameListener(internalFrameClosingAdapter);
        gameWindow.addInternalFrameListener(savingDataAdapter);
        gameWindow.addComponentListener(savingDataAdapter);
        restoreGameWindowState();
    }

    private void restoreGameWindowState() {
        gameWindow.setLocation(savingData.windowState().getWindowXCoordinate(gameWindow),
                savingData.windowState().getWindowYCoordinate(gameWindow));
        gameWindow.setSize(savingData.windowState().getWindowWidth(gameWindow),
                savingData.windowState().getWindowHeight(gameWindow));
        switch (savingData.windowState().getWindowState(gameWindow)) {
            case "closed" -> gameWindow.setVisible(false);
            case "opened" -> gameWindow.setVisible(true);
        }
        switch (savingData.windowState().getWindowView(gameWindow)) {
            case "iconified" -> {
                try {
                    gameWindow.setIcon(true);
                } catch (PropertyVetoException e) {
                    Logger.debug(e.toString());
                }
            }
            case "deiconified" -> {
                try {
                    gameWindow.setIcon(false);
                } catch (PropertyVetoException e) {
                    Logger.debug(e.toString());
                }
            }
        }
    }

    private void addWindow(JInternalFrame frame) {
        desktopPane.add(frame);
    }

    private JMenuBar generateMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        switchLang = generateSwitchLang();
        lookAndFeelMenu = generateLookAndFeelMenu();
        testMenu = generateTestMenu();
        quitMenu = generateQuitMenu();
        menuBar.add(switchLang);
        menuBar.add(lookAndFeelMenu);
        menuBar.add(testMenu);
        menuBar.add(quitMenu);
        return menuBar;
    }

    private JMenu generateSwitchLang() {
        JMenu menu = new JMenu(control.getLocale("FRAME_SWITCH_LANG"));
        menu.setMnemonic(KeyEvent.VK_V);
        menu.getAccessibleContext().setAccessibleDescription(control.getLocale("FRAME_SWITCH_LANG"));
        switchLangRu = generateSwitchLangRuItem();
        switchLangEn = generateSwitchLangEnItem();
        menu.add(switchLangRu);
        menu.add(switchLangEn);
        return menu;
    }

    private JMenuItem generateSwitchLangRuItem() {
        JMenuItem item = new JMenuItem(control.getLocale("LANG_RU"), KeyEvent.VK_S);
        item.setMnemonic(KeyEvent.VK_V);
        item.addActionListener((event) -> {
            currentLang = Locale.getDefault();
            control.setLocale(currentLang);
        });
        return item;
    }

    private JMenuItem generateSwitchLangEnItem() {
        JMenuItem item = new JMenuItem(control.getLocale("LANG_EN"), KeyEvent.VK_S);
        item.setMnemonic(KeyEvent.VK_V);
        item.addActionListener((event) -> {
            currentLang = Locale.ENGLISH;
            control.setLocale(currentLang);
        });
        return item;
    }

    private JMenu generateQuitMenu() {
        JMenu menu = new JMenu(control.getLocale("FRAME_QUIT"));
        menu.setMnemonic(KeyEvent.VK_V);
        menu.getAccessibleContext().setAccessibleDescription(control.getLocale("FRAME_APP_QUIT"));
        quitMenuItem = generateQuitMenuItem();
        menu.add(quitMenuItem);
        return menu;
    }

    private JMenuItem generateQuitMenuItem() {
        JMenuItem item = new JMenuItem(control.getLocale("FRAME_APP_QUIT"), KeyEvent.VK_S);
        item.addActionListener((event) ->
                dispatchEvent(new WindowEvent(MainApplicationFrame.this, WindowEvent.WINDOW_CLOSING))
        );
        return item;
    }

    private JMenu generateLookAndFeelMenu() {
        JMenu menu = new JMenu(control.getLocale("FRAME_DISPLAY_MODE"));
        menu.setMnemonic(KeyEvent.VK_V);
        menu.getAccessibleContext().setAccessibleDescription(control.getLocale("FRAME_MANAGE_DISPLAY_MODE"));
        systemLookAndFeelItem = generateSystemLookAndFeelItem();
        crossplatformLookAndFeelItem = generateCrossplatformLookAndFeelItem();
        menu.add(systemLookAndFeelItem);
        menu.add(crossplatformLookAndFeelItem);
        return menu;
    }

    private JMenuItem generateSystemLookAndFeelItem() {
        JMenuItem item = new JMenuItem(control.getLocale("FRAME_SYS_SCHEME"), KeyEvent.VK_S);
        item.addActionListener((event) -> {
            setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            this.invalidate();
        });
        return item;
    }

    private JMenuItem generateCrossplatformLookAndFeelItem() {
        JMenuItem item = new JMenuItem(control.getLocale("FRAME_UNI_SCHEME"), KeyEvent.VK_S);
        item.addActionListener((event) -> {
            setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            this.invalidate();
        });
        return item;
    }

    private JMenu generateTestMenu() {
        JMenu menu = new JMenu(control.getLocale("FRAME_TESTS"));
        menu.setMnemonic(KeyEvent.VK_T);
        menu.getAccessibleContext().setAccessibleDescription(control.getLocale("FRAME_TEST_COMMANDS"));
        logMessagelItem = generateLogMessagelItem();
        menu.add(logMessagelItem);
        return menu;
    }

    private JMenuItem generateLogMessagelItem() {
        JMenuItem item = new JMenuItem(control.getLocale("FRAME_MES_LOG"), KeyEvent.VK_S);
        item.addActionListener((event) -> Logger.debug(control.getLocale("LOG_MES")));
        return item;
    }

    private void setLookAndFeel(String className) {
        try {
            UIManager.setLookAndFeel(className);
            SwingUtilities.updateComponentTreeUI(this);
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException |
                 UnsupportedLookAndFeelException e) {
            Logger.debug(e.toString());
        }
    }

    /**
     * Метод, который меняет регариует, если изменилась локаль.
     *
     * @param evt A PropertyChangeEvent object describing the event source
     *            and the property that has changed.
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (control.equals(evt.getSource())) {
            if (ControlLang.PROPERTY_LANG.equals(evt.getPropertyName())) {
                changeMainFrame();
            }
        }
    }

    /**
     * Метод, устанавливающий текст в каждое поле, в котором используется локаль.
     */
    private void changeMainFrame() {
        switchLang.setText(control.getLocale("FRAME_SWITCH_LANG"));
        lookAndFeelMenu.setText(control.getLocale("FRAME_DISPLAY_MODE"));
        testMenu.setText(control.getLocale("FRAME_TESTS"));
        quitMenu.setText(control.getLocale("FRAME_QUIT"));

        switchLangRu.setText(control.getLocale("LANG_RU"));
        switchLangEn.setText(control.getLocale("LANG_EN"));

        quitMenuItem.setText(control.getLocale("FRAME_APP_QUIT"));

        systemLookAndFeelItem.setText(control.getLocale("FRAME_SYS_SCHEME"));
        crossplatformLookAndFeelItem.setText(control.getLocale("FRAME_UNI_SCHEME"));

        logMessagelItem.setText(control.getLocale("FRAME_MES_LOG"));
    }
}
