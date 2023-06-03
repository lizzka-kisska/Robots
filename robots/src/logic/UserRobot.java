package logic;

import java.awt.event.KeyEvent;
import java.util.Observable;

public class UserRobot extends Observable implements MovingRobot {
    public static String KEY_COORDS_CHANGED;
    public static String KEY_DISTANCE_CHANGED;
    public volatile double xCoordinate;
    public volatile double yCoordinate;
    public volatile double direction;
    public volatile int xOffset;
    public volatile int yOffset;
    public volatile boolean isVisible;
    public volatile double distanceToTarget;
    private static final UserRobot userRobot = new UserRobot();


    public UserRobot() {
    }

    public static UserRobot getInstance() {
        return userRobot;
    }

    public void changeDirection(KeyEvent e) {
        switch (e.getKeyChar()) {
            case 'w', 'W' -> {
                xOffset = UserRobotOffset.UP.getXOffset();
                yOffset = UserRobotOffset.UP.getYOffset();
                direction = UserRobotDirection.UP.getDirectionAngle();
            }
            case 'a', 'A' -> {
                xOffset = UserRobotOffset.LEFT.getXOffset();
                yOffset = UserRobotOffset.LEFT.getYOffset();
                direction = UserRobotDirection.LEFT.getDirectionAngle();
            }
            case 's', 'S' -> {
                xOffset = UserRobotOffset.DOWN.getXOffset();
                yOffset = UserRobotOffset.DOWN.getYOffset();
                direction = UserRobotDirection.DOWN.getDirectionAngle();
            }
            case 'd', 'D' -> {
                xOffset = UserRobotOffset.RIGHT.getXOffset();
                yOffset = UserRobotOffset.RIGHT.getYOffset();
                direction = UserRobotDirection.RIGHT.getDirectionAngle();
            }
        }
    }

    public void moveUserRobot(int width, int height) {
        updateCoordinates(width, height);
    }

    private double distanceTo(double X, double Y) {
        double diffX = X - xCoordinate;
        double diffY = Y - yCoordinate;
        return Math.sqrt(diffX * diffX + diffY * diffY);
    }

    public boolean reachedTarget(int targetX, int targetY) {
        int MINIMUM_DISTANCE = 6;
        distanceToTarget = distanceTo(targetX, targetY);
        setChanged();
        notifyObservers(KEY_DISTANCE_CHANGED);
        clearChanged();
        return distanceToTarget < MINIMUM_DISTANCE;
    }

    private void updateCoordinates(int width, int height) {
        int ZERO_VALUE = 0;
        double newXCoordinate = xCoordinate + xOffset;
        double newYCoordinate = yCoordinate + yOffset;
        if (width != ZERO_VALUE) {
            newXCoordinate = applyLimits(newXCoordinate, width);
        }
        if (height != ZERO_VALUE) {
            newYCoordinate = applyLimits(newYCoordinate, height);
        }
        xCoordinate = newXCoordinate;
        yCoordinate = newYCoordinate;
        setChanged();
        notifyObservers(KEY_COORDS_CHANGED);
        clearChanged();
    }

    public boolean isInsideBush(int bushX, int bushY) {
        int RADIUS = 15;
        return distanceTo(bushX, bushY) < RADIUS;
    }
}