package gui;

import javax.swing.*;
import java.awt.*;

public interface FrameClosingAdapter {
    default int getChoice(Component parentComponent) {
        int OPTION_YES_INT_VALUE = 0;
        Object[] options = getOptions();
        return JOptionPane.showOptionDialog(parentComponent,
                getMessage(),
                getTittle(),
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[OPTION_YES_INT_VALUE]);
    }

    Object[] getOptions();

    String getTittle();

    Object getMessage();
}
