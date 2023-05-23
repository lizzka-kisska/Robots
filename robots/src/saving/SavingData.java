package saving;

import java.awt.event.ComponentEvent;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Locale;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

import log.Logger;

import javax.swing.event.InternalFrameEvent;

public class SavingData {
    private final Preferences preferredLocale;
    private final Preferences logWindowState;
    private final Preferences gameWindowState;
    private static final SavingData savingData = new SavingData();

    public SavingData() {
        preferredLocale = Preferences.userRoot().node("configuration/preferredLocale.xml");
        logWindowState = Preferences.userRoot().node("configuration/framesstate/logWindowState.xml");
        gameWindowState = Preferences.userRoot().node("configuration/framesstate/gameWindowState.xml");
    }

    public static SavingData getInstance() {
        return savingData;
    }


    public void updateSize(ComponentEvent e) {
        switch (e.getComponent().getClass().getName()) {
            case "gui.internalframes.LogWindow" -> {
                logWindowState.putInt("width", e.getComponent().getWidth());
                logWindowState.putInt("height", e.getComponent().getHeight());
            }
            case "gui.internalframes.GameWindow" -> {
                gameWindowState.putInt("width", e.getComponent().getWidth());
                gameWindowState.putInt("height", e.getComponent().getHeight());
            }
        }

    }

    public void updateLocation(ComponentEvent e) {
        switch (e.getComponent().getClass().getName()) {
            case "gui.internalframes.LogWindow" -> {
                logWindowState.putInt("X", e.getComponent().getX());
                logWindowState.putInt("Y", e.getComponent().getY());
            }
            case "gui.internalframes.GameWindow" -> {
                gameWindowState.putInt("X", e.getComponent().getX());
                gameWindowState.putInt("Y", e.getComponent().getY());
            }
        }

    }

    public void updateStateToOpened(InternalFrameEvent e) {
        switch (e.getInternalFrame().getClass().getName()) {
            case "gui.internalframes.LogWindow" -> logWindowState.put("state", "opened");
            case "gui.internalframes.GameWindow" -> gameWindowState.put("state", "opened");
        }
    }

    public void updateStateToClosed(InternalFrameEvent e) {
        switch (e.getInternalFrame().getClass().getName()) {
            case "gui.internalframes.LogWindow" -> logWindowState.put("state", "closed");
            case "gui.internalframes.GameWindow" -> gameWindowState.put("state", "closed");
        }
    }

    public void updateViewToIconified(InternalFrameEvent e) {
        switch (e.getInternalFrame().getClass().getName()) {
            case "gui.internalframes.LogWindow" -> logWindowState.put("view", "iconified");
            case "gui.internalframes.GameWindow" -> gameWindowState.put("view", "iconified");
        }
    }

    public void updateViewToDeiconified(InternalFrameEvent e) {
        switch (e.getInternalFrame().getClass().getName()) {
            case "gui.internalframes.LogWindow" -> logWindowState.put("view", "deiconified");
            case "gui.internalframes.GameWindow" -> gameWindowState.put("view", "deiconified");
        }
    }

    public Integer getLogWindowXCoordinate() {
        return logWindowState.getInt("X", 10);
    }

    public Integer getLogWindowYCoordinate() {
        return logWindowState.getInt("Y", 10);
    }

    public Integer getLogWindowWidth() {
        return logWindowState.getInt("width", 200);
    }

    public Integer getLogWindowHeight() {
        return logWindowState.getInt("height", 500);
    }

    public String getLogWindowState() {
        return logWindowState.get("state", "opened");
    }

    public String getLogWindowView() {
        return logWindowState.get("view", "deiconified");
    }

    public void uploadLogWindowState() {
        try {
            logWindowState.exportNode(new FileOutputStream("configuration/framesstate/logWindowState.xml"));
        } catch (BackingStoreException | IOException e) {
            Logger.debug(e.toString());
        }
    }

    public Integer getGameWindowXCoordinate() {
        return gameWindowState.getInt("X", 10);
    }

    public Integer getGameWindowYCoordinate() {
        return gameWindowState.getInt("Y", 10);
    }

    public Integer getGameWindowWidth() {
        return gameWindowState.getInt("width", 200);
    }

    public Integer getGameWindowHeight() {
        return gameWindowState.getInt("height", 500);
    }

    public String getGameWindowState() {
        return gameWindowState.get("state", "opened");
    }

    public String getGameWindowView() {
        return gameWindowState.get("view", "deiconified");
    }

    public void uploadGameWindowState() {
        try {
            gameWindowState.exportNode(new FileOutputStream("configuration/framesstate/gameWindowState.xml"));
        } catch (BackingStoreException | IOException e) {
            Logger.debug(e.toString());
        }
    }

    public Locale getPreferredLocale() {
        String locale = preferredLocale.get("locale", "EN");
        if (locale.equals("RU")) {
            return Locale.getDefault();
        } else {
            return Locale.ENGLISH;
        }
    }

    public void setPreferredLocale(Locale locale) {
        if (locale.getCountry().equals("RU")) {
            preferredLocale.put("locale", "RU");
        } else {
            preferredLocale.put("locale", "EN");
        }
    }

    public void uploadPreferredLocale() {
        try {
            preferredLocale.exportNode(new FileOutputStream("configuration/preferredLocale.xml"));
        } catch (BackingStoreException | IOException e) {
            Logger.debug(e.toString());
        }
    }

    public void setDefaultLogWindowState() {
        logWindowState.putInt("X", 10);
        logWindowState.putInt("Y", 10);
        logWindowState.putInt("width", 200);
        logWindowState.putInt("height", 500);
        logWindowState.put("state", "opened");
        logWindowState.put("view", "deiconified");
    }

    public void setDefaultGameWindowState() {
        gameWindowState.putInt("X", 400);
        gameWindowState.putInt("Y", 10);
        gameWindowState.putInt("width", 400);
        gameWindowState.putInt("height", 400);
        gameWindowState.put("state", "opened");
        gameWindowState.put("view", "deiconified");
    }
}
