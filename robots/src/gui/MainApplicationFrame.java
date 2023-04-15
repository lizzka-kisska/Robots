package gui;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Locale;

import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import localization.ControlLang;
import log.Logger;

public class MainApplicationFrame extends JFrame implements ActionListener, PropertyChangeListener {
    private final JDesktopPane desktopPane = new JDesktopPane();
    /**
     * @value Класс, контролирующий выбранную локаль, присваивает существующий класс ControlLang.
     */
    private final ControlLang control = ControlLang.getInstance();
    private static Locale currentLang = Locale.getDefault();
    /**
     * Поля методов, в которых используется локаль
     */
    private static JMenu switchLang, lookAndFeelMenu, testMenu, quitMenu;
    private static JMenuItem switchLangRu, switchLangEn, quitMenuItem, systemLookAndFeelItem,
            crossplatformLookAndFeelItem, logMessagelItem;

    /**
     * Конструктор класса создает главное окно приложения,
     * а также подписывается на изменение локали через метод addLocaleChangeListener.
     */
    public MainApplicationFrame() {
        control.addLocaleChangeListener(this);

        setContentPane(desktopPane);
        setFrameSize();
        addWindow(createLogWindow(control.getLocale("FRAME_WORKING_PROTOCOL")));
        addWindow(createGameWindow());
        setJMenuBar(generateMenuBar());
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    private void setFrameSize() {
        int inset = 50;
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(inset, inset,
                screenSize.width,
                screenSize.height);
    }

    private LogWindow createLogWindow(String text) {
        LogWindow logWindow = new LogWindow(Logger.getDefaultLogSource());
        logWindow.setLocation(10, 10);
        logWindow.setSize(300, 800);
        setMinimumSize(logWindow.getSize());
        logWindow.pack();
        Logger.debug(text);
        return logWindow;
    }

    private GameWindow createGameWindow() {
        GameWindow gameWindow = new GameWindow();
        gameWindow.setSize(400, 400);
        return gameWindow;
    }

    private void addWindow(JInternalFrame frame) {
        desktopPane.add(frame);
        frame.setVisible(true);
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
        item.addActionListener(this);
        return item;
    }

    /**
     * Метод закрытия окна и свобождение используемых ресурсов, если была нажата соответствующая кнопка.
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        this.setVisible(false);
        this.dispose();
        System.exit(0);
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
        item.addActionListener((event) -> {
            Logger.debug(control.getLocale("LOG_MES"));
        });
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
     * @param evt A PropertyChangeEvent object describing the event source
     *          and the property that has changed.
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
