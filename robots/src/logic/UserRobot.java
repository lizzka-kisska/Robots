package logic;

import java.awt.event.KeyEvent;

public class UserRobot implements MovingRobot {
    public volatile double xCoordinate;
    public volatile double yCoordinate;
    public volatile double direction;
    public volatile int xOffset = 0;
    public volatile int yOffset = 0;

    public UserRobot(double userRobotXCoordinate, double userRobotYCoordinate, double userRobotDirection) {
        this.xCoordinate = userRobotXCoordinate;
        this.yCoordinate = userRobotYCoordinate;
        this.direction = userRobotDirection;
    }

    public void changeDirection(KeyEvent e) {
        switch (e.getKeyChar()) {
            case 'w' -> {
                xOffset = 0;
                yOffset = -1;
                direction = UserRobotDirection.UP.getDirectionAngle();
            }
            case 'a' -> {
                xOffset = -1;
                yOffset = 0;
                direction = UserRobotDirection.LEFT.getDirectionAngle();
            }
            case 's' -> {
                xOffset = 0;
                yOffset = 1;
                direction = UserRobotDirection.DOWN.getDirectionAngle();
            }
            case 'd' -> {
                xOffset = 1;
                yOffset = 0;
                direction = UserRobotDirection.RIGHT.getDirectionAngle();
            }
        }
    }

    public void moveUserRobot(int width, int height) {
        updateCoordinates(width, height);
    }

    private double distanceToTarget(double targetX, double targetY) {
        double diffX = targetX - xCoordinate;
        double diffY = targetY - yCoordinate;
        return Math.sqrt(diffX * diffX + diffY * diffY);
    }

    public boolean reachedTarget(int targetX, int targetY) {
        return distanceToTarget(targetX, targetY) < 6;
    }

    private void updateCoordinates(int width, int height) {
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

    public boolean isInsideBush(int bushX, int bushY) {
        return distanceToTarget(bushX, bushY) < 15;
    }
}
