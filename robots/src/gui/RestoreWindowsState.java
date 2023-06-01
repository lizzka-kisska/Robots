package gui;

import log.Logger;

import javax.swing.*;
import java.beans.PropertyVetoException;

import static gui.MainApplicationFrame.savingData;

public interface RestoreWindowsState {
    default void restoreWindowState(JInternalFrame window) {
        window.setLocation(savingData.windowState().getWindowXCoordinate(window),
                savingData.windowState().getWindowYCoordinate(window));
        window.setSize(savingData.windowState().getWindowWidth(window),
                savingData.windowState().getWindowHeight(window));
        switch (savingData.windowState().getWindowState(window)) {
            case "closed" -> window.setVisible(false);
            case "opened" -> window.setVisible(true);
        }
        switch (savingData.windowState().getWindowView(window)) {
            case "iconified" -> {
                try {
                    window.setIcon(true);
                } catch (PropertyVetoException e) {
                    Logger.debug(e.toString());
                }
            }
            case "deiconified" -> {
                try {
                    window.setIcon(false);
                } catch (PropertyVetoException e) {
                    Logger.debug(e.toString());
                }
            }
        }
    }
}
