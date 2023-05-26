package logic;

import java.awt.event.KeyEvent;

public class UserRobot implements MovingRobot{
    public volatile double xCoordinate;
    public volatile double yCoordinate;
    public volatile double direction;
    public volatile int xOffset = 0;
    public volatile int yOffset = 0;
    public UserRobot(double userRobotXCoordinate, double userRobotYCoordinate, double userRobotDirection){
        this.xCoordinate = userRobotXCoordinate;
        this.yCoordinate = userRobotYCoordinate;
        this.direction = userRobotDirection;
    }

    public void changeDirection(KeyEvent e){
        switch (e.getKeyChar()){
            case 'w', 'ц' -> {xOffset = 0; yOffset = -1; direction = 4.65;}
            case 'a', 'ф' -> {xOffset = -1; yOffset = 0; direction = 3.1;}
            case 's', 'ы' -> {xOffset = 0; yOffset = 1; direction = 1.5;}
            case 'd', 'в' -> {xOffset = 1; yOffset = 0; direction = 0;}
        }
    }

    public void moveUserRobot(int width, int height){
        double newXCoordinate = xCoordinate + xOffset;
        double newYCoordinate = yCoordinate + yOffset;
        if (width != 0) {
            newXCoordinate = applyLimits(newXCoordinate, width);
        }
        if (height != 0) {
            newYCoordinate = applyLimits(newYCoordinate, height);
        }
        xCoordinate = newXCoordinate;
        yCoordinate = newYCoordinate;
    }
}
