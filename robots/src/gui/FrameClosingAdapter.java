package gui;

import javax.swing.*;
import java.awt.*;

public interface FrameClosingAdapter {
    default int getChoice(Component parentComponent) {
        Object[] options = getOptions();
        return JOptionPane.showOptionDialog(parentComponent,
                getMessage(),
                getTittle(),
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]);
    }

    Object[] getOptions();

    String getTittle();

    Object getMessage();
}
