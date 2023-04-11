import gui.MainApplicationFrame;

import javax.swing.SwingUtilities;
import java.awt.*;

public class RobotsProgram {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainApplicationFrame frame = new MainApplicationFrame();
            frame.pack();
            frame.setVisible(true);
            frame.setExtendedState(Frame.MAXIMIZED_BOTH);
        });
    }
}
