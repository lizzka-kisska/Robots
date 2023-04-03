package gui;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import log.Logger;

public class MainApplicationFrame extends JFrame implements ActionListener{
    private final JDesktopPane desktopPane = new JDesktopPane();

    public MainApplicationFrame() {
        setContentPane(desktopPane);
        setFrameSize();
        addWindow(createLogWindow());
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

    private LogWindow createLogWindow() {
        LogWindow logWindow = new LogWindow(Logger.getDefaultLogSource());
        logWindow.setLocation(10, 10);
        logWindow.setSize(300, 800);
        setMinimumSize(logWindow.getSize());
        logWindow.pack();
        Logger.debug("Протокол работает");
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
        menuBar.add(generateLookAndFeelMenu());
        menuBar.add(generateTestMenu());
        menuBar.add(generateQuitMenu());
        return menuBar;
    }

    private JMenu generateLookAndFeelMenu() {
        JMenu menu = new JMenu("Режим отображения");
        menu.setMnemonic(KeyEvent.VK_V);
        menu.getAccessibleContext().setAccessibleDescription("Управление режимом отображения приложения");
        menu.add(generateSystemLookAndFeelItem());
        menu.add(generateCrossplatformLookAndFeelItem());
        return menu;
    }

    private JMenu generateQuitMenu() {
        JMenu menu = new JMenu("Выход");
        menu.setMnemonic(KeyEvent.VK_V);
        menu.getAccessibleContext().setAccessibleDescription("Выход из приложения");
        menu.add(generateQuitMenuItem());
        return menu;
    }

    private JMenuItem generateQuitMenuItem() {
        JMenuItem item = new JMenuItem("Выход из приложения", KeyEvent.VK_S);
        item.addActionListener(this);
        return item;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.setVisible(false);
        this.dispose();
        System.exit(0);
    }

    private JMenuItem generateSystemLookAndFeelItem() {
        JMenuItem item = new JMenuItem("Системная схема", KeyEvent.VK_S);
        item.addActionListener((event) -> {
            setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            this.invalidate();
        });
        return item;
    }

    private JMenuItem generateCrossplatformLookAndFeelItem() {
        JMenuItem item = new JMenuItem("Универсальная схема", KeyEvent.VK_S);
        item.addActionListener((event) -> {
            setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            this.invalidate();
        });
        return item;
    }

    private JMenu generateTestMenu() {
        JMenu menu = new JMenu("Тесты");
        menu.setMnemonic(KeyEvent.VK_T);
        menu.getAccessibleContext().setAccessibleDescription("Тестовые команды");
        menu.add(generateLogMessagelItem());
        return menu;
    }

    private JMenuItem generateLogMessagelItem() {
        JMenuItem item = new JMenuItem("Сообщение в лог", KeyEvent.VK_S);
        item.addActionListener((event) -> {
            Logger.debug("Новая строка");
        });
        return item;
    }

    private void setLookAndFeel(String className) {
        try {
            UIManager.setLookAndFeel(className);
            SwingUtilities.updateComponentTreeUI(this);
        } catch (ClassNotFoundException | InstantiationException
                 | IllegalAccessException | UnsupportedLookAndFeelException e) {
            // just ignore
        }
    }
}
