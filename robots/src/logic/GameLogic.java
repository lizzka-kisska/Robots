package logic;

import java.awt.event.KeyEvent;

public class GameLogic {
    TimerToEndGame timerToEndGame = new TimerToEndGame();
    public GameLogic(){
    }

    public void checkPositionRelativeToBush(UserRobot userRobot, Bush bush){
        if (userRobot.isInsideBush(bush.xCoordinate, bush.yCoordinate)) {
            userRobot.isVisible = false;
            userRobot.xOffset = UserRobotOffset.HALT.getXOffset();
            userRobot.yOffset = UserRobotOffset.HALT.getYOffset();
        } else {
            userRobot.isVisible = true;
        }
    }

    public void checkDistance(Robot robot, UserRobot userRobot) {
        int RADIUS = 100;
        if (userRobot.isVisible) {
            if (distance(robot.xCoordinate, userRobot.xCoordinate, robot.yCoordinate, userRobot.yCoordinate) <= RADIUS) {
                timerToEndGame.startAndRunTimer();
            } else {
                timerToEndGame.stopTimer();
            }
        }
    }

    public double distance(double x1, double x2, double y1, double y2) {
        return Math.sqrt(Math.pow(x2 - x1, 2)
                + Math.pow(y2 - y1, 2));
    }
}
