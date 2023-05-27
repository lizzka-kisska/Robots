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
                direction = 4.65;
            }
            case 'a' -> {
                xOffset = -1;
                yOffset = 0;
                direction = 3.1;
            }
            case 's' -> {
                xOffset = 0;
                yOffset = 1;
                direction = 1.5;
            }
            case 'd' -> {
                xOffset = 1;
                yOffset = 0;
                direction = 0;
            }
        }
    }

    public void updateXOffset(int offset) {
        xOffset = offset;
    }

    public void updateYOffset(int offset) {
        yOffset = offset;
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
        return distanceToTarget(bushX, bushY) < 20;
    }
}
