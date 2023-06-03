package logic;

import java.util.Observable;
import java.util.Random;

public class Robot extends Observable implements MovingRobot {
    public static String KEY_COORDS_CHANGED;
    public final double maxVelocity = 0.1;
    public final double maxAngularVelocity = 0.001;
    public final double duration = 10;
    public volatile double xCoordinate;
    public volatile double yCoordinate;
    public volatile double direction;
    public volatile double distanceToTarget;
    public double angle = 0;
    public double velocity = maxVelocity;
    public double angularVelocity = 0;
    public double angleToNextXY = 0;
    private static final int ZERO_VALUE = 0;
    private static final Random random = new Random();
    private static final Robot robot = new Robot();

    public Robot() {
    }

    public static Robot getInstance() {
        return robot;
    }

    public double getAngleToNextXY(double nextX, double nextY) {
        double diffX = nextX - xCoordinate;
        double diffY = nextY - yCoordinate;
        return asNormalizedRadians(Math.atan2(diffY, diffX));
    }

    private double distanceTo() {
        double diffX = Target.getInstance().getX() - xCoordinate;
        double diffY = Target.getInstance().getY() - yCoordinate;
        return Math.sqrt(diffX * diffX + diffY * diffY);
    }

    public double asNormalizedRadians(double angle) {
        double DOUBLE_PI = 2 * Math.PI;
        return (angle % (DOUBLE_PI) + DOUBLE_PI) % (DOUBLE_PI);
    }

    public void updateAngularVelocity(double angleToNextXY) {
        double MINIMUM_DISTANCE = 10e-7;
        if (Math.abs(direction - angleToNextXY) < MINIMUM_DISTANCE) {
            angularVelocity = ZERO_VALUE;
        } else if (direction >= Math.PI) {
            if (direction - Math.PI < angleToNextXY && angleToNextXY < direction)
                angularVelocity = -maxAngularVelocity;
            else
                angularVelocity = maxAngularVelocity;
        } else {
            if (direction < angleToNextXY && angleToNextXY < direction + Math.PI)
                angularVelocity = maxAngularVelocity;
            else
                angularVelocity = -maxAngularVelocity;
        }
    }

    private int getNextX(int width) {
        return random.nextInt(width);
    }

    private int getNextY(int height) {
        return random.nextInt(height);
    }

    public void moveRobot(int width, int height) {
        if (width != ZERO_VALUE || height != ZERO_VALUE) {
            distanceToTarget = distanceTo();
            int nextX = getNextX(width);
            int nextY = getNextY(height);
            updateAngle(nextX, nextY);
            updateAngularVelocity(angleToNextXY);
            velocity = applyLimits(velocity, maxVelocity);
            updateDirection();
            updateCoordinates(width, height);
        }
    }

    private void updateCoordinates(int width, int height) {
        double newXCoordinate = xCoordinate + velocity * duration * Math.cos(direction);
        double newYCoordinate = yCoordinate + velocity * duration * Math.sin(direction);
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

    private void updateDirection() {
        double newDirection = asNormalizedRadians(direction + Math.min(angle, angularVelocity) * duration);
        direction = newDirection;
    }

    private void updateAngle(double nextX, double nextY) {
        angleToNextXY = getAngleToNextXY(nextX, nextY);
        angle = asNormalizedRadians(angleToNextXY - direction);
    }
}
