package logic;

import gui.GameVisualizer;
import gui.MainApplicationFrame;

public class TimerToEndGame {
    private long startTime;
    private static boolean needTime = true;

    public TimerToEndGame(){}

    public void startAndRunTimer(){
        if (needTime) {
            startTime = System.currentTimeMillis();
            needTime = false;
        }
        int ONE_MINUTE_IN_MS = 1000;
        MainApplicationFrame.timerWindow.setTime(
                Long.toString(2 - (System.currentTimeMillis() - startTime) / ONE_MINUTE_IN_MS));
        int TWO_MINUTES_IN_MS = 2000;
        if (System.currentTimeMillis() - startTime >= TWO_MINUTES_IN_MS) {
            MainApplicationFrame.timerWindow.setText("LOSE");
            GameVisualizer.runGame = false;
        }
    }

    public void stopTimer(){
        needTime = true;
        MainApplicationFrame.timerWindow.setText("HYPE");
    }
}
