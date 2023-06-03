package saving.states;

import javax.swing.*;
import javax.swing.event.InternalFrameEvent;
import java.awt.event.ComponentEvent;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

public class FramesState {
    private final Preferences windowState;
    private static final FramesState windowsState = new FramesState();

    public FramesState() {
        windowState = Preferences.userRoot().node("configuration/framesstate");
    }

    public FramesState getInstance() {
        return windowsState;
    }

    public void updateSize(ComponentEvent e) {
        String windowName = getWindowNameFromComponentEvent(e) + ".xml";
        windowState.node(windowName).putInt("width", e.getComponent().getWidth());
        windowState.node(windowName).putInt("height", e.getComponent().getHeight());
    }

    public void updateLocation(ComponentEvent e) {
        String windowName = getWindowNameFromComponentEvent(e) + ".xml";
        windowState.node(windowName).putInt("X", e.getComponent().getX());
        windowState.node(windowName).putInt("Y", e.getComponent().getY());
    }

    public void updateStateToOpened(InternalFrameEvent e) {
        String windowName = getWindowNameFromInternalFrameEvent(e) + ".xml";
        windowState.node(windowName).put("state", "opened");
    }

    public void updateStateToClosed(InternalFrameEvent e) {
        String windowName = getWindowNameFromInternalFrameEvent(e) + ".xml";
        windowState.node(windowName).put("state", "closed");
    }

    public void updateViewToIconified(InternalFrameEvent e) {
        String windowName = getWindowNameFromInternalFrameEvent(e) + ".xml";
        windowState.node(windowName).put("view", "iconified");
    }

    public void updateViewToDeiconified(InternalFrameEvent e) {
        String windowName = getWindowNameFromInternalFrameEvent(e) + ".xml";
        windowState.node(windowName).put("view", "deiconified");
    }

    private String getWindowNameFromComponentEvent(ComponentEvent e) {
        String className = e.getComponent().getClass().getName();
        return className.substring(className.lastIndexOf(".") + 1);
    }

    private String getWindowNameFromInternalFrameEvent(InternalFrameEvent e) {
        String className = e.getInternalFrame().getClass().getName();
        return className.substring(className.lastIndexOf(".") + 1);
    }

    public void uploadWindowState() {
        try {
            for (String child : windowState.childrenNames()) {
                windowState.node(child).exportNode(new FileOutputStream("configuration/framesstate/" + child));
            }
        } catch (BackingStoreException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Integer getWindowXCoordinate(JInternalFrame internalFrame) {
        String windowName = getWindowNameFromJInternalFrame(internalFrame) + ".xml";
        return windowState.node(windowName).getInt("X", 10);
    }

    public Integer getWindowYCoordinate(JInternalFrame internalFrame) {
        String windowName = getWindowNameFromJInternalFrame(internalFrame) + ".xml";
        return windowState.node(windowName).getInt("Y", 10);
    }

    public Integer getWindowWidth(JInternalFrame internalFrame) {
        String windowName = getWindowNameFromJInternalFrame(internalFrame) + ".xml";
        return windowState.node(windowName).getInt("width", 200);
    }

    public Integer getWindowHeight(JInternalFrame internalFrame) {
        String windowName = getWindowNameFromJInternalFrame(internalFrame) + ".xml";
        return windowState.node(windowName).getInt("height", 500);
    }

    public String getWindowState(JInternalFrame internalFrame) {
        String windowName = getWindowNameFromJInternalFrame(internalFrame) + ".xml";
        return windowState.node(windowName).get("state", "opened");
    }

    public String getWindowView(JInternalFrame internalFrame) {
        String windowName = getWindowNameFromJInternalFrame(internalFrame) + ".xml";
        return windowState.node(windowName).get("view", "deiconified");
    }

    private String getWindowNameFromJInternalFrame(JInternalFrame internalFrame) {
        String className = internalFrame.getClass().getName();
        return className.substring(className.lastIndexOf(".") + 1);
    }

    public void setDefaultLogWindowState() {
        String fileName = "LogWindow.xml";
        windowState.node(fileName).putInt("X", 10);
        windowState.node(fileName).putInt("Y", 10);
        windowState.node(fileName).putInt("width", 200);
        windowState.node(fileName).putInt("height", 500);
        windowState.node(fileName).put("state", "opened");
        windowState.node(fileName).put("view", "deiconified");
    }

    public void setDefaultGameWindowState() {
        String fileName = "GameWindow.xml";
        windowState.node(fileName).putInt("X", 400);
        windowState.node(fileName).putInt("Y", 10);
        windowState.node(fileName).putInt("width", 400);
        windowState.node(fileName).putInt("height", 400);
        windowState.node(fileName).put("state", "opened");
        windowState.node(fileName).put("view", "deiconified");
    }

    public void setDefaultTimerWindowState() {
        String fileName = "TimerWindow.xml";
        windowState.node(fileName).putInt("X", 500);
        windowState.node(fileName).putInt("Y", 400);
        windowState.node(fileName).putInt("width", 200);
        windowState.node(fileName).putInt("height", 100);
        windowState.node(fileName).put("state", "opened");
        windowState.node(fileName).put("view", "deiconified");
    }

    public void setDefaultRobotsCoordinatesState() {
        String fileName = "RobotsCoordinates.xml";
        windowState.node(fileName).putInt("X", 900);
        windowState.node(fileName).putInt("Y", 10);
        windowState.node(fileName).putInt("width", 450);
        windowState.node(fileName).putInt("height", 230);
        windowState.node(fileName).put("state", "opened");
        windowState.node(fileName).put("view", "deiconified");
    }

    public void setDefaultRobotsDistanceToTargetState() {
        String fileName = "RobotsDistanceToTarget.xml";
        windowState.node(fileName).putInt("X", 900);
        windowState.node(fileName).putInt("Y", 250);
        windowState.node(fileName).putInt("width", 450);
        windowState.node(fileName).putInt("height", 230);
        windowState.node(fileName).put("state", "opened");
        windowState.node(fileName).put("view", "deiconified");
    }
}
