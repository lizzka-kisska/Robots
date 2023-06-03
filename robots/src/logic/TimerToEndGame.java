package logic;

import gui.GameVisualizer;
import gui.MainApplicationFrame;

public class TimerToEndGame {
    private long startTime;
    private static boolean needTime = true;

    public TimerToEndGame() {
    }

    public void startAndRunTimer() {
        if (needTime) {
            startTime = System.currentTimeMillis();
            needTime = false;
        }
        int ONE_SECOND_IN_MS = 1000;
        int THREE_SECONDS_IN_MS = 3000;
        if (MainApplicationFrame.timerWindow != null) {
            MainApplicationFrame.timerWindow.setTime(
                    Long.toString((THREE_SECONDS_IN_MS + ONE_SECOND_IN_MS
                            - (System.currentTimeMillis() - startTime)) / ONE_SECOND_IN_MS));
        }
        if (System.currentTimeMillis() - startTime >= THREE_SECONDS_IN_MS) {
            MainApplicationFrame.timerWindow.setText("LOSE");
            GameVisualizer.runGame = false;
        }
    }

    public void stopTimer() {
        if (MainApplicationFrame.timerWindow != null) {
            needTime = true;
            MainApplicationFrame.timerWindow.setText("HYPE");
        }
    }
}
